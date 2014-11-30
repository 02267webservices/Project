package ws.dtu.rest.data;

import dtu.ws.group8.lameduck.client.FlightInfoType;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/*
 * Note that this class is only a stub and does not provide a correct implementation
 * of the business logic.
 */
@XmlRootElement(name = "Itinerary")

public class Itinerary {
    @XmlElement
    public int id;
    @XmlElement
    public List<FlightInfoType> flights;
   // private Hotel[] hotels;
    
    public Itinerary(int id) {
        this.id = id;
    }
    
    public List<FlightInfoType> getFlights() {
        return flights;
    }
    
    public int getId(){
        return id;
    }
    
    public void setFlight(FlightInfoType fly){
        flights.add(fly);
    }
    
  
}