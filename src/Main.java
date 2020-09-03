public class Main {
    public static void main(String[] args) {
        ProcessModel processModel = new ProcessModel();

        ProcessModel communication = new ProcessModel("communication",1,"planning",1,1);
        ProcessModel planning = new ProcessModel("planning",2,"end",0.8,"communication",0.2,0);
        ProcessModel end = new ProcessModel("end",1,0);

        for (int i = 0; i < 20; i++){
            System.out.println("Iteration number " + i + "\n");
            System.out.println("The value for communication component is + " + communication.getValue());
            System.out.println("The value for planning component is + " + planning.getValue());
            System.out.println("The value for end component is + " + end.getValue());

            planning.setValue(planning.getValue() + communication.getValue()*communication.getOutputPercent());

            end.setValue();


        }
    }
}
