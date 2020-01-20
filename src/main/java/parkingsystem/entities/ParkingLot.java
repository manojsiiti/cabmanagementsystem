package parkingsystem.entities;

import java.util.List;

public class ParkingLot {
    private int totalSlot;
    private int entryPoints;
    private int exitPoints;

    private List<ParkingSlot> parkingSlotList;

    public List<ParkingSlot> getParkingSlotList() {
        return parkingSlotList;
    }

    public void setParkingSlotList(List<ParkingSlot> parkingSlotList) {
        this.parkingSlotList = parkingSlotList;
    }

    public ParkingLot() {
    }

    public ParkingLot(int totalSlot, int entryPoints, int exitPoints) {
        this.totalSlot = totalSlot;
        this.entryPoints = entryPoints;
        this.exitPoints = exitPoints;
    }

    public int getTotalSlot() {
        return totalSlot;
    }

    public void setTotalSlot(int totalSlot) {
        this.totalSlot = totalSlot;
    }

    public int getEntryPoints() {
        return entryPoints;
    }

    public void setEntryPoints(int entryPoints) {
        this.entryPoints = entryPoints;
    }

    public int getExitPoints() {
        return exitPoints;
    }

    public void setExitPoints(int exitPoints) {
        this.exitPoints = exitPoints;
    }
}
