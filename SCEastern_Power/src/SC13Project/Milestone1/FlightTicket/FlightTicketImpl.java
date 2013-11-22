package SC13Project.Milestone1.FlightTicket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import SC13Project.Milestone1.FlightTicket.XMLProcessing.AirlineInfo;
import SC13Project.Milestone1.FlightTicket.XMLProcessing.AirlineList;
import SC13Project.Milestone1.FlightTicket.XMLProcessing.BookingInfo;
import SC13Project.Milestone1.FlightTicket.XMLProcessing.BookingList;
import SC13Project.Milestone1.FlightTicket.XMLProcessing.DateInfo;
import SC13Project.Milestone1.FlightTicket.XMLProcessing.FlightTicketXMLParser;

//Please do not change the name of the package or this interface
//Please add here your implementation
public class FlightTicketImpl implements BookingFlightWS {

	private Object lock1 = new Object();
	private Object lock2 = new Object();
	private Object lock3 = new Object();
	
	private Object dataLock = new Object();
	
	
	@Override
	public List<FlightInfo> getFlightInfo(String departure, String destination, FlightTicketDate date) {
		synchronized(dataLock) {
			return getFlightInfoUnsychronized(departure, destination, date);
		}
	}

	private List<FlightInfo> getFlightInfoUnsychronized(String departure, String destination, FlightTicketDate date) {
		System.out.println("##DEBUG: calling getFlightInfo" +" "+ departure+":"+destination+":"+date+ " " + new Date(System.currentTimeMillis()) );
		List<FlightInfo> flightInfoList = new ArrayList<FlightInfo>();
		if(departure == null || destination == null || date == null){
			System.out.println("##DEBUG: - getFlightInfo exit NULL param"+" "+ new Date(System.currentTimeMillis()) );
			return flightInfoList;
		}
			
		AirlineList flights;
		List<AirlineInfo> flightsInfo;
		//synchronized(dataLock)
		{
			flights = FlightTicketXMLParser.getTickets().getAirlines();
			flightsInfo = flights.getAirlines();
		}
		for (AirlineInfo airlineInfo : flightsInfo) {
			// check departure, destination
			if(!airlineInfo.getDeparture().equals(departure))
				continue;
			if(!airlineInfo.getDestination().equals(destination))
				continue;
			// check dates 
			for(DateInfo dateInfo : airlineInfo.getDates().getDates()){
				if(dateInfo.getYear() != date.getYear())
					continue;
				if(dateInfo.getMonth() != date.getMonth())
					continue;
				if(dateInfo.getDay() != date.getDay())
					continue;
				// add valid flight
				FlightInfo newFlightInfo = new FlightInfo();
				newFlightInfo.setDeparture(departure);
				newFlightInfo.setDestination(destination);
				newFlightInfo.setFlightNo(airlineInfo.getFlightNo());
				newFlightInfo.setPrice(airlineInfo.getPrice());
				String flightNumber = airlineInfo.getFlightNo();
				BookingList bookingList = getBookings(flightNumber, date);
				int bookedSeats = 0;
				for(BookingInfo booking : bookingList.getBookings()){
					bookedSeats += booking.getSeats();
				}
				int avalilableSeats = airlineInfo.getSeats() - bookedSeats;
				newFlightInfo.setSeats(avalilableSeats);
				flightInfoList.add(newFlightInfo);
			}
		}
		String output = "";
		for(AirlineInfo info : flightsInfo){
			output+= info.toString()+ "#";
		}
		System.out.println("##DEBUG: - getFlightInfo exit "+ flightInfoList.size()+"||"+ output +" "+ new Date(System.currentTimeMillis()) );
		return flightInfoList;
	}
	
	
	
	@Override
	public String bookFlight(String flightNumber, FlightTicketDate date, int seats) throws FlightUnAvailableException {
		synchronized(dataLock) {
			return bookFlightUnsychronized(flightNumber, date, seats);
		}
	}
	
