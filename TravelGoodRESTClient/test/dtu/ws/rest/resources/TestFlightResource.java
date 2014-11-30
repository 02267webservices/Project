package dtu.ws.rest.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import dtu.ws.group8.lameduck.client.*;
import hotelreservationservices.HotelType;
import hotelreservationservices.HotelsType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import static org.junit.Assert.assertEquals;


import org.junit.Test;
import ws.dtu.rest.data.MyBean;
/**
 *
 * @author emilhein
 */

public class TestFlightResource {
    static final String ITINERARY_URI = "http://localhost:8080/RestWebService/webresources/itineraries";
    //static final String FLIGHTS_URI = "http://localhost:8080/RestWebService/webresources/flights";
    static //final String HOTEL_URI = "http://localhost:8080/RestWebService/webresources/itinerary";
    private ArrayList<String> itine = new ArrayList();
    
  

     /*
  
     @Test
     public void cancelTest(){
         String res = r.queryParam("bookingnumber","ABC1234").delete(String.class);
         System.out.println("True cancelling of booking number was succesfull: " +res);
         assertEquals("true",res);
     }
    
     @Test
     public void cancelTest2(){
         String res = r.queryParam("bookingnumber","ABC1234srtw5").delete(String.class);
         System.out.println("Should be false: " +res);
         assertEquals("false",res);
     }
  */
     
     @Test
     public void P1(){
         
     Client client = Client.create();

      //Get list of flights   *****************
   
     List<FlightInfoType> flightsInfo = getFlights(client, "Copenhagen", "Berlin", "2015-01-01");
         
          if (!flightsInfo.isEmpty()){
            for (int i = 0; i < flightsInfo.size(); i++) {
		System.out.println(flightsInfo.get(i).getFlightReservationService()+ "\nFlightNumber: " +
                           flightsInfo.get(i).getFlightBookingNumber()+ "\nPrice: " +
                           Double.toString(flightsInfo.get(i).getFlightPrice()) + "\nCompany: " +
                           flightsInfo.get(i).getFlightInfo().getCarrierName() + "\nDestination: " +
                           flightsInfo.get(i).getFlightInfo().getDestinationAirport() + "\nFrom: " +
                           flightsInfo.get(i).getFlightInfo().getStartAirport()+ "\n");
            }       
     }
     //***********************************************************
          
     //**************ADD TO ITINERARY**************************     
         addFlightsToItinerary(client,"ABC1234");
         addhotelsToItinerary(client, "2");
         addFlightsToItinerary(client,"ABC4321");
         addFlightsToItinerary(client,"DEF5678");
         addhotelsToItinerary(client, "5");
      //************************GET ITINERARY************************
         MyBean bean = getItinerary(client);
      
         Map<String, String> fligts = bean.getFligtList();
         Map<String, String> hotels = bean.getHotelList();

         for (Map.Entry pairs : fligts.entrySet()) {
             System.out.println(pairs.getKey() + ":     " + pairs.getValue());
         } 
         for (Map.Entry pairs : hotels.entrySet()) {
             System.out.println(pairs.getKey() + ":     " + pairs.getValue());
         } 
         
         //**********************BOOK ITINERARY****************
         
         bookItinerary(client, bean);

         
         //************************GET ITINERARY****************
         System.out.println();
         MyBean tee = getItinerary(client);
         fligts = tee.getFligtList();
         hotels = tee.getHotelList();

         for (Map.Entry pairs : fligts.entrySet()) {
             System.out.println(pairs.getKey() + ":     " + pairs.getValue());
         } 
         for (Map.Entry pairs : hotels.entrySet()) {
             System.out.println(pairs.getKey() + ":     " + pairs.getValue());
         } 
         
     }  
        
     
     @Test
     public void P2(){
         
     Client client = Client.create();
      List<FlightInfoType> flightsInfo = getFlights(client, "Copenhagen", "Berlin", "2015-01-01");
         
          if (!flightsInfo.isEmpty()){
            for (int i = 0; i < flightsInfo.size(); i++) {
		System.out.println(flightsInfo.get(i).getFlightReservationService()+ "\nFlightNumber: " +
                           flightsInfo.get(i).getFlightBookingNumber()+ "\nPrice: " +
                           Double.toString(flightsInfo.get(i).getFlightPrice()) + "\nCompany: " +
                           flightsInfo.get(i).getFlightInfo().getCarrierName() + "\nDestination: " +
                           flightsInfo.get(i).getFlightInfo().getDestinationAirport() + "\nFrom: " +
                           flightsInfo.get(i).getFlightInfo().getStartAirport()+ "\n");
            }       
     }
     //****************Adding flight to itinerary********************
              addFlightsToItinerary(client,"ABC1234");
              System.out.println(cancelPlannig(client));
              
               MyBean tee = getItinerary(client);
               Map<String, String> fligts = tee.getFligtList();
               Map<String, String> hotels = tee.getHotelList();

         for (Map.Entry pairs : fligts.entrySet()) {
             System.out.println(pairs.getKey() + ":     " + pairs.getValue());
         } 
         for (Map.Entry pairs : hotels.entrySet()) {
             System.out.println(pairs.getKey() + ":     " + pairs.getValue());
         } 
         
              
     }
     
