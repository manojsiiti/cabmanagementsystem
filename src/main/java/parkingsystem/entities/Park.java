package parkingsystem.entities;

public class Park {
    private Car car;
    private int entryPoint;
    private int exitPoint;
    private Choice choice;

    public Park() {
    }

    public Park(Car car, int entryPoint, Choice strategy, int exitPoint) {
        this.car = car;
        this.entryPoint = entryPoint;
        this.choice = strategy;
        this.exitPoint = exitPoint;
    }

    public int getExitPoint() {
        return exitPoint;
    }

    public void setExitPoint(int exitPoint) {
        this.exitPoint = exitPoint;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(int entryPoint) {
        this.entryPoint = entryPoint;
    }
}
