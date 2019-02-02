package servermanagement.entities;

public class ResultRequest {
    private String connId;
    private String timeLeft;

    public ResultRequest(){}

    public ResultRequest(String connId, String timeLeft){
        this.connId = connId;
        this.timeLeft = timeLeft;
    }

    public String getConnId() {
        return connId;
    }

    public void setConnId(String connId) {
        this.connId = connId;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }
}
