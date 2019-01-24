package cabbooking.entities;


public class Booking {

    private String bookingId;
    private User user;
    private City fromCity;
    private City toCity;

    public Booking(){}

    public Booking(String bookingId, User user, City fromCity, City toCity) {
        super();
        this.bookingId = bookingId;
        this.user = user;
        this.fromCity = fromCity;
        this.toCity = toCity;
    }

    public String getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public City getFromCity() {
        return fromCity;
    }

    public City getToCity() {
        return toCity;
    }
}
