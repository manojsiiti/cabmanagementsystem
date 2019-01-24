package cabbooking.cabController;

import cabbooking.cabservice.CabService;
import cabbooking.entities.Booking;
import cabbooking.entities.Cab;
import cabbooking.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class CabController {

    @Autowired
    private CabService cabService;

    @RequestMapping(method = RequestMethod.POST, value = "/book-my-cab")
    public String bookMyCab(@RequestBody Booking myBooking){
        return cabService.bookMyCab(myBooking);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cabs")
    public List<Cab> getAllCabs(){
        return cabService.getRegisteredCabs();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cities")
    public List<City> getAllCities(){
        return cabService.getRegisteredCities();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register-cab")
    public void addCab(@RequestBody Cab cab){
         cabService.registerCab(cab);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register-city")
    public void addCity(@RequestBody City city){
        cabService.registerCity(city);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change-city/{cabid}")
    public void changeCity(@RequestBody City city, @PathVariable("cabid") String cabid){
        cabService.changeCity(city, cabid);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change-cabstate/{cabid}/{state}")
    public void changeCity(@PathVariable("state") String state, @PathVariable("cabid") String cabid){
        cabService.changeState(state, cabid);
    }


}
