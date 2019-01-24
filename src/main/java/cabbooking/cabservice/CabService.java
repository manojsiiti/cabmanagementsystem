package cabbooking.cabservice;

import cabbooking.entities.Booking;
import cabbooking.entities.Cab;
import cabbooking.entities.CabState;
import cabbooking.entities.City;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CabService {

    //Only bangalore is registered by default
    private List<City> registeredCities =  new ArrayList<>(Arrays.asList(new City("bangalore", "Bangalore"), new City("kota", "Kota")));
    private List<Cab> registeredCabs = new ArrayList<>(Arrays.asList(new Cab(UUID.randomUUID().toString(),""+System.currentTimeMillis(), CabState.IDLE, new City("bangalore", "Bangalore"))));

    /**
     * To get all registered cabs
     * @return
     */
    public List<City> getRegisteredCities(){
        return registeredCities;
    }

    /**
     * to get all registered cities
     * @param city
     */
    public void registerCity(City city){
        registeredCities.add(city);
    }

    /**
     * to change the city of given cab
     * @param city
     * @param cabId
     */
    public void changeCity(City city,String cabId) {
        boolean cabFound=false;
        for(Cab cab: registeredCabs){
            if(cab.getCabId().equalsIgnoreCase(cabId)){
                cabFound = true;
                cab.setCurrentCity(city); break;
            }
        }
        if(!cabFound){
            throw new RuntimeException("Cab not found");
        }
    }

    /**
     * to change the state of given cab
     * @param state
     * @param cabId
     */
    public void changeState(String state,String cabId) {
        boolean cabFound=false;
        for(Cab cab: registeredCabs){
            if(cab.getCabId().equalsIgnoreCase(cabId)){
                cabFound = true;
                cab.setCurrentCabState(CabState.valueOf(state));
                break;
            }
        }
        if(!cabFound){
            throw new RuntimeException("Cab not found");
        }
    }

    public List<Cab> getRegisteredCabs(){
        return registeredCabs;
    }

    public void registerCab(Cab cab){
        List<City> list = registeredCities
                .stream()
                .filter(city->city.getId().equalsIgnoreCase(cab.getCurrentCity().getId()))
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(list)){
            throw new RuntimeException("city is not registered");
        }

        cab.setCabId(UUID.randomUUID().toString());
        if(cab.getCurrentCabState()==null){
            cab.setCurrentCabState(CabState.IDLE);
        }
        if(cab.getLastTripEndTime()==null){
            cab.setLastTripEndTime(""+System.currentTimeMillis());
        }
        registeredCabs.add(cab);
    }

    //returns cabId
    public String bookMyCab(Booking booking){
        validateBooking(booking);
        City city = booking.getFromCity();
        City toCity = booking.getToCity();

        List<Cab> cabList =  registeredCabs
                .stream()
                .filter(cab->cab.getCurrentCity().getId().equals(city.getId()))
                .filter(cab-> cab.getCurrentCabState()==CabState.IDLE)
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(cabList)){
            return "Cab not available in the city";
        }

        Cab  myCab = findMyCab(cabList);
        runCab(myCab, toCity);

        return myCab.getCabId();

    }

    private void validateBooking(Booking booking){
        boolean isFromCityRegistered = false;
        boolean isToCityRegistered = false;
        City city = booking.getFromCity();
        City toCity = booking.getToCity();

        for(City c: registeredCities){
            if(c.getId().equals(city.getId())){
                isFromCityRegistered = true;
            }
            if(c.getId().equals(toCity.getId())){
                isToCityRegistered = true;
            }
        }
        if(!isFromCityRegistered){
            throw new RuntimeException("Source City not registered") ;
        }

        if(!isToCityRegistered){
            throw new RuntimeException("Destination City not registered") ;
        }

    }

    private Cab findMyCab(List<Cab> cabs){
        Cab myCab = null;
        long currentTime = System.currentTimeMillis();
        long timeDiff = 0;

        for(Cab cab: cabs){
            long td = currentTime - Long.parseLong(cab.getLastTripEndTime());
            if(timeDiff < td){
                myCab = cab;
                timeDiff = td;
            }
        }
        return myCab;

    }

    private void runCab(Cab cab, City toCity){


        Runnable runnable  = new Runnable() {
            @Override
            public void run() {
                try {
                    cab.setCurrentCabState(CabState.ON_TRIP);
                    cab.setCurrentCity(new City("no-the-way","On The Way"));
                    Thread.sleep(10000);
                    cab.setCurrentCabState(CabState.IDLE);
                    cab.setLastTripEndTime(""+System.currentTimeMillis());
                    cab.setCurrentCity(toCity);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();
    }


}
