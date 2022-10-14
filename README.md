# roundrobin-scheduler
Simulated round robin process scheduler

## Goal
Building a round robin preemptive scheduler to organize the work of processes in a computing system.

## Tasks
1) Create a class that represents a process in a computing system.
    - Process objects must have a unique identifier
    - Arrival time which represents when the process arrived in the system (integer value)
    - Execution time which represents how long process needs to run to finish the task (integer value)
    - Priority of the process

2) Create a class that represents the engine. 
    - Scheduler: The scheduler schedules the input processes with respect to their arrival time and considers 
      their unique identifiers whenever they have same arrival time.
    - Initializer: creates such an array of processes dynamically. The initializer reads an input text file that
      contains information about the processes
    - The engine works on two threads, thread 1 and thread 2, that run sequentially. 
      Thread 1 runs the initializer, and Thread 2 waits until thread 1 is done then runs the scheduler directly after.
