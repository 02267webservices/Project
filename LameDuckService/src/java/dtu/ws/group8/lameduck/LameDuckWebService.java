/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dtu.ws.group8.lameduck;

import dk.dtu.imm.BankService;
import dk.dtu.imm.CreditCardFaultMessage;
import dk.dtu.imm.AccountType;
import dk.dtu.imm.CreditCardInfoType;
import dk.dtu.imm.ExpirationDateType;

import java.util.ArrayList;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import dtu.ws.group8.lameduck.types.FlightDetails;
import dtu.ws.group8.lameduck.types.FlightInfoListType;
import dtu.ws.group8.lameduck.types.FlightInfoType;

/**
 *
 * @author gravr
 */
@WebService(serviceName = "LameDuckService", portName = "LameDuckPort", endpointInterface = "dtu.ws.group8.lameduck.LameDuckWSDLPortType", targetNamespace = "http://lameduck.group8.ws.dtu", wsdlLocation = "WEB-INF/wsdl/LameDuckWebService/LameDuckWSDL.wsdl")
public class LameDuckWebService {
    //@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service = new BankService();
    
    static private ArrayList<FlightInfoType> flightsFromDB = new  ArrayList<FlightInfoType>();
    static private ArrayList<FlightInfoType> bookedFlights = new  ArrayList<FlightInfoType>();
    static boolean gotFlightsFromDB = false;


    public dtu.ws.group8.lameduck.types.FlightInfoListType getFlights(dtu.ws.group8.lameduck.types.GetFlightRequestType input) {
        
        if(!gotFlightsFromDB){
            getFlightsFromDB();
        }
        
        String start = input.getFlightStartAirport();
        String destination = input.getFlightDestinationAirport();
        XMLGregorianCalendar dateFlight = input.getFlightDate();

        FlightInfoListType returnListOfFlights = new FlightInfoListType();

       for(FlightInfoType flight:flightsFromDB){
            if(start.equals(flight.getFlightInfo().getStartAirport()) &&
               destination.equals(flight.getFlightInfo().getDestinationAirport()) &&
               dateFlight.equals(flight.getFlightInfo().getLiftOffDate())
                    ){
                returnListOfFlights.getFlightInformation().add(flight);
            }

        }

        return returnListOfFlights;
    }

    public boolean bookFlight(dtu.ws.group8.lameduck.types.BookFlightRequestType input) throws BookFlightFault {
        double flightPrice = -1;
        boolean alreadyBooked = false;
        //get price of flight
        for(FlightInfoType flight : flightsFromDB)
            if(flight.getFlightBookingNumber().equals(input.getFlightBookingNumber())){
                bookedFlights.add(flight);
                flightPrice = (double) flight.getFlightPrice();
                break;
            }
        if (flightPrice==-1){
            throw new BookFlightFault("Booking number not found", null);
        }
        /*
        //Check if already booked
        for(FlightInfoType flight : bookedFlights){
         
            if(flight.getFlightBookingNumber().equals(input.getFlightBookingNumber())){
                alreadyBooked = true;
                break;
            }
        }
        if (alreadyBooked) {
            throw new BookFlightFault("Flight is already booked", null);
        }
        */ 

        dtu.ws.group8.lameduck.types.CreditCardInfoType cardInfo = input.getCreditCardInfo();
        CreditCardInfoType cardInfoImmFormat = new CreditCardInfoType();
        cardInfoImmFormat.setName(cardInfo.getName());
        cardInfoImmFormat.setNumber(cardInfo.getCardNumber());
            
        ExpirationDateType expDate = new ExpirationDateType();
        expDate.setMonth(cardInfo.getExpiryDate().getMonth());
        expDate.setYear(cardInfo.getExpiryDate().getYear()% 10);
        cardInfoImmFormat.setExpirationDate(expDate);
        
        AccountType accType = new AccountType();
        accType.setName("LameDuck");
        accType.setNumber("50208812");
        
        try {
            return chargeCreditCard(8,cardInfoImmFormat,(int)flightPrice,accType);
            
        }catch (Exception ex) {
              throw new BookFlightFault("FastMoney Failed",null,ex);
        }
    }

    public boolean cancelFlight(dtu.ws.group8.lameduck.types.CancelFlightRequestType input) throws CancelFlightFault {
        boolean foundFlight = false;
        double flightPrice = -1;
        
        //check if flight exsists (not a requirement)
        for(FlightInfoType flight : bookedFlights)
            if(flight.getFlightBookingNumber().equals(input.getFlightBookingNumber())){
                bookedFlights.remove(flight);
                flightPrice = (double) flight.getFlightPrice();
                foundFlight = true;
                break;
            }
        if (!foundFlight){
            throw new CancelFlightFault("Booking number not found", null);
        }
        
        //try to refund half of price of flight via fastmoney
        try {
            //Should have used dk.dtu.imm.CreditCardInfoType in CancelFlightRequestType instead of dtu.ws.group8.lameduck.types.CreditCardInfoType
            //solution: http://stackoverflow.com/questions/2079823/importing-two-classes-with-same-name-how-to-handle
            
            dtu.ws.group8.lameduck.types.CreditCardInfoType cardInfo = input.getCreditCardInfo();
            CreditCardInfoType cardInfoImmFormat = new CreditCardInfoType();
            cardInfoImmFormat.setName(cardInfo.getName());
            cardInfoImmFormat.setNumber(cardInfo.getCardNumber());
            
            ExpirationDateType expDate = new ExpirationDateType();
            expDate.setMonth(cardInfo.getExpiryDate().getMonth());
            expDate.setYear(cardInfo.getExpiryDate().getYear()% 10);
            
            cardInfoImmFormat.setExpirationDate(expDate);
            
            AccountType accType = new AccountType();
            accType.setName("LameDuck");
            accType.setNumber("50208812");
            
            return refundCreditCard(8,cardInfoImmFormat,(int)flightPrice/2,accType);
            
        }catch (Exception ex) {
            throw new CancelFlightFault("FastMoney Failed", null,ex);
        }
 
    }
    
    
    private void getFlightsFromDB() {

        DatatypeFactory df;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            return;
        }
        XMLGregorianCalendar date = df.newXMLGregorianCalendar("2015-01-01");