	private String bookFlightUnsychronized(String flightNumber, FlightTicketDate date, int seats) throws FlightUnAvailableException {
		System.out.println("##DEBUG: calling bookFlight "+ flightNumber+":"+seats+":"+date +" "+ new Date(System.currentTimeMillis()) );
		String bookingId = "";
		if(flightNumber == null || date == null || seats == 0){
			System.out.println("##DEBUG: - bookFlight flightUnavailableException - invalid param!" +" "+ new Date(System.currentTimeMillis()) );
			throw new FlightUnAvailableException();
		}
		AirlineList flights;
		List<AirlineInfo> flightsInfo;
		//synchronized(dataLock)
		{
			flights = FlightTicketXMLParser.getTickets().getAirlines();
			flightsInfo = new ArrayList<AirlineInfo>(flights.getAirlines());
		}
		
		boolean bookingAvailable = false;
		for (AirlineInfo airlineInfo : flightsInfo) {
			boolean equalsFlightNo = airlineInfo.getFlightNo().equals(flightNumber);
			if(!equalsFlightNo)
				continue;
			// check dates 
			boolean dateOk = false;
			for(DateInfo dateInfo : airlineInfo.getDates().getDates()){
				dateOk = false;
				if( dateInfo.getYear() != date.getYear())
					continue;
				if( dateInfo.getMonth() != date.getMonth())
					continue;
				if( dateInfo.getDay() != date.getDay())
					continue;
				dateOk = true;
				break;
			}
			if(!dateOk){
				System.out.println("##DEBUG: - bookFlight flightUnavailableException! date not ok" +" "+ new Date(System.currentTimeMillis()) );
				throw new FlightUnAvailableException();
			}
			
			BookingList bookingList = getBookings(flightNumber, date);
			int bookedSeats = 0;
			for(BookingInfo booking : bookingList.getBookings()){
				bookedSeats += booking.getSeats();
			}
			
			int availableSeats = airlineInfo.getSeats() - bookedSeats;
			System.out.println("	#INFO: - bookFlight: bookedSeats " + bookedSeats+"/"+ airlineInfo.getSeats() + " available: "+ availableSeats );
			if(availableSeats>=seats){
				bookingAvailable = true;
				BookingList bookings;
				//synchronized(dataLock)
				{
					bookings = FlightTicketXMLParser.getTickets().getBookings();
				}
				BookingInfo newBooking = new BookingInfo();
				bookingId=flightNumber+"#"+date.toString()+"#"+(bookedSeats+seats); //TODO: generate bookingId to be unique
				newBooking.setBookingID(bookingId);
				newBooking.setFlightNo(flightNumber);
				DateInfo newDateInfo = new DateInfo();
				newDateInfo.setDay(date.getDay());
				newDateInfo.setMonth(date.getMonth());
				newDateInfo.setYear(date.getYear());
				newBooking.setDate(newDateInfo);
				newBooking.setSeats(seats);
				//synchronized(dataLock)
				{
					bookings.addBookingInfo(newBooking);
				}

				storeXMLData();
				break;
			}
		}
		
		if(!bookingAvailable){
			System.out.println("##DEBUG: - bookFlight flightUnavailable!" +" "+ new Date(System.currentTimeMillis()) );
			throw new FlightUnAvailableException();
		}
		System.out.println("##DEBUG: - bookFlight exit" +" "+ new Date(System.currentTimeMillis()) );
		return bookingId;
	}

	private void storeXMLData(){
		//synchronized(dataLock)
		{
			FlightTicketXMLParser.getInstance().pushDataToXML();
			FlightTicketXMLParser.reloadDB();
		}
	}
	
	private BookingList getBookings(String flightNumber, FlightTicketDate date){
		BookingList newBookingList = new BookingList();
		BookingList bookings;
		List<BookingInfo> bookingInfoList;
		//synchronized(dataLock)
		{
			bookings = FlightTicketXMLParser.getTickets().getBookings();
			bookingInfoList = bookings.getBookings();
			for(BookingInfo booking : bookingInfoList){
				if(!booking.getFlightNo().equals(flightNumber))
					continue;
				
				boolean dateOk = false;
				DateInfo dateInfo = booking.getDate();
				if( dateInfo.getYear() != date.getYear())
					continue;
				if( dateInfo.getMonth() != date.getMonth())
					continue;
				if( dateInfo.getDay() != date.getDay())
					continue;
				dateOk = true;
				
				newBookingList.addBookingInfo(booking);
			}
		}
		
		return newBookingList;
	}
	
	
	
	@Override
	public void cancelBooking(String bookingID) {
		synchronized(dataLock) {
			cancelBookingUnsychronized(bookingID);
		}
	}
	
	private void cancelBookingUnsychronized(String bookingID) {
		System.out.println("##DEBUG: calling cancelBookedFlight" +" "+ new Date(System.currentTimeMillis()) );
		if(bookingID == null || bookingID.length() == 0){
			System.out.println("##DEBUG: - cancelBookedFlight - invalid param" +" "+ new Date(System.currentTimeMillis()) );
			return;
		}
		BookingList bookings;
		List<BookingInfo> bookingInfoList;
		//synchronized(dataLock)
		{
			bookings = FlightTicketXMLParser.getTickets().getBookings();
			bookingInfoList = bookings.getBookings();
			List<BookingInfo> toRemove = new ArrayList<BookingInfo>();
			for(BookingInfo booking : bookingInfoList){
				if(!booking.getBookingID().equals(bookingID))
					continue;
				toRemove.add(booking);
			}
			for(BookingInfo booking : toRemove){
				bookings.removeBookingInfo(booking);
			}
		}
		storeXMLData();
		System.out.println("##DEBUG: - cancelBookedFlight exit" +" "+ new Date(System.currentTimeMillis()) );
	}
	
}
