
package hotelreservationservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HotelType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HotelType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bookingNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="priceForWholeStay" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="paymentGuarantee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelType", propOrder = {
    "name",
    "address",
    "bookingNumber",
    "priceForWholeStay",
    "paymentGuarantee"
})

@XmlRootElement(name = "HotelType")

public class HotelType {

    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String address;
    protected int bookingNumber;
    protected int priceForWholeStay;
    protected boolean paymentGuarantee;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the bookingNumber property.
     * 
     */
    public int getBookingNumber() {
        return bookingNumber;
    }

    /**
     * Sets the value of the bookingNumber property.
     * 
     */
    public void setBookingNumber(int value) {
        this.bookingNumber = value;
    }

    /**
     * Gets the value of the priceForWholeStay property.
     * 
     */
    public int getPriceForWholeStay() {
        return priceForWholeStay;
    }

    /**
     * Sets the value of the priceForWholeStay property.
     * 
     */
    public void setPriceForWholeStay(int value) {
        this.priceForWholeStay = value;
    }

    /**
     * Gets the value of the paymentGuarantee property.
     * 
     */
    public boolean isPaymentGuarantee() {
        return paymentGuarantee;
    }

    /**
     * Sets the value of the paymentGuarantee property.
     * 
     */
    public void setPaymentGuarantee(boolean value) {
        this.paymentGuarantee = value;
    }

}
