import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class MainEngine{

    public static ArrayList<Process> processes = new ArrayList<Process>(100);
    public static int quantum = 0;
    public static File directory  = new File("Round Robin/src/TestsFiles");
    public static File [] files = directory.listFiles();
    public static int count = 0;

    public static void setProcesses(ArrayList<Process> processes) {
        MainEngine.processes = processes;
    }

    public static void setQuantum(int quantum) {
        MainEngine.quantum = quantum;
    }

    //multi-threading, one thread for initializer and one for the scheduler
    public static void main(String[] args) {
        Thread thread1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Initializer(files[count]);
                    }
                }
        );
        Thread thread2 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Scheduler(processes, quantum);
                    }
                }
        );

        thread1.start();

        try {
            thread1.join();
        }catch(InterruptedException error){
            error.printStackTrace();
        }
        thread2.start();
    }

    /*initialize the array list using the file in the directory which holds the "processes" and their
    corresponding info */
    public static void Initializer(File file){
        int i = 0;

        boolean entered = false; //flag when process amount and quantum have been initialized/entered
        File file1 = new File(file.getAbsolutePath());

            try (Scanner sc = new Scanner(file1)) {
                while (sc.hasNextLine()) {
                    String s = sc.nextLine(); //turn each line into a string
                    String[] str = s.split(", "); //split the string
                    if (!entered) {    //if the first line of the file hasn't been read, parse it. In order to set the quantum
                        setQuantum(Integer.parseInt(str[1]));
                        entered = true;
                    } else { //Once the quantum has been set. Each process holds a process ID, an arrival time, an execution time, and a priority
                        Process p1 = new Process((Integer.parseInt(str[0])), Integer.parseInt(str[1]),
                                Integer.parseInt(str[2]), Integer.parseInt(str[3]));
                        processes.add(i, p1);

                        i++;
                    }
                }
                System.out.println("File name: " + file.getName() + "\n");
            } catch (Exception error) {
                System.out.println("File not found");
            }
        }

    /*Function accepts an array of processes and quantum (integer value represents the maximum time
    window for how long each process should run). The scheduler schedules the input processes with respect
    to their arrival time and considers their unique identifiers whenever they have same arrival time
    (two processes with same arrival time, then consider the one with smaller id as first choice).*/
    public static void Scheduler(ArrayList processes, int quantum){
        //sortArray(processes);
        mergeSort(processes);

        Queue <Process> pQueue = new LinkedList<Process>();

        //add processes in the array to a queue, processes were in an array per assignment specifications
        for (int i = 0; i < processes.size(); i++){
            pQueue.add((Process) processes.get(i));
        }
        try {
            while (!pQueue.isEmpty()){  //"run" the processes
                Process current = pQueue.remove();
                for (int j = 0; j < quantum; j++){
                    if (current.getExecution() <= 0){
                        break;
                    }
                    else{
                        current.setExecution(current.getExecution() -1);
                    }
                }
                if (current.getExecution() > 0)
                    pQueue.add(current);

                System.out.println(current.toString());
            }
        } catch (Exception error){
            error.printStackTrace();
        }
        recMethod();
    }

    //initially used selection sort for processes
    //switched to merge sort to optimize the program
    public static void sortArray(ArrayList processes){        //helper method
        for (int i = 0; i < processes.size() - 1; i++){
            int min = i;
            for (int nextIndex = i + 1; nextIndex < processes.size(); nextIndex++){
                Process curr = (Process) processes.get(nextIndex);
                Process smallest = (Process)processes.get(min);
                if (curr.getArrival() < smallest.getArrival()) {
                    Collections.swap(processes, processes.indexOf(smallest), processes.indexOf(curr));
                }
                else if ((curr.getArrival() == smallest.getArrival()))
                    if (curr.getPID() < smallest.getPID()){
                        Collections.swap(processes, processes.indexOf(smallest), processes.indexOf(curr));
                }
            }
        }
        setProcesses(processes);
    }

    //chose merge sort because it is more stable and worst case time complexity is better than quick sort
    public static void mergeSort(ArrayList processes){
        mergeSortRec(processes, 0, processes.size() - 1);
    }

    public static void mergeSortRec(ArrayList processes, int first, int last){
        if (first < last){
            int mid = (first + last) / 2;
            mergeSortRec(processes, first, mid);
            mergeSortRec(processes, mid + 1, last);
            merge(processes, first, last);
        }
    }

    public static void merge(ArrayList processes, int first, int last){ // need to add an extra step for comparing pID
        int mid = (first + last) / 2;
        int n = last - first + 1;
        ArrayList<Process> temp = new ArrayList<Process>(n);

        //indices
        int idx1 = first;
        int idx2 = mid + 1;
        int j = 0;
        Process p1;
        Process p2;

        while(idx1 <= mid && idx2 <= last){
            p1 = (Process) processes.get(idx1);
            p2 = (Process) processes.get(idx2);
            if (p1.getArrival() == p2.getArrival()){
                if (p1.getPID() < p2.getPID()){
                    temp.add(j, p1);
                    idx1++;
                }
                else{
                    temp.add(j, p2);
                    idx2++;
                }
            }
             else if (p1.getArrival() < p2.getArrival()) {
                temp.add(j, p1);
                idx1++;
            }
            else {
                temp.add(j, p2);
                idx2++;
            }
            j++;

        }

        while (idx1 <= mid){
            temp.add(j,((Process) processes.get(idx1)));
            idx1++;
            j++;
        }
        while (idx2 <= last){
            temp.add(j, ((Process) processes.get(idx2)));
            idx2++;
            j++;
        }

        for (int i = 0; i < n; i++)
            processes.set((first + i), temp.get(i));

    }

    //recursive helper method
    public static void recMethod(){
        ArrayList <Process> processes1 = new ArrayList<Process>(100);
        setProcesses(processes1);
        count++;
        if (files.length == count){       //base case: if all files have been checked, return
            count = 0;
            return;
        }
        else{                           //general case
            Initializer(files[count]);
            Scheduler(processes1, quantum);
        }
    }
}
