package ws.dtu.rest.resource;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.Static;
import hotelreservationservices.HotelsType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.dtu.rest.data.Hotel;


@Path("hotel")
public class HotelsRessource {

  @GET
  @Produces(MediaType.TEXT_XML)
   public HotelsType getHotels() {
            DatatypeFactory df;
            XMLGregorianCalendar arrivalDate = null;
            XMLGregorianCalendar departureDate = null;
            
       try {
           df = DatatypeFactory.newInstance();
            arrivalDate = df.newXMLGregorianCalendar("2015-01-01");
            departureDate = df.newXMLGregorianCalendar("2015-03-01");

       } catch (DatatypeConfigurationException ex) {
           Logger.getLogger(ItineraryRessource.class.getName()).log(Level.SEVERE, null, ex);
       }
        HotelsType result = getHotels("some city 1",arrivalDate,departureDate);
    return result;
   }

    private static HotelsType getHotels(java.lang.String city, javax.xml.datatype.XMLGregorianCalendar arrival, javax.xml.datatype.XMLGregorianCalendar departure) {
        hotelreservationservices.HotelReservationService service = new hotelreservationservices.HotelReservationService();
        hotelreservationservices.HotelReservationServices port = service.getHotelReservationServicesBindingPort();
        return port.getHotels(city, arrival, departure);
    }
 
}
