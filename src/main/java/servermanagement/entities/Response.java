package servermanagement.entities;

public class Response {
    private Stat stat;

    public Response() {}
    public Response(Stat stat){
        this.stat = stat;
    }
    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }
}
