package parkingsystem.entities;
//this is entity to store the distance from given exit or entry point
public class SlotDistance {
    private ParkingSlot parkingSlot;
    private int dis;

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public int getDis() {
        return dis;
    }

    public void setDis(int dis) {
        this.dis = dis;
    }

    public SlotDistance(ParkingSlot parkingSlot, int dis) {
        this.parkingSlot = parkingSlot;
        this.dis = dis;
    }

    public String toString() {
        return dis + " " + parkingSlot.toString()+"\n";
    }
}