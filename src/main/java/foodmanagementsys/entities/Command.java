package foodmanagementsys.entities;

public class Command {
    private int time;
    private String command;

    public Command(int time, String command) {
        this.time = time;
        this.command = command;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String toString(){
        return this.time+" : "+command;
    }
}
