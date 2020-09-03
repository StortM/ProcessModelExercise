public class ProcessModel {
    private String name;
    private int timeFrame;
    private String outputDestination;
    private double outputPercent;
    private String returnOutputDestination;
    private double returnOutputPercent;
    private double value;

    public ProcessModel(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(int timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getOutputDestination() {
        return outputDestination;
    }

    public void setOutputDestination(String outputDestination) {
        this.outputDestination = outputDestination;
    }

    public double getOutputPercent() {
        return outputPercent;
    }

    public void setOutputPercent(double outputPercent) {
        this.outputPercent = outputPercent;
    }

    public String getReturnOutputDestination() {
        return returnOutputDestination;
    }

    public void setReturnOutputDestination(String returnOutputDestination) {
        this.returnOutputDestination = returnOutputDestination;
    }

    public double getReturnOutputPercent() {
        return returnOutputPercent;
    }

    public void setReturnOutputPercent(double returnOutputPercent) {
        this.returnOutputPercent = returnOutputPercent;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ProcessModel(String name, int timeFrame, String outputDestination, double outputPercent, String returnOutputDestination, double returnOutputPercent, double value) {
        this.name = name;
        this.timeFrame = timeFrame;
        this.outputDestination = outputDestination;
        this.outputPercent = outputPercent;
        this.returnOutputDestination = returnOutputDestination;
        this.returnOutputPercent = returnOutputPercent;
        this.value = value;
    }

    public ProcessModel(String name, int timeFrame, String outputDestination, double outputPercent, double value) {
        this.name = name;
        this.timeFrame = timeFrame;
        this.outputDestination = outputDestination;
        this.outputPercent = outputPercent;
        this.value = value;
    }

    public ProcessModel(String name, int timeFrame, double value) {
        this.name = name;
        this.timeFrame = timeFrame;
        this.value = value;
    }
}
