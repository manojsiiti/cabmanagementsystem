package cabbooking.entities;


public class User {
    private String id;
    private String name;

    public User(){}
    public User(String name, String id){
        super();
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
