package parkingsystem.entities;

public class Car {
    private String color;
    private String rNumber;//Registration NUmber

    public Car() {

    }
    public Car(String color, String rNumber) {
        this.color = color;
        this.rNumber = rNumber;
    }

    public String toString(){
        return "(number="+rNumber+" color="+color+")\n";
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setrNumber(String rNumber) {
        this.rNumber = rNumber;
    }

    public String getColor() {
        return color;
    }

    public String getrNumber() {
        return rNumber;
    }
}