        FlightInfoType flightInfo1a = new FlightInfoType();
        flightInfo1a.setFlightBookingNumber("ABC1234");
        flightInfo1a.setFlightPrice(4995.95);
        flightInfo1a.setFlightReservationService("Flight Reservation Service INC");
        FlightDetails flight1a = new FlightDetails();
        flight1a.setStartAirport("Copenhagen");
        flight1a.setDestinationAirport("Berlin");
        flight1a.setCarrierName("SAS");
        flight1a.setLiftOffDate(date);
        flight1a.setLandingDate(date);
        flightInfo1a.setFlightInfo(flight1a);
        
        FlightInfoType flightInfo1b = new FlightInfoType();
        flightInfo1b.setFlightBookingNumber("ABC4321");
        flightInfo1b.setFlightPrice(460.00);
        flightInfo1b.setFlightReservationService("Flight Reservation Service INC");
        FlightDetails flight1b = new FlightDetails();
        flight1b.setStartAirport("Copenhagen");
        flight1b.setDestinationAirport("Berlin");
        flight1b.setCarrierName("Norwegian");
        flight1b.setLiftOffDate(date);
        flight1b.setLandingDate(date);
        flightInfo1b.setFlightInfo(flight1b);
        
        XMLGregorianCalendar date2 = df.newXMLGregorianCalendar("2015-01-05");
        FlightInfoType flightInfo2 = new FlightInfoType();
        flightInfo2.setFlightBookingNumber("DEF5678");
        flightInfo2.setFlightPrice(950.00);
        flightInfo2.setFlightReservationService("Flight Reservation Service INC");
        FlightDetails flight2 = new FlightDetails();
        flight2.setStartAirport("Berlin");
        flight2.setDestinationAirport("Krakow");
        flight2.setCarrierName("Air Berlin");
        flight2.setLiftOffDate(date2);
        flight2.setLandingDate(date2);
        flightInfo2.setFlightInfo(flight2);
        
        XMLGregorianCalendar date3 = df.newXMLGregorianCalendar("2015-01-05");
        FlightInfoType flightInfo3 = new FlightInfoType();
        flightInfo3.setFlightBookingNumber("GHI912");
        flightInfo3.setFlightPrice(3000.00);
        flightInfo3.setFlightReservationService("Flight Reservation Service INC");
        FlightDetails flight3 = new FlightDetails();
        flight3.setStartAirport("Krakow");
        flight3.setDestinationAirport("Moscow");
        flight3.setCarrierName("Areoflot");
        flight3.setLiftOffDate(date3);
        flight3.setLandingDate(date3);
        flightInfo3.setFlightInfo(flight3);
        
        XMLGregorianCalendar date4 = df.newXMLGregorianCalendar("2015-01-10");
        FlightInfoType flightInfo4 = new FlightInfoType();
        flightInfo4.setFlightBookingNumber("JKL345");
        flightInfo4.setFlightPrice(3500.00);
        flightInfo4.setFlightReservationService("Flight Reservation Service INC");
        FlightDetails flight4 = new FlightDetails();
        flight4.setStartAirport("Moscow");
        flight4.setDestinationAirport("Denmark");
        flight4.setCarrierName("Areoflot");
        flight4.setLiftOffDate(date4);
        flight4.setLandingDate(date4);
        flightInfo4.setFlightInfo(flight4);

        flightsFromDB.add(flightInfo1a);
        flightsFromDB.add(flightInfo1b);
        flightsFromDB.add(flightInfo2);
        flightsFromDB.add(flightInfo3);
        flightsFromDB.add(flightInfo4);
        
        gotFlightsFromDB = true;
    }

    private boolean chargeCreditCard(int group, dk.dtu.imm.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.AccountType account) throws CreditCardFaultMessage {
        dk.dtu.imm.BankPortType port = service.getBankPort();
        return port.chargeCreditCard(group, creditCardInfo, amount, account);
    }

    private boolean validateCreditCard(int group, dk.dtu.imm.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        dk.dtu.imm.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }

    private boolean refundCreditCard(int group, dk.dtu.imm.CreditCardInfoType creditCardInfo, int amount, dk.dtu.imm.AccountType account) throws CreditCardFaultMessage {
        dk.dtu.imm.BankPortType port = service.getBankPort();
        return port.refundCreditCard(group, creditCardInfo, amount, account);
    }

    
}
