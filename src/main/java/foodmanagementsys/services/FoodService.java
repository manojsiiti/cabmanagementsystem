package foodmanagementsys.services;

import foodmanagementsys.entities.Item;
import foodmanagementsys.entities.Order;
import foodmanagementsys.entities.Restaurant;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class FoodService {

    private static List<Restaurant> restaurants = new ArrayList<>();
    public static Map<Order, Restaurant> orderMap = new HashMap<>();
    public String placeOrder(Order order) {
        List<Item> itmes = order.getItemList();

        Restaurant r = findMyRes(itmes);
        if(r==null) {
            return  "Not Found";
        }else {
            updatePrice(r, itmes);
            r.getOrders().add(order);
            orderMap.put(order, r);
            return  r.getName() + " Price is: "+totalPrice(itmes, r) +" "+order.toString();
        }
    }

    private void updatePrice(Restaurant r,List<Item> itmes){
        List<Item> m = r.getMenu();
        for(Item i: m){
            for(Item mi:itmes){
                if(i.equals(mi)){
                    mi.setPrice(i.getPrice());
                }
            }
        }
    }

    public String updatePrice(String rName, String iId, int newPrce){
        List<Restaurant> rlist = restaurants.stream().filter(r->r.getName().equals(rName)).collect(Collectors.toList());
        boolean found = false;
        if(!CollectionUtils.isEmpty(rlist)) {
            List<Item> manue = rlist.get(0).getMenu();
            for(Item i: manue) {
                if(i.getId().equals(iId)){
                    found = true;
                    i.setPrice(newPrce);
                    break;
                }
            }
            if(found)
                return manue.toString();
            else{
                return "Not Found";
            }
        }

        return "Not Found";
    }

    public String dispatchOrder(String oid ){
        Order order = null;
        for(Order o: orderMap.keySet()){
            if(o.getId().equals(oid)){
                order = o;
                Restaurant r = orderMap.get(o);
                if(!r.getOrders().contains(order)){
                    return "Order already disptched";
                }
                r.getOrders().remove(o);
                break;
            }
        }
        if(order==null) {
            return "Not Found";
        }else {
            return order.toString();
        }
    }

    private Restaurant findMyRes(List<Item> myitmes){
        List<Restaurant> rs= new ArrayList<>();

        int total = myitmes.size();
        for(Restaurant r: restaurants) {
            List<Item> items =  r.getMenu();
            boolean allFound = false;
            int foundI = 0;
            for(Item item: items) {
                for(Item myI : myitmes ) {
                    if(myI.equals(item)) {
                        foundI++;
                    }
                }
            }
            if(foundI>=total) {
                rs.add(r);
            }
        }
        if(CollectionUtils.isEmpty(rs)) {
            return null;
        }
        int tp = Integer.MAX_VALUE;
        Restaurant myR = null;
        for(Restaurant r: rs) {
            if(r.getOrders().size()>=r.getLimit()) continue;
            int p = totalPrice(myitmes, r);
            if(p<tp){
                tp = p;
                myR = r;
            }
        }

        return myR;
    }

    private int totalPrice (List<Item> myitmes, Restaurant r) {
        List<Item> items = r.getMenu();
        int p = 0;
        for(Item i: myitmes ) {
            List<Item> fI = items.stream().filter(i2->i.equals(i2)).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(fI)){
                p = p + fI.get(0).getPrice();
            }

        }

        return p;
    }

    public void buildRestaurents() {
        List<Item> items = getItems();
        Restaurant r1 = new Restaurant(2, "r1", Arrays.asList(items.get(0), items.get(1), items.get(2)));
        //Restaurant r2 = new Restaurant(1, "r2", Arrays.asList(items.get(4), items.get(1), items.get(3)));
       // Restaurant r3 = new Restaurant(10, "r3", Arrays.asList(items.get(0), items.get(1), items.get(2), items.get(3), items.get(4)));
        //Restaurant r4 = new Restaurant(2, "r4", Arrays.asList(items.get(4), items.get(1), items.get(3)));
        restaurants.add(r1);
        //restaurants.add(r2);
        /*restaurants.add(r3);
        restaurants.add(r4);*/
    }

    private List<Item> getItems() {
        List<Item> items = new ArrayList<>();
         Item i1 = new Item("i1","i1",10);
         items.add(i1);

        Item i2 = new Item("i2","i2",13);
        items.add(i2);
        Item i3 = new Item("i3","i3",4);
        items.add(i3);
        Item i4 = new Item("i4","i4",87);
        items.add(i4);
        Item i5 = new Item("i1","i1",100);
        items.add(i5);


        return items;
    }
}
