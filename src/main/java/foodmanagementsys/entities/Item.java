package foodmanagementsys.entities;

public class Item {

    private String name;
    private String id;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Item() {
    }

    public Item(String name, String id, int price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public boolean equals(Item i){
        return this.id.equals(i.getId());
    }

    public String toString(){
        return "<<ItemName: "+this.getName()+ " price: "+price+">>";
    }
}
