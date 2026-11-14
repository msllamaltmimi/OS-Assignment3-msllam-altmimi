import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;

// ANSI Color Codes for enhanced terminal output (same as Assignment 1)
class Colors {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
}

// DO NOT REMOVE OR MODIFY THIS CLASS
class RaceWindow {
    static void pause() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Global simulation settings
class Config {
    static final int TIME_SCALE = 5;
}

// Shared statistics protected by one common lock
class SharedStats {
    static int totalProcesses = 0;
    static int totalCPUs = 0;
    static int contextSwitchCount = 0;
    static int completedProcessCount = 0;
    static long totalServiceTime = 0;

    static final java.util.concurrent.atomic.AtomicInteger busyWaitPolls =
            new java.util.concurrent.atomic.AtomicInteger(0);

    // One lock protects all three shared statistics.
    private static final ReentrantLock statsLock = new ReentrantLock();

    public static void incrementContextSwitch() {
        statsLock.lock();
        try {
            int tmp = contextSwitchCount;
            RaceWindow.pause();
            contextSwitchCount = tmp + 1;
        } finally {
            statsLock.unlock();
        }
    }

    public static void incrementCompletedProcess() {
        statsLock.lock();
        try {
            int tmp = completedProcessCount;
            RaceWindow.pause();
            completedProcessCount = tmp + 1;
        } finally {
            statsLock.unlock();
        }
    }

    public static void addServiceTime(int ms) {
        statsLock.lock();
        try {
            long tmp = totalServiceTime;
            RaceWindow.pause();
            totalServiceTime = tmp + ms;
        } finally {
            statsLock.unlock();
        }
    }

    public static int getCompletedProcessCount() {
        statsLock.lock();
        try {
            return completedProcessCount;
        } finally {
            statsLock.unlock();
        }
    }

    public static int getContextSwitchCount() {
        statsLock.lock();
        try {
            return contextSwitchCount;
        } finally {
            statsLock.unlock();
        }
    }

    public static long getTotalServiceTime() {
        statsLock.lock();
        try {
            return totalServiceTime;
        } finally {
            statsLock.unlock();
        }
    }
}

// Shared ready queue protected by a lock and coordinated by a semaphore
class ReadyQueue {
    private final LinkedList<Thread> list = new LinkedList<>();
    private final Map<Thread, Process> map = new HashMap<>();

    // Protects all accesses to list and map.
    private final ReentrantLock queueLock = new ReentrantLock();

    // Each permit represents one process waiting in the ready queue.
    private final Semaphore availableProcesses = new Semaphore(0);

    // Protects the one-time shutdown notification.
    private final ReentrantLock shutdownLock = new ReentrantLock();
    private boolean shutdownSignalled = false;

    public void add(Process process) {
        Thread thread = new Thread(process);

        queueLock.lock();
        try {
            list.addLast(thread);
            map.put(thread, process);
        } finally {
            queueLock.unlock();
        }

        // Announce the new process after releasing the queue lock.
        availableProcesses.release();

        System.out.println(Colors.BLUE + "  ➕ " + Colors.BOLD + Colors.CYAN +
                process.getName() + Colors.RESET +
                Colors.BLUE + " (Priority: " + process.getPriority() +
                ") added to ready queue" + Colors.RESET +
                " │ Remaining: " + Colors.YELLOW +
                process.getRemainingTime() + "ms" + Colors.RESET +
                " of " + process.getBurstTime() + "ms");
    }

    // Wait for a process permit without holding queueLock.
    public void awaitProcessPermit() throws InterruptedException {
        availableProcesses.acquire();
    }

    public Thread poll() {
        queueLock.lock();
        try {
            if (list.isEmpty()) {
                return null;
            }

            Thread head = list.getFirst();
            RaceWindow.pause();
            list.removeFirst();

            return head;
        } finally {
            queueLock.unlock();
        }
    }

    public Process processOf(Thread thread) {
        queueLock.lock();
        try {
            return map.get(thread);
        } finally {
            queueLock.unlock();
        }
    }

    public String snapshot() {
        queueLock.lock();
        try {
            StringBuilder sb = new StringBuilder();

            for (Thread t : list) {
                if (sb.length() > 0) {
                    sb.append(" → ");
                }
                sb.append(map.get(t).getName());
            }

            return sb.length() == 0 ? "empty" : sb.toString();
        } finally {
            queueLock.unlock();
        }
    }

