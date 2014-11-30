/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;

import ws.HotelsType;
import ws.CreditCardType;


/**
 *
 * @author gravr
 */
public class NiceViewTest {
    
    @Test
    public void NiceViewServiceTesting(){
        
        //Testing getHotels
        
        try {
            DatatypeFactory df = DatatypeFactory.newInstance();
            XMLGregorianCalendar arrivalDate = df.newXMLGregorianCalendar("2015-01-01");
            XMLGregorianCalendar departureDate = df.newXMLGregorianCalendar("2015-03-01");
            HotelsType hotelsHolder = getHotels("some city 1",arrivalDate,departureDate);
            
            assertNotNull(hotelsHolder);
            
            if (!hotelsHolder.hotels.isEmpty()){
                for (int i = 0; i < hotelsHolder.hotels.size(); i++) {
                    System.out.println(hotelsHolder.hotels.get(i).getName()+ "\n" +
                           hotelsHolder.hotels.get(i).getAddress()+ "\n" +
                           hotelsHolder.hotels.get(i).getBookingNumber());
                }
            }
        }catch (Exception ex) {
        }
        
        //Testing bookHotel
        
        CreditCardType creditCard = new CreditCardType();
        creditCard.expirationMonth = 5;
        creditCard.expirationYear = 9;
        creditCard.name = "Thor-Jensen Claus";
        creditCard.number = "50408825";
        
         try {
            boolean bookingSuccess = bookHotel(2,creditCard);
            System.out.println("True if booking of hotel was succesful: " +bookingSuccess);
            assertTrue(bookingSuccess);
        }catch (BookHotelFault ex){
            
            System.out.println("Booking failed with message: " +ex.getMessage());
        }
        
        //Cancel bookHotel
         
         try {
            boolean cancelSuccess = cancelHotel(2);
            System.out.println("True if cancallation of hotel was succesful: " +cancelSuccess);
            assertTrue(cancelSuccess);
        }catch (CancelHotelFault ex){
            
            System.out.println("Cancellation failed with message: " +ex.getMessage());
        } 
    }
    

    private static HotelsType getHotels(java.lang.String city, javax.xml.datatype.XMLGregorianCalendar arrival, javax.xml.datatype.XMLGregorianCalendar departure) {
        ws.HotelReservationService service = new ws.HotelReservationService();
        ws.HotelReservationServices port = service.getHotelReservationServicesBindingPort();
        return port.getHotels(city, arrival, departure);
    }

    private static boolean bookHotel(int bookingNumber, ws.CreditCardType creditCard) throws BookHotelFault {
        ws.HotelReservationService service = new ws.HotelReservationService();
        ws.HotelReservationServices port = service.getHotelReservationServicesBindingPort();
        return port.bookHotel(bookingNumber, creditCard);
    }

    private static boolean cancelHotel(int bookingNumber) throws CancelHotelFault {
        ws.HotelReservationService service = new ws.HotelReservationService();
        ws.HotelReservationServices port = service.getHotelReservationServicesBindingPort();
        return port.cancelHotel(bookingNumber);
    }
    
    
}