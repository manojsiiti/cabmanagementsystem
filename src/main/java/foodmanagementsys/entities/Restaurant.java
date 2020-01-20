package foodmanagementsys.entities;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private int limit;
    private String name;
    private List<Item> menu = new ArrayList<>();;
    private List<Order> orders = new ArrayList<>();

    public Restaurant() {
    }

    public Restaurant(int limit,String name , List<Item> menu) {
        this.limit = limit;
        this.menu = menu;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Item> getMenu() {
        return menu;
    }

    public void setMenu(List<Item> menu) {
        this.menu = menu;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String toString(){
        return name;
    }
}
