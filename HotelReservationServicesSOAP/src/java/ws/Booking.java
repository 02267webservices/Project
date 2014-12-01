package ws;

import hotelreservationservices.CreditCardType;
import hotelreservationservices.HotelType;
/**
 *
 * @author Emil
 */
public class Booking {
    
    // New
    
    public Booking(HotelType hotel) {
        
        this.hotel = hotel;
        
    }
    
    // Properties
    
    private final HotelType hotel;
    private CreditCardType creditCard;
    private boolean booked = false;
    private boolean cancelled = false;
    
    public HotelType getHotel() {
        
        return hotel;
        
    }
    
    public CreditCardType getCreditCard() {
        
        return creditCard;
        
    }
    
    public boolean getBooked() {
        
        return booked;
        
    }
    
    public boolean getCancelled() {
        
        return cancelled;
        
    }
    
    // Functions
    
    public void book() {
        
        booked = true;
        
    }
    
    public void book(CreditCardType value) {
        
        creditCard = value;
        booked = true;
        
    }
    
    public void cancel() {
        
        cancelled = true;
        
    }
    
}