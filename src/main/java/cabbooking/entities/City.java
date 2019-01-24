package cabbooking.entities;

public class City {
    private String id;

    private String name;

    public City(){}
    public City(String id, String name){
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
