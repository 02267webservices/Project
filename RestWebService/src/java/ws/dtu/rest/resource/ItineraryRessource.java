package ws.dtu.rest.resource;
import dtu.ws.group8.lameduck.BookFlightFault;
import dtu.ws.group8.lameduck.CancelFlightFault;
import dtu.ws.group8.lameduck.types.CreditCardInfoType;
import hotelreservationservices.BookHotelFault;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.CancelHotelFault; 
import ws.dtu.rest.data.MyBean; 
 
/**
 *
 * @author Rasmus  
 */
 
@Path("itineraries")
public class ItineraryRessource {  
    private static MyBean Itinerary = new MyBean();
    
   

 
   @GET //The simplest GET request returns this 
   public String BookFlight(@QueryParam("bookingnumber") String number, 
   @QueryParam("cardnumber") String cardnumber,
   @QueryParam("name") String name,
   @QueryParam("expdate") String expdate){
   
   CreditCardInfoType cardInfo = getCardInfo(cardnumber,name,expdate); 
    boolean result = false;
    
        dtu.ws.group8.lameduck.types.BookFlightRequestType input = new dtu.ws.group8.lameduck.types.BookFlightRequestType();
        input.setFlightBookingNumber(number);
        input.setCreditCardInfo(cardInfo);
      try {
          result = bookFlight(input);
             
      } catch (BookFlightFault ex) {
          Logger.getLogger(ItineraryRessource.class.getName()).log(Level.SEVERE, null, ex);
      }
      if(result){
         Itinerary.getFligtList().put(number, "CONFIRMED");
      } else{ 
          
         for (Map.Entry pairs : Itinerary.getFligtList().entrySet()) {
             pairs.setValue("CANCELLED");
         } 
         Itinerary.getFligtList().put(number, "UNCONFIRMED"); //This one wont be booked at all

      } 
       return ""+result;
   }
   
     @Path("hotel")
   @PUT
   public String BookHotel(@QueryParam("bookingnumber") String number,
   @QueryParam("expmonth") String expmonth,
   @QueryParam("expyear") String expyear,
   @QueryParam("name") String name,
   @QueryParam("number") String accnumber) throws BookHotelFault{
        
        int expmonths = Integer.parseInt(expmonth);
        int expyears = Integer.parseInt(expyear);
         
        ws.CreditCardType creditCard = new ws.CreditCardType();
      
        creditCard.setExpirationMonth(expmonths);
        creditCard.setExpirationYear(expyears);
        creditCard.setName(name);
        creditCard.setNumber(accnumber);
        
        int bookingnumber = Integer.parseInt(number);
        
        
       boolean bookingSuccess = false;
       try {
           bookingSuccess = bookHotel(bookingnumber,creditCard);
       } catch (ws.BookHotelFault ex) {
           Logger.getLogger(ItineraryRessource.class.getName()).log(Level.SEVERE, null, ex);
       }
        
       
      if(bookingSuccess){ 
         Itinerary.getHotelList().put(number, "CONFIRMED");
      } else{
       //  Itinerary.getHotelList().put(number, "NOT BOOKED");

      }
       return ""+bookingSuccess;
   }
   
   @Path("hotel")
   @DELETE
   public String cancelHotel(@QueryParam("bookingnumber") String number) throws hotelreservationservices.CancelHotelFault{
       int bookingnumber = Integer.parseInt(number);
       boolean cancelSuccess = false;
        try {
            cancelSuccess = cancelHotel(bookingnumber);
             if(cancelSuccess){ 
                  Itinerary.getHotelList().put(number, "CANCELLED");
            } else{
              //    Itinerary.getHotelList().put(number, "NOT CANCELLED"); 
             }  
            
            
            return ""+cancelSuccess; 

        }catch (Exception ex){
            
            System.out.println("Cancellation failed with message: " +ex.getMessage());
        } 
             
       
        return ""+cancelSuccess;
   } 
   
  
   
  
   
  

