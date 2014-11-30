
package hotelreservationservices;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.1
 * 
 */
@WebFault(name = "bookHotelFaultElement", targetNamespace = "http://HotelReservationServices")
public class BookHotelFault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private BookHotelFaultType faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public BookHotelFault(String message, BookHotelFaultType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public BookHotelFault(String message, BookHotelFaultType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: hotelreservationservices.BookHotelFaultType
     */
    public BookHotelFaultType getFaultInfo() {
        return faultInfo;
    }

}