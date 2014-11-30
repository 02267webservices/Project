
package ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "HotelReservationServices", targetNamespace = "http://HotelReservationServices")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface HotelReservationServices {


    /**
     * 
     * @param departure
     * @param arrival
     * @param city
     * @return
     *     returns ws.HotelsType
     */
    @WebMethod
    @WebResult(name = "hotels", partName = "hotels")
    public HotelsType getHotels(
        @WebParam(name = "city", partName = "city")
        String city,
        @WebParam(name = "arrival", partName = "arrival")
        XMLGregorianCalendar arrival,
        @WebParam(name = "departure", partName = "departure")
        XMLGregorianCalendar departure);

    /**
     * 
     * @param creditCard
     * @param bookingNumber
     * @return
     *     returns boolean
     * @throws BookHotelFault
     */
    @WebMethod
    @WebResult(name = "status", partName = "status")
    public boolean bookHotel(
        @WebParam(name = "bookingNumber", partName = "bookingNumber")
        int bookingNumber,
        @WebParam(name = "creditCard", partName = "creditCard")
        CreditCardType creditCard)
        throws BookHotelFault
    ;

    /**
     * 
     * @param bookingNumber
     * @return
     *     returns boolean
     * @throws CancelHotelFault
     */
    @WebMethod
    @WebResult(name = "status", partName = "status")
    public boolean cancelHotel(
        @WebParam(name = "bookingNumber", partName = "bookingNumber")
        int bookingNumber)
        throws CancelHotelFault
    ;

}
