# 📝 MY_WORK: Student Information, Development Log, Reflection & Answers

> This is the **only file** your instructor reads to grade Parts 3 and 4 (documentation and video). Everything you write here must be **in your own words**.

---

## 🛑 STOP: Read This Before You Do Anything Else

> ### 1️⃣ Read the whole `README.md` first
> The `README.md` in this repository contains the full instructions: how this assignment continues Assignment 1, the Chapter 5 recap, class descriptions, feature specifications, question prompts and the video script. **If you skip it, you will lose marks.**
>
> ### 2️⃣ Understand the full code before answering any question
> Open `SchedulerSimulationSync.java` and read it **from top to bottom**. You must be able to explain what `SharedStats`, `ReadyQueue`, `CpuWorker`, `RaceWindow.pause()`, `ReentrantLock` and `Semaphore` do **before** you write a single answer in Parts B and C. **Run the original starter first** (it is intentionally broken) and **save its output**: you need it for Question 1 of Part C.
>
> ### 3️⃣ Use only the code of this repository
> Work **only** on `SchedulerSimulationSync.java` from this repository. Do **not** copy or paste code from your previous assignments.
>
> ### 4️⃣ Commit many times, not once
> A single commit, or all commits made in the last hour, costs you **-0.5 mark**. See the [Commit Rules](#-commit-rules-mandatory) below.

**How to use this file:**
1. Fill in your **Student Information** (below) right now.
2. Follow the steps in the **Work Roadmap** in order.
3. Update the **Development Log** *every time* you work on the assignment, not at the end.
4. Do not delete any section header. Replace the `[...]` placeholders with your own text.

---

## 👤 Student Information

> ⚠️ **WARNING:** Fill this in first. Your name and ID must match the student ID you set in `SchedulerSimulationSync.java` (line 304) and the one you say in your video.

| Field | Your Answer |
|-------|-------------|
| **Full Name** | [Msllam ALTamimi] |
| **Student ID** | [442051735] |
| **University Email** | [442051735@std.psau.edu.sa |
| **GitHub Username** | [msllamaltmimi] |
| **Repository Link** | [https://github.com/msllamaltmimi/OS-Assignment3-msllam-altmimi] |
 
---

## 🎥 Video Link

**Video Link**: [Paste your video link here]

> ⚠️ **WARNING:** The video must be **publicly accessible** ("Anyone with the link can view") on **Google Drive**, **YouTube (Unlisted or Public)** or any other cloud file-sharing system. A private, restricted or broken link counts as a **missing video (-1 mark)**.
>
> 💡 **TIP:** Open the link in a **private/incognito window** before you submit. If it asks you to log in or request access, it is not public.
>
> 📌 **NOTE:** The link goes in **this file only** (`MY_WORK.md`), **not** in `README.md`. Name your video file `StudentID_Assignment3_Demo.mp4`. It must last **2 to 3 minutes**.

---

## 🗺️ Work Roadmap (follow in this order)

| Step | What to do | Where | Marks |
|:----:|------------|-------|:-----:|
| 0 | Read `README.md`, read the full code, **run the original starter and save its output** (it fails on purpose) | Your IDE | – |
| 1 | Fork, rename, keep the repo **PUBLIC**, set your student ID (line 304), **commit** | GitHub + code | Part 1 (1) |
| 2 | Feature 1: Mutex lock on the ready queue, **commit** | Code | Part 2 (0.25) |
| 3 | Feature 2: Mutex lock(s) on the shared statistics, **commit** | Code | Part 2 (0.25) |
| 4 | Feature 3: Counting semaphore instead of busy waiting, **commit** | Code | Part 2 (0.5) |
| 5 | Development Log (5+ entries, different dates) | This file, Part A | Part 3 (0.5) |
| 6 | Reflection (4 questions) | This file, Part B | Part 3 (0.5) |
| 7 | Technical Answers (4 questions) | This file, Part C | Part 3 (0.5) |
| 8 | Record the video, upload it, paste the link above | Video + this file | Part 4 (1.5) |
| 9 | Final check, then submit the repo link on Blackboard | Blackboard | – |

> 💡 **TIP:** Tick each step off as you go. Do not leave the log, the reflection or the video for the last day.

---

## 🔁 Commit Rules (MANDATORY)

> ### ⚠️ MANY COMMITS ARE REQUIRED. A single bulk commit is penalized (-0.5 mark).

**Minimum: 4 meaningful commits. Aim for 7 or more.**

| # | Commit | Example message |
|:-:|--------|-----------------|
| 1 | Student ID set | `Set my student ID: 441234567` |
| 2 | Feature 1: Ready queue lock | `Feature 1: Protected the ready queue with a ReentrantLock` |
| 3 | Feature 2: Statistics locks | `Feature 2: Added locks to the shared statistics counters` |
| 4 | Feature 3: Semaphore | `Feature 3: Replaced busy waiting with a counting semaphore` |
| 5 | Development log entries | `Docs: Added development log entries 1-3` |
| 6 | Reflection and answers | `Docs: Completed reflection and technical answers` |
| 7 | Video link | `Docs: Added demo video link` |

**Rules:**
- ✅ **One commit per feature.** Do not put all three features in one commit.
- ✅ **Commit after each work session**, and after each part of this file.
- ✅ **Spread your commits over different dates.** Not all in one day.
- ❌ **Do not make all commits in the last hour** before the deadline (November 14, 2026).
- ❌ **No vague messages** like `done`, `update` or `final version`.

> 💡 **TIP:** Your commit history is checked and you **show it in your video** (at least 4 commits visible). Your development log dates should match your commit dates.
>
> 💡 **TIP:** **Use VS Code** (see *Recommended Development Environment* in `README.md` for the full setup). Sign in to GitHub in VS Code, then commit from the Source Control panel (Ctrl+Shift+G) → stage → write a message → Commit → Sync/Push. You can edit and commit this file the same way. **Pushing** matters: commits that are not pushed to GitHub are invisible to the instructor.

---

# Part A: Development Log (0.5 mark)

Entry 1 - [November 9, 2026, 6:00 PM – 10:00 PM]

What I did: Reviewed the starter project and tested the original program.

Details:

Read the assignment instructions and reviewed the SchedulerSimulationSync.java source code.

Examined the main components, including ReadyQueue, SharedStats, Process, and CpuWorker.

Ran the original starter program using my student ID.

Observed that CPU-1 and CPU-2 both selected P1.

The program produced an IllegalThreadStateException and eventually stopped because of the watchdog.

Saved the original output as evidence of the race condition.

Challenges: I initially did not understand why two CPU workers selected the same process at almost the same time.

Solution: I examined the ready queue operations and identified that multiple CPU workers could access the shared queue without proper synchronization.

Time spent: 4 hours

Entry 2 - [November 10, 2026, 6:00 PM – 10:00 PM]

What I did: Implemented synchronization for the ready queue.

Details:

Added a ReentrantLock named queueLock to the ReadyQueue class.

Protected the queue operations using lock(), try, and finally.

Applied the lock to operations that access the shared queue and process map, including poll(), add(), processOf(), and snapshot().

Ensured that the lock is released using unlock() inside the finally block.

Challenges: I needed to prevent two CPU workers from selecting and removing the same process from the ready queue.

Solution: I used a lock to protect the queue and map operations, ensuring that only one worker could access these shared structures at a time.

Time spent: 4 hours

Entry 3 - [November 11, 2026, 6:00 PM – 10:00 PM]

What I did: Protected the shared statistics using locks.

Details:

Reviewed the shared variables in the SharedStats class.

Added a ReentrantLock named statsLock.

Protected the methods that update the statistics, including incrementContextSwitch(), incrementCompletedProcess(), and addServiceTime().

Protected the getter methods to ensure consistent access to shared values.

Used try and finally blocks to guarantee that the lock is released after each operation.

Challenges: Multiple CPU workers update shared statistics during the simulation, which could cause inconsistent values if the operations were not protected.

Solution: I used a common statistics lock to ensure that updates and reads of shared values are performed safely.

Time spent: 4 hours

Entry 4 - [November 12, 2026, 6:00 PM – 10:00 PM]

What I did: Implemented a counting semaphore for process availability and CPU worker coordination.

Details:

Imported and used Java's Semaphore class.

Added availableProcesses to the ReadyQueue and initialized it with zero permits.

Released a permit whenever a process was added to the ready queue.

Used acquire() in the CPU workers to wait until a process became available.

Implemented shutdown signaling to release permits and allow CPU workers to finish when the simulation was completed.

Challenges: I needed to coordinate CPU workers with available processes and ensure that workers could exit without remaining blocked.

Solution: I used a counting semaphore to notify workers when processes became available and added a shutdown mechanism to wake the remaining workers when processing was complete.

Time spent: 4 hours

Entry 5 - [November 13, 2026, 6:00 PM – 10:00 PM]

What I did: Tested the synchronized program and checked the verification results.

Details:

Ran the modified program using the same student ID and simulation configuration as the original test.

Checked that CPU workers selected different processes.

Confirmed that all 19 processes completed successfully.

Verified that the expected and observed context switches were both 41.

Verified that the expected and observed completed processes were both 19.

Verified that the total service time was 61,562 ms and busy-waiting polls were zero.

Confirmed that the program finished with exit code 0 and without a watchdog warning.

Challenges: I needed to confirm that synchronization fixed the original problems without affecting the expected simulation results.

Solution: I compared the final output with the original output and checked all four verification results. All four checks passed in this test run.

Time spent: 4 hours

Entry 6 - [November 14, 2026, 6:00 PM – 10:00 PM]

What I did: Reviewed the implementation and completed the assignment documentation.

Details:

Reviewed the synchronization code and checked that locks were released in finally blocks.

Reviewed the development log and reflection sections.

Prepared the technical explanations using the original and final program outputs.

Reviewed the final assignment checklist.

Checked the repository and commit history.

Prepared the assignment for final submission.

Challenges: I needed to explain the synchronization concepts clearly and connect them to the actual implementation and test results.

Solution: I reviewed the code and compared the original and synchronized outputs to support my explanations with evidence.

Time spent: 4 hours

---

## Development Log Summary

Total time spent: 24 hours (6 days × 4 hours per day).

Most challenging part: The most challenging part was fixing the race condition in the ready queue because multiple CPU workers could select the same process at the same time. I had to understand how concurrent access affected the queue and how a lock could protect the critical section.

What I learned: I learned how to use ReentrantLock to protect shared data and how a counting semaphore can coordinate CPU workers with available processes. I also learned the importance of releasing locks safely and testing concurrent programs carefully.

What I would do differently: I would analyze the shared data structures and identify possible race conditions earlier. I would also test the program after implementing each synchronization feature instead of waiting until all changes were completed.

---

# Part B: Reflection (0.5 mark)

> 🛑 **STOP:** Do **not** start this part until you have read the `README.md`, read the **entire** `SchedulerSimulationSync.java`, run it, and finished the three features.
>
> ⚠️ **WARNING:** Each answer must be **5 to 7 sentences**, in **your own words**. Copied or AI-generated answers without understanding get **0 marks for the whole assignment**. You may be asked to explain them in person.
>
> 💡 **TIP:** Mention concrete things you actually did: a method you wrote, an error you hit, a line of output you saw. Generic answers score low.
>
> 💡 **TIP:** Draft your answer in a few bullet points first, then turn them into sentences.

## Question 1: What did you learn about synchronization?

> 💡 **TIP:** Talk about race conditions (read-modify-write), critical sections, mutual exclusion with `ReentrantLock` and `try/finally`, the role of a counting semaphore, and what surprised you when the starter failed.

**Your Answer:** *(5-7 sentences)*

I learned that synchronization is important when multiple threads access and modify shared data at the same time. A race condition can happen when two threads perform read-modify-write operations without proper protection, which may lead to incorrect results. In my project, I used ReentrantLock to protect the ready queue and shared statistics, and I used try and finally to make sure that locks were released safely. I also learned that a counting semaphore can control access to available processes and allow CPU workers to wait until work is ready. What surprised me was that the original program allowed CPU-1 and CPU-2 to select P1 at the same time, which caused an IllegalThreadStateException. After implementing synchronization, the final test completed successfully, and all four verification checks passed.


## Question 2: What was the most challenging part of this assignment?

> 💡 **TIP:** Pick **one** specific challenge (understanding why the starter failed, choosing the lock granularity, the initial value of the semaphore, the shutdown of the CPUs, Git, the video) and say *why* it was hard.

**Your Answer:** *(5-7 sentences)*

The most challenging part was understanding and fixing the race condition in the ready queue. At first, I did not understand why two CPU workers could select the same process during the simulation. The original output showed that both CPU-1 and CPU-2 selected P1, and then one worker produced an IllegalThreadStateException. This was difficult because the problem was related to the timing of thread execution rather than a simple programming mistake. I had to understand how two threads could access the queue before either one completed the operation of removing a process. I solved this problem by using a ReentrantLock to protect the queue operations and ensure mutual exclusion.


## Question 3: How did you overcome the challenges you faced?

> 💡 **TIP:** Describe your method: reading the slides and the documentation, adding `System.out.println` to debug, running the program many times, re-reading the README, testing after each small change, asking for help.

**Your Answer:** *(5-7 sentences)*

I overcame the challenges by reading the assignment instructions and reviewing the Java code carefully before making changes. I focused on understanding the ReadyQueue, SharedStats, and CpuWorker classes to identify where shared data was accessed. I implemented the synchronization features step by step, starting with the ready queue lock, then protecting the statistics, and finally adding the counting semaphore. After making the changes, I ran the program and compared the original output with the final output. The final test showed that all 19 processes completed successfully, with 41 context switches, 61,562 ms of service time, and zero busy-waiting polls. This process helped me understand the purpose of each synchronization mechanism and how to use it in a practical Java program.


## Question 4: Where can synchronization concepts be applied in real-world applications?

> 💡 **TIP:** Use real applications you know (banking and money transfers, ticket or seat booking, a shared counter on a website, a print queue, a database) and connect each one to what you built here (shared data, critical section, lock or semaphore).

**Your Answer:** *(5-7 sentences)*

Synchronization is used in many real-world applications where multiple users or processes access shared resources. In banking systems, locks can protect account balances during money transfers to prevent incorrect updates when several transactions happen at the same time. In online ticket booking systems, synchronization can prevent two customers from booking the same seat. Websites can also use locks to update shared counters, such as the number of visitors, without losing updates. In operating systems, semaphores can coordinate access to shared resources such as printers and manage waiting processes. These examples are similar to my assignment because the ready queue and statistics are shared resources that must be accessed safely by multiple CPU workers.


### Optional: What would you like to learn more about?

I would like to learn more about advanced synchronization techniques, especially ReadWriteLock, atomic variables, and thread-safe data structures. I am also interested in understanding deadlocks and how to prevent them in larger concurrent applications.


### Optional: How confident do you feel about synchronization concepts now?

Intermediate. I understand the basic concepts of race conditions, critical sections, mutual exclusion, locks, and counting semaphores. I can identify some synchronization problems and apply locks to protect shared data. However, I still need more practice with advanced concurrency problems, especially deadlocks and thread scheduling.


### Optional: Feedback on the assignment

I found this assignment useful because it helped me understand synchronization through practical programming rather than theory alone. Running the original program and comparing its output with the synchronized version made the race condition easier to understand. The assignment was challenging, especially when working with multiple CPU threads, but it improved my understanding of Java concurrency and operating system concepts.


---

# Part C: Technical Answers (0.5 mark)

> 🛑 **STOP:** You cannot answer these questions without understanding the code. Re-read `SchedulerSimulationSync.java` and **run it** first. Your answers must reference **your own code and your own output** (your student ID makes your output and your expected values unique).
>
> ⚠️ **WARNING:** Each answer must be **3 to 5 sentences**, with specific examples from your code or output. Use correct terms: race condition, critical section, mutual exclusion, mutex lock, semaphore, busy waiting, deadlock, producer, consumer.
>
> 💡 **TIP:** Keep the output of the **original starter** and the output of your **final version** in text files or screenshots, so you can copy real snippets for Question 1.

## Question 1: Race Condition in Your Own Output

**Question**: Run the **original starter** (before any fix) and paste the **verification block** of that run. Which value was wrong (expected vs observed)? Explain **step by step**, with two named threads/CPUs, how **one lost update** happens on one of the shared variables (read, `RaceWindow.pause()`, write). Why can the same bug happen on a real machine even without `RaceWindow`, and why is it hard to detect?

> ⚠️ **WARNING:** The snippet must come from **your own run** (with your student ID), not from a classmate or from this README.
>
> 💡 **TIP:** Use a small timeline: *CPU-1 reads `contextSwitchCount` = 12 → CPU-2 reads 12 → CPU-1 writes 13 → CPU-2 writes 13 → one increment is lost.* If your starter run crashed before printing numbers, run it several times (or paste the exception and the watchdog message and explain it).

**Your Answer:** *(3-5 sentences)*

The original starter program had a race condition because multiple CPU workers could access the ready queue without mutual exclusion. In my original output, CPU-1 and CPU-2 both selected P1, and CPU-2 then threw an IllegalThreadStateException when it tried to start the same thread again. A lost update can also happen when CPU-1 reads a shared counter, pauses, and CPU-2 reads the same old value before either CPU writes the updated value; both may then write the same result, losing one increment. This problem can happen on a real machine without RaceWindow.pause() because thread scheduling and timing can still allow concurrent operations to overlap, making the bug difficult to reproduce consistently. After synchronization, my final run completed successfully with all four verification checks passing.


Verification block of the **original starter**:
Exception in thread "CPU-2" java.lang.IllegalThreadStateException
    at java.base/java.lang.Thread.start(Thread.java:1416)
    at CpuWorker.run(SchedulerSimulationSync.java:283)

WATCHDOG: Simulation did not finish (33 s).

Context switches: Expected 41, Observed 39 - FAILED
Completed processes: Expected 19, Observed 18 - FAILED
Total service time: Expected 61562, Observed 58841 - FAILED
Busy-waiting polls: Expected 0, Observed 1068 - FAILED

At least one check failed. Race condition or busy waiting remains.

Verification block of **my final version**:
Context switches: Expected 41, Observed 41 - PASSED
Completed processes: Expected 19, Observed 19 - PASSED
Total service time: Expected 61562, Observed 61562 - PASSED
Busy-waiting polls: Expected 0, Observed 0 - PASSED

All shared values are consistent.
Program finished with exit code 0.

**Explanation of the timeline:**
For example, if contextSwitchCount has a value of 12, CPU-1 may read 12 and pause before writing. CPU-2 may also read 12 during that time, and then both CPUs may write 13 instead of the correct value 14. This is a lost update because two increments result in only one recorded increment. A lock protects the read-modify-write operation so that the second CPU must wait until the first CPU finishes.


## Question 2: Critical Sections and Lock Granularity

**Question**: List the **critical sections** you protected (variables and methods) and the lock(s) you used. **Lock granularity:** did you use **one lock** for the three statistics or **one lock per variable**? Why, and what is the trade-off? Why is the **ready queue** protected by a **different** lock than the statistics? Explain how your solution provides **mutual exclusion**, and discuss **progress** and **bounded waiting** (a default `ReentrantLock` is not fair: what does that imply?).

> 💡 **TIP:** The three counters are independent: does updating `contextSwitchCount` need to block an update of `totalServiceTime`? Also think about what could go wrong (deadlock, or long waiting) if the queue lock and the statistics lock were the **same** lock and a thread held it while doing something slow.

**Your Answer:** *(3-5 sentences)*
I used a separate ReentrantLock named queueLock to protect the ready queue and its process map, including add(), poll(), processOf(), and snapshot(). For the shared statistics, I used one common ReentrantLock named statsLock to protect the update and getter methods for the counters. Using one statistics lock makes the implementation simpler and protects the operations consistently, but it also means that updates to independent counters can block each other. The queue uses a different lock because queue operations and statistics updates are separate critical sections, and separating them reduces unnecessary blocking between CPU workers. These locks provide mutual exclusion, but the default ReentrantLock is not fair, so a waiting thread is not guaranteed to acquire the lock in arrival order, and bounded waiting is not guaranteed.


| Critical section (variable / method) | Lock used | Why it needs protection |
Critical section (variable / method)

Lock used

Why it needs protection

ReadyQueue.add()

queueLock

Protects adding threads to the queue and updating the process map.

ReadyQueue.poll()

queueLock

Prevents two CPU workers from removing or selecting the same process.

ReadyQueue.processOf()

queueLock

Protects access to the shared process map.

ReadyQueue.snapshot()

queueLock

Protects reading the shared queue contents.

SharedStats.incrementContextSwitch()

statsLock

Prevents lost updates to the context switch counter.

SharedStats.incrementCompletedProcess()

statsLock

Protects updates to the completed process counter.

SharedStats.addServiceTime()

statsLock

Prevents incorrect updates to total service time.

SharedStats getter methods

statsLock

Ensures consistent reading of shared statistics.

ReadyQueue.signalShutdown()

shutdownLock

Ensures that shutdown signaling happens only once.

## Question 3: Busy Waiting vs Semaphore

**Question**: Describe the **busy waiting** of the starter and its cost (give the `Busy-waiting polls` number from your **starter** run). What does the **value of your semaphore** represent? What is its **initial value** and why? What is its value right after the `numProcesses` initial `add()` calls? Why does the last CPU call `release(totalCPUs)` at the end, and what would happen if it did not?

> 💡 **TIP:** Think about what a CPU does when the queue is empty in the starter, and what a CPU does in your final version (is it running, ready or blocked?). Also check what happens to a CPU blocked in `acquire()` when no process will ever be added again.

**Your Answer:** *(3-5 sentences)*

In the original starter, CPU workers repeatedly checked whether the ready queue contained a process, which caused busy waiting and wasted CPU time when the queue was empty. My original output showed 1,068 busy-waiting polls, while my final output showed zero. In my final code, the availableProcesses counting semaphore starts at 0 because no processes are available before the initial processes are added, and its value becomes 19 after the 19 initial add() calls. Each release() represents an available process permit, while acquire() allows a CPU worker to block until a permit becomes available. The last CPU calls release(totalCPUs) to wake blocked workers during shutdown, and without this release, a CPU worker could remain blocked forever after all processes have completed.


## Question 4: Classical Problems and Deadlock

**Question**: The CPUs and the ready queue look like a classical synchronization problem of Chapter 5. **Name it**, and say who plays the **producer** and the **consumer** roles (careful: here the CPUs also re-enqueue processes). If the ready queue were **bounded** (capacity `K`), name the **three semaphores** you would need and their **initial values**. Describe a **deadlock scenario** that appears if a thread acquired the **mutex before** waiting on the semaphore (or if two locks were acquired in different orders), and say **which of the four necessary conditions** you would break to prevent it.

> 💡 **TIP:** Recall the bounded-buffer solution of the slides (`empty`, `full`, `mutex`) and the order of `wait()` calls in the producer and the consumer. For the deadlock, think about a producer that holds the mutex while waiting for a free slot that only a consumer (who needs the mutex) can create.

**Your Answer:** *(3-5 sentences)*

Classical problem and roles:

This project is related to the classical Producer-Consumer problem, also known as the Bounded-Buffer problem. The CPU workers act as consumers when they take processes from the ready queue, and they also act as producers when they re-enqueue processes that still have remaining execution time.

Semaphores for a bounded queue (name and initial value):

For a bounded queue with capacity K, I would use three semaphores: empty = K to count the available empty slots, full = 0 to count the available processes, and mutex = 1 to protect access to the queue. These values allow producers and consumers to coordinate safely without accessing a full or empty queue incorrectly.

Deadlock scenario and prevention:

A deadlock could happen if a producer acquired the mutex while the queue was full and then waited for an empty slot, because a consumer would need the same mutex to remove a process and create an empty slot. This creates a circular wait because the producer holds the mutex while waiting for an action that the consumer cannot perform. To prevent this, the producer should wait for an empty slot before acquiring the mutex, following the correct semaphore order. This breaks the hold-and-wait condition and helps prevent deadlock.


## Summary

**Key concepts I understood through these questions:**
Race conditions occur when multiple threads access or update shared data without proper synchronization.

ReentrantLock provides mutual exclusion for critical sections, while a counting semaphore coordinates access to available processes.

The Producer-Consumer problem explains how processes can be added to and removed from a shared ready queue safely.


**Concepts I need to study more:**
Deadlock prevention and the four necessary conditions for deadlock.

Lock fairness, bounded waiting, and advanced Java concurrency mechanisms.

---

# ✅ Final Checklist (complete before submitting)

> ⚠️ **WARNING:** Go through every line. Blackboard does not accept submissions after the deadline, which is **November 14, 2026**.

**Repository**
- [ ] Repository is **PUBLIC** (Settings → Danger Zone → Visibility)
- [ ] Repository is renamed to `OS-Assignment3-YourFirstName-YourLastName`
- [ ] GitHub account uses the university email (`@std.psau.edu.sa`)

**Code**
- [X] Student ID is set in `SchedulerSimulationSync.java` (line 304)
- [X] My code is based **only** on the starter of this repository (nothing copied from previous assignments)
- [X] Code compiles and runs with no errors, and terminates by itself (no `⛔ WATCHDOG`)
- [X] Feature 1 (ready queue lock), Feature 2 (statistics locks) and Feature 3 (semaphore, no busy waiting) all work
- [ ] **The four verification lines are ✔ in 5 consecutive runs**
- [X] Every lock is released in a `finally` block
- [ ] `RaceWindow`, `Config`, the verification block and the watchdog are **unchanged**
- [ ] Each feature has clear comments

**Commits**
- [ ] **At least 4 meaningful commits, ideally 7 or more**
- [ ] **One commit per feature**
- [ ] Commits are spread over **different dates** (not all in the last hour)
- [ ] Everything is **pushed** to GitHub

**This file (`MY_WORK.md`)**
- [X] Full name and student ID filled in at the top
- [X] Development log has **5+ entries** on different dates
- [X] Reflection: 4 questions, 5-7 sentences each
- [X] Technical answers: 4 questions, 3-5 sentences each, with examples from **your** output (starter output **and** final output pasted in Question 1)
- [ ] No `[...]` placeholders left
- [X] No section headers deleted

**Video**
- [ ] 2-3 minutes long, named `StudentID_Assignment3_Demo.mp4`
- [ ] Shows your name, ID, repository, 3 features (and your lock granularity choice), IDE execution with the verification block, one synchronization concept, and commit history
- [ ] Link is **public** (tested in an incognito window) and pasted in the **Video Link** section above

**Blackboard**
- [ ] Submit **only** the link to your public GitHub repository

> 🎯 **Good luck!** Start early, commit regularly, and make sure you can explain every line you submit.
