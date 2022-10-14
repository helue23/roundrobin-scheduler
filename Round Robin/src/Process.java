//Paulina Morales
//Process class - from eac
public class Process {
    private int pID;
    private int arrival;
    private int execution;
    private int priority;

    //default constructor
    public Process(){
        pID = 0;
        arrival = 0;
        execution = 0;
        priority = 0;
    }

    //full constructor
    public Process(int pID, int arrival, int execution, int priority){
        this.pID = pID;
        this.arrival = arrival;
        this.execution = execution;
        this.priority = priority;
    }

    //getters and setters
    public int getPID() {
        return pID;
    }

    public void setPID(int pID) {
        this.pID = pID;
    }

    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public int getExecution() {
        return execution;
    }

    public void setExecution(int execution) {
        this.execution = execution;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setLength(int length){

    }

    //override to string method
    @Override
    public String toString(){
        if (this.getExecution() == 0) {
            return "Process: " + pID + "\nArrival Time: " + arrival + "\nTime needed for execution: " +
                    execution + "\nPriority: " + priority + "\nProcess terminated" + "\n";
        }
        else {
            return "Process: " + pID + "\nArrival Time: " + arrival + "\nTime needed for execution: " +
                    execution + "\nPriority: " + priority + "\n";
        }

    }




//    public static Process[] sort(Process[] process){
//
//        try {
//            for (int i = 0; i < processes.size() - 1; i++){
//                int holder = i;
//
//                for (int nextIndex = i + 1; nextIndex < processes.length; nextIndex++){
//                    if (processes[nextIndex].getArrival() <= processes[holder].getArrival()){
//                        if (processes[nextIndex].getArrival() == processes[holder].getArrival() &&
//                                processes[nextIndex].getPID() < processes[holder].getPID()) {
//
//                            holder = nextIndex;
//                            Process temp = processes[holder];
//                            processes[holder] = processes[i];
//                            processes[i] = temp;
//                        }
//
//                    }
//
//                }
//
//            }
//            return processes;
//        }catch (Exception error){
//            error.printStackTrace();
//        }
//
//        return processes;
//    }
}
