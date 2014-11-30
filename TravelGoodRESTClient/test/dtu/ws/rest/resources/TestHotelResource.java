package dtu.ws.rest.resources;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.ws.rs.core.MediaType;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import ws.dtu.rest.data.Hotel;


public class TestHotelResource {
     final String HOTEL_URI = "http://localhost:8080/RestWebService/webresources/hotel";

     Client client = Client.create();
     WebResource resource = client.resource(HOTEL_URI);
     
  //Denne test skulle meget gerne populere et array af hotels
      @Before
     public void setUp() {
         resource.path("reset").put();
     }
    
      
      ///Denne test laver bare enn GET request på standard uri (HOTEL_URI)
     @Test
     public void testHotel() {
         String result = resource.get(String.class);  
         assertEquals("MERIOTT", result);
         
        }

     
     //Denne test laver en GET request på pathen 'id'
     @Test
     public void testId() {
         String res = resource.path("id").accept(MediaType.TEXT_PLAIN).get(String.class);
         assertEquals("1",res);
     }

   
  
}
