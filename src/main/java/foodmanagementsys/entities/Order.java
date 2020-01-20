package foodmanagementsys.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private List<Item> itemList = new ArrayList<>();
    private int totalPrice;

    public Order() {
    }

    public Order(String id, List<Item> itemList) {
        this.id = id;
        this.itemList = itemList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String toString(){
        return "["+id+" : "+itemList.toString()+"]";
    }
}