    // Wake all CPU workers exactly once after every process has completed.
    public void signalShutdown() {
        shutdownLock.lock();
        try {
            if (!shutdownSignalled) {
                shutdownSignalled = true;
                availableProcesses.release(SharedStats.totalCPUs);
            }
        } finally {
            shutdownLock.unlock();
        }
    }
}

// Class representing a process
class Process implements Runnable {
    private final String name;
    private final int burstTime;
    private final int timeQuantum;
    private int remainingTime;
    private final int priority;
    private final long creationTime;
    private long completionTime = 0;
    private volatile int cpuId = 0;

    public Process(String name, int burstTime, int timeQuantum, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.timeQuantum = timeQuantum;
        this.remainingTime = burstTime;
        this.priority = priority;
        this.creationTime = System.currentTimeMillis();
    }

    public void setCpuId(int cpuId) {
        this.cpuId = cpuId;
    }

    @Override
    public void run() {
        int runTime = Math.min(timeQuantum, remainingTime);

        SharedStats.incrementContextSwitch();

        System.out.println(Colors.BRIGHT_GREEN + "  ▶ [CPU-" + cpuId + "] " +
                Colors.BOLD + Colors.CYAN + name + Colors.RESET +
                Colors.GREEN + " (Priority: " + priority +
                ") executing quantum" + Colors.RESET +
                " [" + runTime + "ms]");

        try {
            Thread.sleep(runTime / Config.TIME_SCALE);
        } catch (InterruptedException e) {
            System.out.println(Colors.RED + "  ✗ " + name +
                    " was interrupted." + Colors.RESET);
            Thread.currentThread().interrupt();
            return;
        }

        remainingTime -= runTime;
        SharedStats.addServiceTime(runTime);

        int overallProgress = (int) (((double) (burstTime - remainingTime) /
                burstTime) * 100);

        System.out.println(Colors.YELLOW + "  ⏸ [CPU-" + cpuId + "] " +
                Colors.CYAN + name + Colors.RESET +
                " completed quantum " + Colors.BRIGHT_YELLOW +
                runTime + "ms" + Colors.RESET +
                " │ Overall progress: " +
                createProgressBar(overallProgress, 20) +
                Colors.MAGENTA + "  remaining: " +
                remainingTime + "ms" + Colors.RESET);

        if (remainingTime > 0) {
            System.out.println(Colors.BLUE + "  ↻ " +
                    Colors.CYAN + name + Colors.RESET +
                    " yields CPU for context switch");
        } else {
            completionTime = System.currentTimeMillis();
            SharedStats.incrementCompletedProcess();

            System.out.println(Colors.BRIGHT_GREEN + "  ✓ " +
                    Colors.BOLD + Colors.CYAN + name + Colors.RESET +
                    Colors.BRIGHT_GREEN + " finished execution!" +
                    Colors.RESET);
        }
    }

    private String createProgressBar(int progress, int width) {
        int filled = (progress * width) / 100;
        StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < width; i++) {
            bar.append(i < filled
                    ? Colors.GREEN + "█" + Colors.RESET
                    : Colors.WHITE + "░" + Colors.RESET);
        }

        bar.append("] ").append(progress).append("%");
        return bar.toString();
    }

    public String getName() {
        return name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isFinished() {
        return remainingTime <= 0;
    }

    public long getTurnaroundTime() {
        return completionTime == 0
                ? 0
                : (completionTime - creationTime) * Config.TIME_SCALE;
    }

    public long getWaitingTime() {
        return completionTime == 0
                ? 0
                : Math.max(0, getTurnaroundTime() - burstTime);
    }
}

// CPU worker: waits on the semaphore instead of polling the ready queue.
class CpuWorker extends Thread {
    private final int cpuId;
    private final ReadyQueue readyQueue;

