package servermanagement.entities;

public class Request {
    private String connId;
    private String timeOut;
    private String currentTime;

    public Request(){}

    public Request(String connId, String timeOut){
        this.connId = connId;
        this.timeOut = timeOut;
        this.currentTime = ""+System.currentTimeMillis();
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getConnId() {
        return connId;
    }

    public void setConnId(String connId) {
        this.connId = connId;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public boolean equals(Request r ){
        return this.getConnId().equals(r.getConnId());
    }
}
