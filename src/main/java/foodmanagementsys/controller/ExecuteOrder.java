package foodmanagementsys.controller;

import foodmanagementsys.entities.Command;
import foodmanagementsys.entities.Item;
import foodmanagementsys.entities.Order;
import foodmanagementsys.services.FoodService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExecuteOrder {

    public void execute(List<Command> list) {
        FoodService fs = new FoodService();
        for(Command c: list) {
            String comm[] = c.getCommand().split("-");
            String cname = comm[1];
            switch (cname) {
                case "po":{
                    System.out.println(fs.placeOrder(getOrder(c.getCommand())));
                    break;
                }
                case "up":{
                    System.out.println(fs.updatePrice(comm[2],comm[3],Integer.parseInt(comm[4])));
                    break;
                }
                case "do":{
                    System.out.println(fs.dispatchOrder(comm[2]));
                    break;
                }
            }
        }

    }

    private Order getOrder(String command) {
        Order o = new Order();

        String []list = command.split("-");
        o.setId(list[2]);
        for( int i=3;i<list.length;i++){
            Item item = new Item();
            item.setId(list[i]);
            item.setName(list[i]);
            o.getItemList().add(item);
        }

        return o;
    }

    private String getCommandName(String command){
        return command.split("-")[1];
    }
}
