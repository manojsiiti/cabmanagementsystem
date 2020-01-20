package parkingsystem.service;

import parkingsystem.entities.SlotDistance;
import parkingsystem.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private int totalSlots = 10;
    private int entryPoints = 3;
    private int exitPoints = 2;

    private ParkingLot parkingLot = null;

    private int[][] distanceFromEntry = new int[entryPoints][totalSlots];
    private int[][] distanceFromExit = new int[exitPoints][totalSlots];


    public String createParkigSlots(ParkingLot parkingLot){
        validateParkingLotRequest(parkingLot);
        this.totalSlots = parkingLot.getTotalSlot();
        this.entryPoints = parkingLot.getEntryPoints();
        this.exitPoints = parkingLot.getExitPoints();

        distanceFromEntry = new int[entryPoints][totalSlots];
        distanceFromExit = new int[exitPoints][totalSlots];
        initParkingSlots(parkingLot);
        fillDistance(distanceFromEntry); // from Entry
        fillDistance(distanceFromExit); // form Exit
        return "Created a parking lot with "+totalSlots + " slots and "+entryPoints+ " entry points and "+exitPoints+ " exit points";
    }

    public ParkingSlot parkMyCar(Park park) {
        validateParkingLot();
        validateParkRequest(park);
        ParkingSlot mySlot = findMySlot(park);
        if(mySlot==null){
            throw new RuntimeException("Slot is not available for Parking");
        }
        mySlot.setState(State.Full);
        mySlot.setParkedCar(park.getCar());
        return mySlot;
    }

    public ParkingSlot getCarSlotForNumber(String rNumber) {
        validateParkingLot();
        if(null == rNumber){
            throw new RuntimeException("Please provide valid Number");
        }
        List<ParkingSlot> parkingSlots =parkingLot.getParkingSlotList().stream()
                .filter(slot->slot.getParkedCar()!=null)
                .filter(slot->slot.getParkedCar().getrNumber().equals(rNumber))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(parkingSlots)){
            throw new RuntimeException("Car not found with Number="+rNumber);
        }else {
            return parkingSlots.get(0);
        }
    }

    public List<ParkingSlot> getAllSlotsForColor(String color){
        validateParkingLot();
        if(null == color){
            throw new RuntimeException("Please provide valid color");
        }
        List<ParkingSlot> parkingSlots =parkingLot.getParkingSlotList().stream()
                .filter(slot->slot.getParkedCar()!=null)
                .filter(slot->slot.getParkedCar().getColor().equals(color))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(parkingSlots)){
            throw new RuntimeException("Car not found with color="+color);
        }else {
            return parkingSlots;
        }
    }

    public List<ParkingSlot> getStatus(){
        validateParkingLot();
        return parkingLot.getParkingSlotList();
    }

    public ParkingSlot leaveSlot(int slotNumber) {
        validateParkingLot();
        ParkingSlot slot = parkingLot.getParkingSlotList().get(slotNumber);
        if(slot.getState()==State.Empty){
            throw new RuntimeException("No car available at this Parking slot");
        }
        slot.setState(State.Empty);
        slot.setParkedCar(null);
        return slot;
    }

    private void validateParkingLotRequest(ParkingLot parkingLot){
        if(null == parkingLot || parkingLot.getExitPoints()<=0 || parkingLot.getEntryPoints() <=0 || parkingLot.getTotalSlot()<=0) {
            throw new RuntimeException("Invalid parking lot request");
        }
    }

    private void validateParkingLot(){
        if(parkingLot==null || CollectionUtils.isEmpty(parkingLot.getParkingSlotList())){
            throw new RuntimeException("Paring lot is not created");
        }
    }

    private void validateParkRequest(Park park) {
        Integer entryPoint  = park.getEntryPoint();
        Integer exitPoint  = park.getExitPoint();
        Car car = park.getCar();
        if(entryPoint<0 || entryPoint > entryPoints-1) {
            throw new RuntimeException("Provided Entry Point is not Available in our list [ 0"+"-"+(entryPoints-1) +"]");
        }
        if(exitPoint<0 || exitPoint > exitPoints-1) {
            throw new RuntimeException("Provided Exit Point is not Available in our list [ 0"+"-"+(exitPoint-1) +"]");
        }
        if(car==null){
            throw new RuntimeException("Please provide the valid car details");
        }
        if(park.getChoice()==null){
            throw new RuntimeException("Please provide detail about you choice");
        }
    }

    private ParkingSlot findMySlot(Park park){
        int[] dis = null;
        if(park.getChoice()== Choice.Near_Entry) {
            dis = distanceFromEntry[park.getEntryPoint()]; // distance of this entry point from all Slots
        }else if(park.getChoice()== Choice.Near_Exit) {
            dis = distanceFromExit[park.getExitPoint()]; // distance of this exit point from all Slots
        }

        //Sort all the parking lot by distance from Entry or Exit based on User choise
        List<SlotDistance> parkingsList = new ArrayList<>(dis.length);
        for(int i=0;i<dis.length;i++){
            parkingsList.add(new SlotDistance(parkingLot.getParkingSlotList().get(i), dis[i]));
        }

        Collections.sort(parkingsList, new Comparator<SlotDistance>() {
            @Override
            public int compare(SlotDistance o1, SlotDistance o2) {
                return o1.getDis()<=o2.getDis()?-1:1;
            }
        });

        System.out.println(parkingsList);
        ParkingSlot mySlot = null;
        for(SlotDistance parkings: parkingsList){
            if(parkings.getParkingSlot().getState()==State.Full) continue;
            mySlot = parkings.getParkingSlot();
            break;
        }
        parkingsList =  null;
        return mySlot;
    }

    private void initParkingSlots( ParkingLot pl){
        parkingLot = new ParkingLot();
        List<ParkingSlot> allParkingSlots = new ArrayList<>(pl.getTotalSlot());

        for(int i=0;i<pl.getTotalSlot();i++){
            allParkingSlots.add( new ParkingSlot(i, State.Empty, null));
        }
        parkingLot.setParkingSlotList(allParkingSlots);
    }

    private void fillDistance(int[][] distance) {
        for(int i=0;i<distance.length;i++){
            for(int j=0;j<distance[i].length;j++){
                distance[i][j] = getDisatance(j, i); // get distance of entrypoint i from slot j
            }
        }
    }

    //This method can be enhanced to get the distance
    private int getDisatance(int slotNumber, int entryOrExitPoint) {
        Random r = new Random();
        int low = 10;
        int high = 100;
        return r.nextInt(high-low) ;
    }

}
