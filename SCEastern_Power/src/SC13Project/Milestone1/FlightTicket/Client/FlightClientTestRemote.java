package SC13Project.Milestone1.FlightTicket.Client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import SC13Project.Milestone1.FlightTicket.FlightInfo;
import SC13Project.Milestone1.FlightTicket.FlightTicketDate;
import SC13Project.Milestone1.FlightTicket.FlightUnAvailableException;

public class FlightClientTestRemote {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testGetFlightInfo() {
		fail("Not yet implemented");
	}

	@Test
	public void testBookFlight() {
		fail("Not yet implemented");
	}

//	@Test
//	public void testCancelBooking() {
//		fail("Not yet implemented");
//	}

	
//	@Test
//	public void testGetFlightInfo_OK() {
//		FlightTicketDate date = new FlightTicketDate();
//		date.setDay(22);
//		date.setMonth(12);
//		date.setYear(2013);
//		String departure = "Bilbao";
//		String destination ="Vienna";
////		List<FlightInfo> flights = flight.getFlightInfo(departure, destination, date);
//		assertEquals(1, flights.size());
//	}

//	@Test
//	public void testGetFlightInfo_NOK() {
//		FlightTicketDate date = new FlightTicketDate();
//		date.setDay(22);
//		date.setMonth(12);
//		date.setYear(2013);
//		String departure = "Bilbao";
//		String destination ="Vienna1";
//		List<FlightInfo> flights = flight.getFlightInfo(departure, destination, date);
//		assertEquals(0, flights.size());
//	}
	
	
	
	
//	@Test
//	public void testBookFlight1() throws FlightUnAvailableException {
//		FlightTicketDate date = new FlightTicketDate();
//		date.setDay(22);
//		date.setMonth(12);
//		date.setYear(2013);
//		String bookingId = flight.bookFlight("flightone", date, 2);
//		assertTrue(bookingId.length() != 0);
//		flight.cancelBooking(bookingId);
//	}

//	@Test 
//	public void testBookFlight2() throws FlightUnAvailableException {
//		FlightTicketDate date = new FlightTicketDate();
//		date.setDay(22);
//		date.setMonth(12);
//		date.setYear(2013);
//		String bookingId = flight.bookFlight("flightone", date, 5);
//		assertTrue(bookingId.length() != 0);
//		flight.cancelBooking(bookingId);
//	}
	
//	// flight inexistent
//	@Test (expected = FlightUnAvailableException.class)
//	public void testBookFlightException1() throws FlightUnAvailableException {
//		FlightTicketDate date = new FlightTicketDate();
//		date.setDay(22);
//		date.setMonth(12);
//		date.setYear(2013);
//		String bookingId = flight.bookFlight("flightone1", date, 2);
//		assertTrue(bookingId.length() != 0);
//	}
	
//	// insufficient places
//	@Test (expected = FlightUnAvailableException.class)
//	public void testBookFlightException2() throws FlightUnAvailableException {
//		FlightTicketDate date = new FlightTicketDate();
//		date.setDay(22);
//		date.setMonth(12);
//		date.setYear(2013);
//		String bookingId = flight.bookFlight("flightone", date, 6);
//		assertTrue(bookingId.length() != 0);
//	}
	
//	// insufficient places
//	@Test (expected = FlightUnAvailableException.class)
//	public void testBookFlight3() throws FlightUnAvailableException {
//		FlightTicketDate date = new FlightTicketDate();
//		date.setDay(17);
//		date.setMonth(1);
//		date.setYear(2014);
//		String bookingId = flight.bookFlight("flightone", date, 5);
//		assertTrue(bookingId.length() != 0);
//		flight.cancelBooking(bookingId);
//	}
	
//	// invalid date
//	@Test (expected = FlightUnAvailableException.class)
//	public void testBookFlightException3() throws FlightUnAvailableException {
//		FlightTicketDate date = new FlightTicketDate();
//		date.setDay(2);
//		date.setMonth(12);
//		date.setYear(2013);
//		String bookingId = flight.bookFlight("flightone", date, 1);
//		assertTrue(bookingId.length() != 0);
//	}
	
//	@Test
//	public void testCancelBooking() {
//		FlightTicketDate date = new FlightTicketDate();
//		date.setDay(22);
//		date.setMonth(12);
//		date.setYear(2013);
//		String bookingId = "";
//		try {
//			bookingId = flight.bookFlight("flightone", date, 2);
//		} catch (FlightUnAvailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		assertTrue(bookingId.length() != 0);
//		flight.cancelBooking(bookingId);
//		
//	}

	
}