    public CpuWorker(int cpuId, ReadyQueue readyQueue) {
        super("CPU-" + cpuId);
        this.cpuId = cpuId;
        this.readyQueue = readyQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Block until a process or a shutdown permit is available.
                readyQueue.awaitProcessPermit();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            Thread thread = readyQueue.poll();

            // A null result means this worker received a shutdown permit.
            if (thread == null) {
                if (SharedStats.getCompletedProcessCount()
                        >= SharedStats.totalProcesses) {
                    return;
                }
                continue;
            }

            Process process = readyQueue.processOf(thread);

            System.out.println(Colors.MAGENTA + "  ┌─ [CPU-" +
                    cpuId + "] picks " + Colors.BRIGHT_CYAN +
                    process.getName() + Colors.MAGENTA +
                    " │ Ready queue: [" + Colors.BRIGHT_WHITE +
                    readyQueue.snapshot() + Colors.MAGENTA +
                    "]" + Colors.RESET);

            process.setCpuId(cpuId);
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            if (!process.isFinished()) {
                readyQueue.add(process);
            } else {
                // The last CPU to observe all completed processes
                // sends the shutdown permits to every CPU worker.
                if (SharedStats.getCompletedProcessCount()
                        >= SharedStats.totalProcesses) {
                    readyQueue.signalShutdown();
                }
            }
        }
    }
}

public class SchedulerSimulationSync {
    public static void main(String[] args) {
        // Student ID used to seed the random number generator
        int studentID = 442051735;

        Random random = new Random(studentID);

        int timeQuantum = 2000 + random.nextInt(4) * 1000;
        int numProcesses = 10 + random.nextInt(11);

        int[] burstTimes = new int[numProcesses];
        for (int i = 0; i < numProcesses; i++) {
            burstTimes[i] = timeQuantum / 2 +
                    random.nextInt(2 * timeQuantum + 1);
        }

        int numCPUs = 2 + random.nextInt(3);
        Random priorityRandom = new Random(studentID + 1L);

        SharedStats.totalProcesses = numProcesses;
        SharedStats.totalCPUs = numCPUs;

        ReadyQueue readyQueue = new ReadyQueue();
        List<Process> allProcesses = new ArrayList<>();

        printHeader(studentID, numProcesses, timeQuantum, numCPUs);

        for (int i = 0; i < numProcesses; i++) {
            Process process = new Process(
                    "P" + (i + 1),
                    burstTimes[i],
                    timeQuantum,
                    1 + priorityRandom.nextInt(10)
            );

            allProcesses.add(process);
            readyQueue.add(process);
        }

        System.out.println("\n" + Colors.BOLD + Colors.GREEN +
                "╔════════════════════════════════════════════════════════════════════════════════╗" +
                Colors.RESET);

        System.out.println(Colors.BOLD + Colors.GREEN + "║" +
                Colors.RESET + Colors.BG_GREEN + Colors.WHITE + Colors.BOLD +
                "                  ▶  " + numCPUs +
                " CPUs STARTING (CONCURRENT SCHEDULER)  ◀                    " +
                Colors.RESET + Colors.BOLD + Colors.GREEN + "║" +
                Colors.RESET);

        System.out.println(Colors.BOLD + Colors.GREEN +
                "╚════════════════════════════════════════════════════════════════════════════════╝" +
                Colors.RESET + "\n");

        List<CpuWorker> cpus = new ArrayList<>();

        for (int i = 1; i <= numCPUs; i++) {
            CpuWorker cpu = new CpuWorker(i, readyQueue);
            cpus.add(cpu);
            cpu.start();
        }

        // Watchdog: unchanged
        long expectedMs = 0;
        for (int b : burstTimes) {
            expectedMs += b / Config.TIME_SCALE;
        }

        long timeoutMs = 3 * expectedMs / numCPUs + 15000;
        long deadline = System.currentTimeMillis() + timeoutMs;
        boolean stuck = false;

        try {
            for (CpuWorker cpu : cpus) {
                long left = deadline - System.currentTimeMillis();
                if (left > 0) {
                    cpu.join(left);
                }
                if (cpu.isAlive()) {
                    stuck = true;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        int expectedQuanta = 0;
        long expectedService = 0;

        for (int b : burstTimes) {
            expectedQuanta += (b + timeQuantum - 1) / timeQuantum;
            expectedService += b;
        }

        if (stuck) {
            System.out.println("\n" + Colors.BOLD + Colors.RED +
                    "⛔ WATCHDOG: the simulation did not finish (" +
                    timeoutMs / 1000 +
                    " s). A process was lost, or a CPU is blocked forever, or the 'completed' counter lost an update." +
                    Colors.RESET);
        }

        printResults(allProcesses, expectedQuanta,
                expectedService, numProcesses);

        if (stuck) {
            System.exit(1);
        }
    }

    private static void printHeader(int studentID, int numProcesses,
                                    int timeQuantum, int numCPUs) {
        String line = "═".repeat(87);

        System.out.println("\n" + Colors.BOLD + Colors.BRIGHT_CYAN +
                "╔" + line + "╗" + Colors.RESET);

        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" +
                Colors.RESET + Colors.BG_BLUE + Colors.BRIGHT_WHITE +
                Colors.BOLD +
                "           CONCURRENT CPU SCHEDULER SIMULATION (ASSIGNMENT 3 - SYNCHRONIZATION)          " +
                Colors.RESET + Colors.BOLD + Colors.BRIGHT_CYAN + "║" +
                Colors.RESET);

        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN +
                "╠" + line + "╣" + Colors.RESET);

        printHeaderRow("  Processes:       ", String.valueOf(numProcesses));
        printHeaderRow("  Time Quantum:    ",
                timeQuantum + "ms (nominal, simulated " +
                        Config.TIME_SCALE + "x faster)");
        printHeaderRow("  CPUs:            ", String.valueOf(numCPUs));
        printHeaderRow("  Student ID:      ", String.valueOf(studentID));

        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN +
                "╚" + line + "╝" + Colors.RESET + "\n");
    }