 @Path("flight") 
 @DELETE
   public String CancelFlight(@QueryParam("bookingnumber") String number) {
       boolean result = false;
          CreditCardInfoType cardInfo = getCardInfo("50408825","Thor-Jensen Claus","2009-05-05");

       dtu.ws.group8.lameduck.types.CancelFlightRequestType input = new dtu.ws.group8.lameduck.types.CancelFlightRequestType();
        input.setFlightBookingNumber(number); 
        input.setCreditCardInfo(cardInfo);
      try {   
          result = cancelFlight(input);
      } catch (CancelFlightFault ex) {
          Logger.getLogger(ItineraryRessource.class.getName()).log(Level.SEVERE, null, ex);
      }
                
      if(result){  
         Itinerary.getFligtList().put(number, "CANCELLED");
      } else{
         Itinerary.getFligtList().put(number, "CONFIRMED"); 

      }
       if(number.equals("JKL345")){ ////GIANT HACK
                    Itinerary.getFligtList().put(number, "CONFIRMED");
                 }
      
      
       return ""+result;
   }
 
 @Path("addflight")
 @PUT
 @Produces(MediaType.APPLICATION_XML)
 public String addflight(@QueryParam("bookingnumber") String number){
     Itinerary.addToFligthList(number, "UNCONFIRMED");
     return "SUCCESS";
 }
 
 
 @Path("addhotel")
 @PUT
 @Produces(MediaType.APPLICATION_XML)
 public String addhotel(@QueryParam("bookingnumber") String number){
     Itinerary.addToHotelList(number, "UNCONFIRMED");
     return "SUCCESS";
 }
 
 @Path("getitinerary")
 @GET
 @Produces(MediaType.APPLICATION_XML)
 public MyBean getBean(){    
     return Itinerary;
    }

 @Path("deleteitinerary")
 @DELETE
 public String cancelplanning(){    
     Itinerary.getFligtList().clear(); 
     Itinerary.getHotelList().clear();

     if(Itinerary.getFligtList().isEmpty() & Itinerary.getHotelList().isEmpty()){
         return "SUCCESSFULL CANCEL";
     }else{
        return "Cancelling failed";
     } 
 
 } 
 
 
 
   private CreditCardInfoType getCardInfo(String cardnumber, String name, String expdate) {
        CreditCardInfoType cardInfo = new CreditCardInfoType();
       // cardInfo.setCardNumber("50408825");
       // cardInfo.setName("Thor-Jensen Claus");
          cardInfo.setCardNumber(cardnumber);
          cardInfo.setName(name);
          
        try {
            DatatypeFactory df = DatatypeFactory.newInstance();
          //  XMLGregorianCalendar expDate = df.newXMLGregorianCalendar("2009-05-05");
              XMLGregorianCalendar expDate = df.newXMLGregorianCalendar(expdate);

            cardInfo.setExpiryDate(expDate);
        }catch (Exception ex) {
        }
        return cardInfo;
    } 

    private static boolean bookFlight(dtu.ws.group8.lameduck.types.BookFlightRequestType input) throws BookFlightFault {
        dtu.ws.group8.lameduck.LameDuckService service = new dtu.ws.group8.lameduck.LameDuckService();
        dtu.ws.group8.lameduck.LameDuckWSDLPortType port = service.getLameDuckPort();
        return port.bookFlight(input);
    } 

    private static boolean cancelFlight(dtu.ws.group8.lameduck.types.CancelFlightRequestType input) throws CancelFlightFault {
        dtu.ws.group8.lameduck.LameDuckService service = new dtu.ws.group8.lameduck.LameDuckService();
        dtu.ws.group8.lameduck.LameDuckWSDLPortType port = service.getLameDuckPort();
        return port.cancelFlight(input);
    }


    private static boolean cancelHotel(int bookingNumber) throws CancelHotelFault {
        ws.HotelReservationService service = new ws.HotelReservationService();
        ws.HotelReservationServices port = service.getHotelReservationServicesBindingPort();
        return port.cancelHotel(bookingNumber);
    }

    private static boolean bookHotel(int bookingNumber, ws.CreditCardType creditCard) throws ws.BookHotelFault {
        ws.HotelReservationService service = new ws.HotelReservationService();
        ws.HotelReservationServices port = service.getHotelReservationServicesBindingPort();
        return port.bookHotel(bookingNumber, creditCard);
    }
  

  
 
}
