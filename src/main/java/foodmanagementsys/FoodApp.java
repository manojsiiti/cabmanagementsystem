package foodmanagementsys;

import foodmanagementsys.controller.ExecuteOrder;
import foodmanagementsys.entities.Command;
import foodmanagementsys.services.FoodService;
import java.util.*;

public class FoodApp {

    public static void main(String arg[]) {

        FoodService service  =new FoodService();
        service.buildRestaurents();

        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        List<String> commands = new ArrayList<>();

        while (!command.equals("run")) {
            commands.add(command);
            command = sc.nextLine();
        }

        List<Command> sortedCommands = sortedCommands(commands);
        ExecuteOrder executeOrder = new ExecuteOrder();
        executeOrder.execute(sortedCommands);
        System.out.println("===============");
        System.out.println(FoodService.orderMap.toString());

        //System.out.println(sortedCommands.toString());


    }

    private static List<Command> sortedCommands(List<String> commands) {
        List<Command> list = new ArrayList<>();
        for(String command: commands) {
            Integer time = Integer.parseInt(command.split("-")[0]);
            Command command1 = new Command(time, command);
            list.add(command1);
        }

        Collections.sort(list, new Comparator<Command>() {
            @Override
            public int compare(Command o1, Command o2) {
                return o1.getTime()>= o2.getTime() ?1: -1;
            }
        });

        return  list;
    }
}
