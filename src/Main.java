import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //testing process model for method printSmallProcessModel
        List<ProcessModel> smallProcessModelList = new ArrayList<>();

        smallProcessModelList.add(new ProcessModel("communication", 1, "planning", 1, 1));
        smallProcessModelList.add(new ProcessModel("planning", 2, "end", 0.8, "communication", 0.2, 0));
        smallProcessModelList.add(new ProcessModel("end", 1, 0));

        //printSmallProcessModel(smallProcessModelList);

        //testing process model for method printLargeProcessModel
        List<ProcessModel> largeProcessModelList = new ArrayList<>();

        largeProcessModelList.add(new ProcessModel("communication", 1, "planning", 1, 1));
        largeProcessModelList.add(new ProcessModel("planning", 2, "modelling", 0.8, "communication", 0.2, 0));
        largeProcessModelList.add(new ProcessModel("modelling", 2, "construction", 0.8, "modelling", 0.2, 0));
        largeProcessModelList.add(new ProcessModel("construction", 4, "deployment", 0.8, "communication", 0.2, 0));
        largeProcessModelList.add(new ProcessModel("deployment", 1, 0));

        printLargeProcessModel(largeProcessModelList);

    }

    public static void printSmallProcessModel(List<ProcessModel> processModelList) {
        ProcessModel communication = processModelList.get(0);
        ProcessModel planning = processModelList.get(1);
        ProcessModel end = processModelList.get(2);

        //counter for counting iterations in planning
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
            if (i == 0) {
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
            else if (i == 1 || i == 2) {
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

    public static void printLargeProcessModel(List<ProcessModel> processModelList){
        ProcessModel communication = processModelList.get(0);
        ProcessModel planning = processModelList.get(1);
        ProcessModel modelling = processModelList.get(2);
        ProcessModel construction = processModelList.get(3);
        ProcessModel deployment = processModelList.get(4);

        //counter for counting iterations in processmodels
        int planningCount = 1;
        int modellingCount = 1;
        int constructionCount = 1;

        //counter for iterations
        //can't use i because we sometimes skip iterations. See loop below
        int iterationCount = 1;

        //initial print
        DecimalFormat df = new DecimalFormat("####0.0000");

        System.out.println("Iteration number " + iterationCount + "\n");
        System.out.println("The value for communication component is " + df.format(communication.getValue()));
        System.out.println("The value for planning component is + " + df.format(planning.getValue()));
        System.out.println("The value for modelling component is + " + df.format(modelling.getValue()));
        System.out.println("The value for construction component is + " + df.format(construction.getValue()));
        System.out.println("The value for deployment component is + " + df.format(deployment.getValue()) + "\n");

        for (int i = 0; deployment.getValue() < 0.95; i++) {
            //communication iteration
            if (i == 0) {
                runIteration(communication,processModelList);
                iterationCount++;
            }

            //planning iteration
            else if (i == 1 || i == 2) {
                if (planningCount == planning.getTimeFrame()) {
                    runIterationWithReturnVal(planning,processModelList);

                    planningCount = 1;
                } else {
                    planningCount++;
                }
                iterationCount++;
            }
            //modelling iteration
            //mulig bug: check udregning
            else if (i == 3 || i == 4){
                if (modellingCount == modelling.getTimeFrame()) {
                    runIterationWithReturnVal(modelling,processModelList);
                    modellingCount = 1;
                }
                else{
                    modellingCount++;
                }
                iterationCount++;
            }

            //construction iteration
            else if (i== 5 || i == 6 || i == 7 || i == 8){
                if (constructionCount == construction.getTimeFrame()){
                    runIterationWithReturnVal(construction,processModelList);
                    constructionCount = 1;
                }
                else{
                    constructionCount++;
                }
                iterationCount++;
            }

            else {
                i = -1;
                continue;
            }

            System.out.println("Iteration number " + iterationCount + "\n");
            System.out.println("The value for communication component is " + df.format(communication.getValue()));
            System.out.println("The value for planning component is + " + df.format(planning.getValue()));
            System.out.println("The value for modelling component is + " + df.format(modelling.getValue()));
            System.out.println("The value for construction component is + " + df.format(construction.getValue()));
            System.out.println("The value for deployment component is + " + df.format(deployment.getValue()) + "\n");
        }
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

    public static void runIterationWithReturnVal(ProcessModel processModel, List<ProcessModel> processModelList){
            double ValueToBeRemoved = processModel.getValue() * processModel.getOutputPercent();
            double ReturnValueToBeRemoved = processModel.getValue() * processModel.getReturnOutputPercent();

            ProcessModel destinationModel = findMatchingDestinationObject(processModel,processModelList);
            ProcessModel returnDestinationModel = findMatchingReturnDestinationObject(processModel,processModelList);

            destinationModel.setValue(destinationModel.getValue() + ValueToBeRemoved);
            returnDestinationModel.setValue(returnDestinationModel.getValue() + ReturnValueToBeRemoved);

            processModel.setValue(processModel.getValue() - ValueToBeRemoved - ReturnValueToBeRemoved);
    }

    public static void runIteration(ProcessModel processModel, List<ProcessModel> processModelList){
        double ValueToBeRemoved = processModel.getValue() * processModel.getOutputPercent();

        ProcessModel destinationModel = findMatchingDestinationObject(processModel,processModelList);

        destinationModel.setValue(destinationModel.getValue() + ValueToBeRemoved);

        processModel.setValue(processModel.getValue() - ValueToBeRemoved);

    }

}