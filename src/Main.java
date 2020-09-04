import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProcessModel processModel = new ProcessModel();
        List<ProcessModel> processModelList = new ArrayList<>();

        processModelList.add(new ProcessModel("communication", 1, "planning", 1, 1));
        processModelList.add(new ProcessModel("planning", 2, "end", 0.8, "communication", 0.2, 0));
        processModelList.add(new ProcessModel("end", 1, 0));

        printSmallProcessModel(processModelList);

    }

    public static void printSmallProcessModel(List<ProcessModel> processModelList) {
        ProcessModel communication = processModelList.get(0);
        ProcessModel planning = processModelList.get(1);
        ProcessModel end = processModelList.get(2);

        //counter for counting iterations
        int planningCount = 1;

        //counter for iterations
        //can't use i because we sometimes skip iterations. See loop below
        int iterationCount = 1;

        //initial print
        System.out.println("Iteration number " + iterationCount + "\n");
        System.out.println("The value for communication component is + " + communication.getValue());
        System.out.println("The value for planning component is + " + planning.getValue());
        System.out.println("The value for end component is + " + end.getValue() + "\n");

        for (int i = 0; end.getValue() < 0.95; i++) {

            //communication iteration
            if (i == 0 && communication.getValue() > 0) {
                double communicationValueToBeRemoved = communication.getValue() * communication.getOutputPercent();
                ProcessModel destinationModel = findMatchingDestinationObject(communication,processModelList);
                destinationModel.setValue(destinationModel.getValue() + communicationValueToBeRemoved);
  /*              for (ProcessModel pm: processModelList) {
                    if(communication.getOutputDestination().equals(pm.getName())){
                        pm.setValue(planning.getValue() + communicationValueToBeRemoved);
                    }
                }*/
                //planning.setValue(planning.getValue() + communicationValueToBeRemoved);
                communication.setValue(communication.getValue() - communicationValueToBeRemoved);
                iterationCount++;
            }

            //planning iteration
            else if ((i == 1 || i == 2) && planning.getValue() != 0) {
                if (planningCount == planning.getTimeFrame()) {
                    double planningValueToBeRemoved = planning.getValue() * planning.getOutputPercent();
                    double planningReturnValueToBeRemoved = planning.getValue() * planning.getReturnOutputPercent();

                    ProcessModel destinationModel = findMatchingDestinationObject(planning,processModelList);
                    ProcessModel returnDestinationModel = findMatchingReturnDestinationObject(planning,processModelList);


                    destinationModel.setValue(destinationModel.getValue() + planningValueToBeRemoved);
                    returnDestinationModel.setValue(returnDestinationModel.getValue() + planningReturnValueToBeRemoved);

                    planning.setValue(planning.getValue() - planningValueToBeRemoved - planningReturnValueToBeRemoved);
                    planningCount = 1;
                } else {
                    planningCount++;
                }
                iterationCount++;
            } else {
                i = -1;
            }
            System.out.println("Iteration number " + iterationCount + "\n");
            System.out.println("The value for communication component is + " + communication.getValue());
            System.out.println("The value for planning component is + " + planning.getValue());
            System.out.println("The value for end component is + " + end.getValue() + "\n");
        }
    }

    public static void printLargeProcessModel(){

    }

    public static ProcessModel findMatchingDestinationObject(ProcessModel pm, List<ProcessModel> processModelList){
        for (ProcessModel processModel : processModelList){
            if (pm.getOutputDestination().equals(processModel.getName())){
                return processModel;
            }
        }
        System.out.println("No matching process models");
        return null;
    }

    public static ProcessModel findMatchingReturnDestinationObject(ProcessModel pm, List<ProcessModel> processModelList){
        for (ProcessModel processModel : processModelList){
            if (pm.getReturnOutputDestination().equals(processModel.getName())){
                return processModel;
            }
        }
        System.out.println("No matching process models");
        return null;
    }
}