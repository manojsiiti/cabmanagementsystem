package cabbooking.entities;

public class Cab {

    private String cabId;
    private String lastTripEndTime;
    private CabState currentCabState;
    private City currentCity;

    public Cab(){}
    public Cab(String cabId, String lastTripEndTime, CabState cabState, City currentCity){
        super();
        this.cabId = cabId;
        this.lastTripEndTime = lastTripEndTime==null?""+System.currentTimeMillis():lastTripEndTime;
        this.currentCabState = cabState==null?CabState.IDLE:cabState;
        this.currentCity = currentCity;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public void setLastTripEndTime(String lastTripEndTime) {
        this.lastTripEndTime = lastTripEndTime;
    }

    public void setCurrentCabState(CabState currentCabState) {
        this.currentCabState = currentCabState;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public String getCabId() {
        return cabId;
    }

    public String getLastTripEndTime() {
        return lastTripEndTime;
    }

    public CabState getCurrentCabState() {
        return currentCabState;
    }

    public City getCurrentCity() {
        return currentCity;
    }


}