     /*
         ///***********************CANCEL ITINERARY*****************
         
         cancelItinerary(client, bean);
         
         //*****************************GET ITINERARY***************
         System.out.println();
         MyBean ss = getItinerary(client);
         fligts = ss.getFligtList();
         hotels = ss.getHotelList();

         for (Map.Entry pairs : fligts.entrySet()) {
             System.out.println(pairs.getKey() + ":     " + pairs.getValue());
         } 
         for (Map.Entry pairs : hotels.entrySet()) {
             System.out.println(pairs.getKey() + ":     " + pairs.getValue());
         } 
        */
         
       //  cancelFlight(client, "ABC1234");
      /*  
        HotelsType hotelsHolder = getHotels(client);
          
          
          
           if (!hotelsHolder.getHotels().isEmpty()){
                for (int i = 0; i < hotelsHolder.getHotels().size(); i++) {
                    System.out.println(hotelsHolder.getHotels().get(i).getName()+ "\n" +
                           hotelsHolder.getHotels().get(i).getAddress()+ "\n" +
                           hotelsHolder.getHotels().get(i).getBookingNumber());
                }
            }
         */
         
     

  
     //   bookHotel(client, "2"); ///2 is the number that will try connection to fastmoney
     //   cancelHotel(client, "2");
         
   
      
    
     //}
     
  public  MyBean getItinerary(Client client) {
        WebResource r = client.resource(ITINERARY_URI); 
        MyBean bean = r.path("getitinerary").get(MyBean.class);
      return bean;
    }
  
  public  String cancelItinerary(Client client, MyBean itinerary) {
         Map<String, String> flights = itinerary.getFligtList();
         Map<String, String> hotels = itinerary.getHotelList();

        for (Map.Entry pairs : flights.entrySet()) {
            cancelFlight(client, pairs.getKey().toString());
        }   
        for (Map.Entry pairs : hotels.entrySet()) {
            cancelHotel(client, pairs.getKey().toString());
        }     
        
        return "Success";
    }
  
  public void bookItinerary(Client client, MyBean itinerary){
   CreditCardInfoType cardInfo = getCardInfo("50408825","Thor-Jensen Claus","2009-05-05");
   Map<String, String> rari = itinerary.getFligtList();
   Map<String, String> hotels = itinerary.getHotelList();

        for (Map.Entry pairs : rari.entrySet()) {
            bookFlight(client, pairs.getKey().toString(),cardInfo);
        }     
         for (Map.Entry pairs : hotels.entrySet()) {
            bookHotel(client, pairs.getKey().toString());
        }  

}
     
  public String addFlightsToItinerary(Client client ,String number){
      WebResource r = client.resource(ITINERARY_URI); 
      String res = r.path("addflight").queryParam("bookingnumber",number).put(String.class);

     return res;
}
  public String addhotelsToItinerary(Client client ,String number){
      WebResource r = client.resource(ITINERARY_URI); 
      String res = r.path("addhotel").queryParam("bookingnumber",number).put(String.class);

     return res;
}
  
  
  
  //***Here comes the 6 main methods you can call on the two SOAP services *********/
  
  public List<FlightInfoType> getFlights(Client client, String from, String to, String date){
     WebResource r = client.resource(ITINERARY_URI); 
     FlightInfoListType res = r.path("flights").queryParam("from",from).queryParam("to",to).queryParam("date",date).get(new GenericType<FlightInfoListType>(){});
      
     List<FlightInfoType> flightsInfo = res.getFlightInformation();

      
      return flightsInfo;
  }
 
  public HotelsType getHotels(Client client) {
      WebResource r = client.resource(ITINERARY_URI);   
      HotelsType res = r.path("hotels").get(new GenericType<HotelsType>(){});
      
      
      
      return res;
  }
  
  
  public String bookFlight(Client client, String number, CreditCardInfoType test){
      
      String param2 = test.getCardNumber();
      String param3 = test.getName();
      String param4 = test.getExpiryDate().toString();
    MultivaluedMap queryParams = new MultivaluedMapImpl();
    queryParams.add("bookingnumber", number);
    queryParams.add("cardnumber", param2);
    queryParams.add("name", param3);
    queryParams.add("expdate", param4);

      WebResource r = client.resource(ITINERARY_URI);
      String ans = r.queryParams(queryParams).get(String.class);
     // System.out.println(ans);
      return ans;
  }
     
  public String bookHotel(Client client, String number){
      WebResource r = client.resource(ITINERARY_URI);
      String ans = r.path("hotel").queryParam("bookingnumber",number).put(String.class);
    //  System.out.println("The booking of hotel "+number+" was: "+ ans);
      return ans;
   }

  public String cancelFlight (Client client, String number){
      WebResource r = client.resource(ITINERARY_URI);
      
      String cancel = r.path("flight").queryParam("bookingnumber",number).delete(String.class);
      System.out.println("cancelling of booking number"+ number + "was succesfull: " +cancel);
         
      return cancel;
  }
  
  public String cancelPlannig(Client client){
      WebResource r = client.resource(ITINERARY_URI);
      String response = r.path("deleteitinerary").delete(String.class);
      return response;
  }
  
  public String cancelHotel (Client client, String number){
      WebResource r = client.resource(ITINERARY_URI);
      
      String cancel = r.path("hotel").queryParam("bookingnumber",number).delete(String.class);
      System.out.println("True cancelling of booking number "+ number + " was: " +cancel);
         
      return cancel;
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
}