    private static void printHeaderRow(String label, String value) {
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" +
                Colors.RESET + Colors.YELLOW + label + Colors.RESET +
                Colors.BRIGHT_YELLOW + String.format("%-68s", value) +
                Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
    }

    // Verification block and process summary table: unchanged
    private static void printResults(List<Process> processes,
                                     int expectedQuanta,
                                     long expectedService,
                                     int numProcesses) {
        System.out.println("\n" + Colors.BOLD + Colors.BRIGHT_GREEN +
                "═══ VERIFICATION (expected values are computed from YOUR seed) ═══" +
                Colors.RESET);

        boolean ok = true;

        ok &= verifyLine("Context switches (quanta executed)",
                expectedQuanta, SharedStats.getContextSwitchCount());

        ok &= verifyLine("Completed processes",
                numProcesses, SharedStats.getCompletedProcessCount());

        ok &= verifyLine("Total service time (ms)",
                expectedService, SharedStats.getTotalServiceTime());

        ok &= verifyLine("Busy-waiting polls (must be 0)",
                0, SharedStats.busyWaitPolls.get());

        System.out.println(ok
                ? Colors.BOLD + Colors.BRIGHT_GREEN +
                  "  ✔ All shared values are consistent." + Colors.RESET
                : Colors.BOLD + Colors.RED +
                  "  ✘ At least one check failed (see the ✘ lines above): a race condition or busy waiting is still present." +
                  Colors.RESET);

        System.out.println("\n" + Colors.BOLD + Colors.BRIGHT_CYAN +
                "═══ Process Summary Table (nominal ms) ═══" + Colors.RESET);

        System.out.println(Colors.BOLD +
                String.format("%-9s %-9s %-11s %-13s %-13s",
                        "Process", "Priority", "Burst",
                        "Waiting", "Turnaround") + Colors.RESET);

        System.out.println("─".repeat(58));

        for (Process p : processes) {
            System.out.println(String.format(
                    "%-9s %-9d %-11d %-13d %-13d",
                    p.getName(),
                    p.getPriority(),
                    p.getBurstTime(),
                    p.getWaitingTime(),
                    p.getTurnaroundTime()
            ));
        }

        System.out.println();
    }

    private static boolean verifyLine(String label,
                                      long expected,
                                      long observed) {
        boolean good = expected == observed;

        System.out.println(String.format(
                "  %-38s expected %-8d observed %-8d ",
                label, expected, observed
        ) + (good
                ? Colors.BRIGHT_GREEN + "✔"
                : Colors.RED + "✘ MISMATCH") + Colors.RESET);

        return good;
    }
}