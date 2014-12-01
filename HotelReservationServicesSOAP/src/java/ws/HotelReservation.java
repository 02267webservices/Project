package ws;

import dk.dtu.imm.fastmoney.BankService;
import dk.dtu.imm.fastmoney.CreditCardFaultMessage;
import dk.dtu.imm.fastmoney.types.AccountType;
import dk.dtu.imm.fastmoney.types.CreditCardInfoType;
import dk.dtu.imm.fastmoney.types.ExpirationDateType;
import hotelreservationservices.BookHotelFault;
import hotelreservationservices.BookHotelFaultType;
import hotelreservationservices.CancelHotelFault;
import hotelreservationservices.CancelHotelFaultType;
import hotelreservationservices.CreditCardType;
import hotelreservationservices.HotelType;
import hotelreservationservices.HotelsType;
import java.util.HashMap;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

@WebService(serviceName = "HotelReservationService", portName = "HotelReservationServicesBindingPort", endpointInterface = "hotelreservationservices.HotelReservationServices", targetNamespace = "http://HotelReservationServices", wsdlLocation = "WEB-INF/wsdl/HotelReservation/HotelReservation.wsdl")
public class HotelReservation {

    // -------- NiceView --------

    private final Object lock = new Object();
    private static final HashMap<Integer, Booking> bookings = new HashMap<Integer, Booking>();
    private int next = 1;
    
    public HotelsType getHotels(String city, XMLGregorianCalendar arrival, XMLGregorianCalendar departure) {
        
        HotelsType hotels = new HotelsType();
        hotels.setName("NiceView");
        
        if (arrival.compare(departure) > 0) {
            return hotels;
        }
        
        HotelType hotel = new HotelType();
        hotel.setName("Hotel that always succeed");
        hotel.setAddress(city);
        hotel.setBookingNumber(next++);
        hotel.setPriceForWholeStay(1000);
        hotel.setPaymentGuarantee(false);
        hotels.getHotels().add(hotel);
        bookings.put(hotel.getBookingNumber(), new Booking(hotel));

        hotel = new HotelType();
        hotel.setName("Hotel that requires payment guarantee and can fail occasionally");
        hotel.setAddress(city);
        hotel.setBookingNumber(next++);
        hotel.setPriceForWholeStay(2000);
        hotel.setPaymentGuarantee(true);
        hotels.getHotels().add(hotel);
        bookings.put(hotel.getBookingNumber(), new Booking(hotel));

        hotel = new HotelType();
        hotel.setName("Hotel that always fails");
        hotel.setAddress(city);
        hotel.setBookingNumber(next++);
        hotel.setPriceForWholeStay(3000);
        hotel.setPaymentGuarantee(false);
        hotels.getHotels().add(hotel);
        bookings.put(hotel.getBookingNumber(), new Booking(hotel));
            
        return hotels;
        
    }

    public boolean bookHotel(int bookingNumber, CreditCardType creditCard) throws BookHotelFault {
        
         HotelsType hotels = new HotelsType();
        hotels.setName("NiceView");
        
       
        
        HotelType hotel = new HotelType();
        hotel.setName("Hotel that always succeed");
        hotel.setAddress("Copenhagen");
        hotel.setBookingNumber(next++);
        hotel.setPriceForWholeStay(1000);
        hotel.setPaymentGuarantee(false);
        hotels.getHotels().add(hotel);
        bookings.put(hotel.getBookingNumber(), new Booking(hotel));

        hotel = new HotelType();
        hotel.setName("Hotel that requires payment guarantee and can fail occasionally");
        hotel.setAddress("Odense");
        hotel.setBookingNumber(next++);
        hotel.setPriceForWholeStay(2000);
        hotel.setPaymentGuarantee(true);
        hotels.getHotels().add(hotel);
        bookings.put(hotel.getBookingNumber(), new Booking(hotel));

        hotel = new HotelType();
        hotel.setName("Hotel that always fails");
        hotel.setAddress("Krakow");
        hotel.setBookingNumber(next++);
        hotel.setPriceForWholeStay(3000);
        hotel.setPaymentGuarantee(false);
        hotels.getHotels().add(hotel);
        bookings.put(hotel.getBookingNumber(), new Booking(hotel));
  /*      
        System.out.print("-------------------------------------------------------------------------");
        System.out.print(creditCard.getExpirationMonth());
        System.out.print(creditCard.getExpirationYear());
        System.out.print(creditCard.getName());
        System.out.print(creditCard.getNumber());

        System.out.print("hashmap lenght" + bookings.size()); 
        System.out.print("__________________________________________________________");
*/
        Booking booking = bookings.get(bookingNumber);

        
        if (!bookings.containsKey(bookingNumber)) {
            BookHotelFaultType fault = new BookHotelFaultType();
            fault.setBookingNumber(bookingNumber);
            fault.setCreditCardInfo(creditCard);
           
           throw new BookHotelFault("Booking was not found.", fault);
        }

        
        /*
        if (booking.getBooked()) {
            BookHotelFaultType fault = new BookHotelFaultType();
            fault.setBookingNumber(bookingNumber);
            fault.setCreditCardInfo(creditCard);
            throw new BookHotelFault("Booking is already booked.", fault);
        } 
        */

        if (booking.getHotel().getName().equalsIgnoreCase("Hotel that always succeed")) {
            booking.book();
            return true;
        }

        if (booking.getHotel().getName().equalsIgnoreCase("Hotel that requires payment guarantee and can fail occasionally")) {

            CreditCardInfoType creditCardInfoType = new CreditCardInfoType();
            ExpirationDateType expirationDateType = new ExpirationDateType();
            AccountType accountType = new AccountType();
            creditCardInfoType.setName(creditCard.getName());
            creditCardInfoType.setNumber(creditCard.getNumber());
            expirationDateType.setMonth(creditCard.getExpirationMonth());
            expirationDateType.setYear(creditCard.getExpirationYear());
            creditCardInfoType.setExpirationDate(expirationDateType);
            accountType.setName("NiceView");
            accountType.setNumber("50308815");

            try {
                if (!chargeCreditCard(8, creditCardInfoType, booking.getHotel().getPriceForWholeStay(), accountType)) {
                    BookHotelFaultType fault = new BookHotelFaultType();
                    fault.setBookingNumber(bookingNumber);
                    fault.setCreditCardInfo(creditCard);
                    throw new BookHotelFault("Payment failed.", fault);
                }
            } catch (CreditCardFaultMessage ex) {
                BookHotelFaultType fault = new BookHotelFaultType();
                fault.setBookingNumber(bookingNumber);
                fault.setCreditCardInfo(creditCard);
                throw new BookHotelFault("Payment failed.", fault, ex);
            }

            booking.book(creditCard);
            return true;
 
        }

        if (booking.getHotel().getName().equalsIgnoreCase("Hotel that always fails")) {
            BookHotelFaultType fault = new BookHotelFaultType();
            fault.setBookingNumber(bookingNumber);
            fault.setCreditCardInfo(creditCard);
            throw new BookHotelFault("Booking cannot be booked.", fault);
        }
        
        BookHotelFaultType fault = new BookHotelFaultType();
        fault.setBookingNumber(bookingNumber);
        fault.setCreditCardInfo(creditCard);
        throw new BookHotelFault("Booking was not found.", fault);
        
    }

