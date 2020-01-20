package parkingsystem.entities;

public class ParkingSlot {
    private int id;
    private State state;
    private Car parkedCar;

    public ParkingSlot() {
    }

    public ParkingSlot(int id, State state, Car parkedCar) {
        this.id = id;
        this.state = state;
        this.parkedCar = parkedCar;
    }

    public String toString(){
        return "(id="+id+" State="+state.name()+ " parkedCar="+((parkedCar!=null)?parkedCar.toString():"none")+") \n";
    }

    public Car getParkedCar() {
        return parkedCar;
    }

    public void setParkedCar(Car parkedCar) {
        this.parkedCar = parkedCar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
