package parkingsystem.controller;

import parkingsystem.entities.Park;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import parkingsystem.entities.ParkingLot;
import parkingsystem.entities.ParkingSlot;
import parkingsystem.service.ParkingService;

import java.util.List;

@RestController
public class ParkingController {

    @Autowired
    private ParkingService service;

    @RequestMapping(method = RequestMethod.POST, value = "/create-parking-lot")
    public String createParkingSlot(@RequestBody ParkingLot parkingLot) {
        try {
            return service.createParkigSlots(parkingLot);
        }catch (RuntimeException ex){
            return ex.getMessage();
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/park")
    public String parkMyCar(@RequestBody Park park) {
        try {
            ParkingSlot slot = service.parkMyCar(park);
            return "Allocated slot number: " + slot.getId();
        }catch (RuntimeException ex){
            return ex.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-car-slot/{forNUmber}")
    public String getCarSlotForNumber(@PathVariable("forNUmber") String forNUmber) {
        try {
            ParkingSlot slot = service.getCarSlotForNumber(forNUmber);
            return  ""+slot.getId();
        }catch (RuntimeException ex){
            return ex.getMessage();
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/get-car-slots/{forColor}")
    public String getAllSlotsForColor(@PathVariable("forColor") String forColor) {
        try {
            List<ParkingSlot> parkingSlots =  service.getAllSlotsForColor(forColor);
            String msg = "";
            for(ParkingSlot slot: parkingSlots){
                msg = msg + slot.toString() + " || ";
            }
            return msg;
        }catch (RuntimeException ex){
            return ex.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/leave/{slotNumber}")
    public String getAllSlotsForColor(@PathVariable("slotNumber") int slotNumber) {
        try {
            ParkingSlot slot = service.leaveSlot(slotNumber);
            return "Slot number " + slot.getId() +" is Empty";
        }catch (RuntimeException ex){
            return ex.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/status")
    public String getStatus() {
        try {
            List<ParkingSlot> parkingSlots =  service.getStatus();
            return parkingSlots.toString();
        }catch (RuntimeException ex){
            return ex.getMessage();
        }
       // return service.getStatus();
    }

}