    public boolean cancelHotel(int bookingNumber) throws CancelHotelFault {
  /*      
           System.out.print("hashmap lenght" + bookings.size()); 
        System.out.print("__________________________________________________________");
*/
    
        if (!bookings.containsKey(bookingNumber)) {
            CancelHotelFaultType fault = new CancelHotelFaultType();
            fault.setBookingNumber(bookingNumber);
           // return true;
            throw new CancelHotelFault("Booking was not found.", fault);	
        }

        Booking booking = bookings.get(bookingNumber);
        
        if (!booking.getBooked()) {
            CancelHotelFaultType fault = new CancelHotelFaultType();
            fault.setBookingNumber(bookingNumber);
            throw new CancelHotelFault("Booking was not booked.", fault);
        }
        
        /*
        if (booking.getCancelled()) {
            CancelHotelFaultType fault = new CancelHotelFaultType();
            fault.setBookingNumber(bookingNumber);
            throw new CancelHotelFault("Booking is already cancelled.", fault);
        }
        */
        
        if (booking.getHotel().getName().equalsIgnoreCase("Hotel that always succeed")) {
            booking.cancel();
            return true;
        }

        if (booking.getHotel().getName().equalsIgnoreCase("Hotel that requires payment guarantee and can fail occasionally")) {

            CreditCardInfoType creditCardInfoType = new CreditCardInfoType();
            ExpirationDateType expirationDateType = new ExpirationDateType();
            AccountType accountType = new AccountType();
            creditCardInfoType.setName(booking.getCreditCard().getName());
            creditCardInfoType.setNumber(booking.getCreditCard().getNumber());
            expirationDateType.setMonth(booking.getCreditCard().getExpirationMonth());
            expirationDateType.setYear(booking.getCreditCard().getExpirationYear());
            creditCardInfoType.setExpirationDate(expirationDateType);
            accountType.setName("NiceView");
            accountType.setNumber("50308815");

            try {
                if (!refundCreditCard(8, creditCardInfoType, booking.getHotel().getPriceForWholeStay(), accountType)) {
                    CancelHotelFaultType fault = new CancelHotelFaultType();
                    fault.setBookingNumber(bookingNumber);
                    throw new CancelHotelFault("Refund failed.", fault);
                }
            } catch (CreditCardFaultMessage ex) {
                CancelHotelFaultType fault = new CancelHotelFaultType();
                fault.setBookingNumber(bookingNumber);
                throw new CancelHotelFault("Refund failed.", fault, ex);
            }

            booking.cancel();
            return true;

        }

        if (booking.getHotel().getName().equalsIgnoreCase("Hotel that always fails")) {
            CancelHotelFaultType fault = new CancelHotelFaultType();
            fault.setBookingNumber(bookingNumber);
            throw new CancelHotelFault("Booking cannot be cancelled.", fault);
        }
            
        
        CancelHotelFaultType fault = new CancelHotelFaultType();
        fault.setBookingNumber(bookingNumber);
        throw new CancelHotelFault("Booking was not found.", fault);
        
    }
    
    // -------- FastMoney --------

    //@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service = new BankService();
    
    private boolean chargeCreditCard(int group, CreditCardInfoType creditCardInfo, int amount, AccountType account) throws CreditCardFaultMessage {
        
        return service.getBankPort().chargeCreditCard(group, creditCardInfo, amount, account);
        
    }

    private boolean refundCreditCard(int group, CreditCardInfoType creditCardInfo, int amount, AccountType account) throws CreditCardFaultMessage {
        
        return service.getBankPort().refundCreditCard(group, creditCardInfo, amount, account);
        
    }   
}  