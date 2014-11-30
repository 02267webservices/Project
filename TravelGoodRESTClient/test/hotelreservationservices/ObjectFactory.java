
package hotelreservationservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hotelreservationservices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HotelElement_QNAME = new QName("http://HotelReservationServices", "hotelElement");
    private final static QName _CreditCardElement_QNAME = new QName("http://HotelReservationServices", "creditCardElement");
    private final static QName _BookHotelFaultElement_QNAME = new QName("http://HotelReservationServices", "bookHotelFaultElement");
    private final static QName _CancelHotelFaultElement_QNAME = new QName("http://HotelReservationServices", "cancelHotelFaultElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hotelreservationservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelHotelFaultType }
     * 
     */
    public CancelHotelFaultType createCancelHotelFaultType() {
        return new CancelHotelFaultType();
    }

    /**
     * Create an instance of {@link BookHotelFaultType }
     * 
     */
    public BookHotelFaultType createBookHotelFaultType() {
        return new BookHotelFaultType();
    }

    /**
     * Create an instance of {@link CreditCardType }
     * 
     */
    public CreditCardType createCreditCardType() {
        return new CreditCardType();
    }

    /**
     * Create an instance of {@link HotelType }
     * 
     */
    public HotelType createHotelType() {
        return new HotelType();
    }

    /**
     * Create an instance of {@link HotelsType }
     * 
     */
    public HotelsType createHotelsType() {
        return new HotelsType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HotelType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://HotelReservationServices", name = "hotelElement")
    public JAXBElement<HotelType> createHotelElement(HotelType value) {
        return new JAXBElement<HotelType>(_HotelElement_QNAME, HotelType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCardType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://HotelReservationServices", name = "creditCardElement")
    public JAXBElement<CreditCardType> createCreditCardElement(CreditCardType value) {
        return new JAXBElement<CreditCardType>(_CreditCardElement_QNAME, CreditCardType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookHotelFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://HotelReservationServices", name = "bookHotelFaultElement")
    public JAXBElement<BookHotelFaultType> createBookHotelFaultElement(BookHotelFaultType value) {
        return new JAXBElement<BookHotelFaultType>(_BookHotelFaultElement_QNAME, BookHotelFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelHotelFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://HotelReservationServices", name = "cancelHotelFaultElement")
    public JAXBElement<CancelHotelFaultType> createCancelHotelFaultElement(CancelHotelFaultType value) {
        return new JAXBElement<CancelHotelFaultType>(_CancelHotelFaultElement_QNAME, CancelHotelFaultType.class, null, value);
    }

}
