package SC13Project.Milestone1.FlightTicket.Client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import SC13Project.Milestone1.FlightTicket.Client.G29FlightTicketImplStub.FlightInfo;

public class FlightClient {

	private static G29FlightTicketImplStub stub;
	
	public static void main(String[] args) {

		try {
			stub = new G29FlightTicketImplStub();
			
			
			
			//request.setDeparture("Bilbao");
			//request.setDestination("Vienna");
			G29FlightTicketImplStub.FlightTicketDate date = new G29FlightTicketImplStub.FlightTicketDate();
			date.setDay(22);
			date.setMonth(12);
			date.setYear(2013);
			
			
			//G29FlightTicketImplStub.GetFlightInfoResponse response = stub.getFlightInfo(request);
			//FlightInfo[] data = response.get_return();
			
		} catch (AxisFault e) {
			
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void testGetFlightInfo_OK() {
		G29FlightTicketImplStub.FlightTicketDate date = new G29FlightTicketImplStub.FlightTicketDate();
		date.setDay(22);
		date.setMonth(12);
		date.setYear(2013);
		String departure = "Bilbao";
		String destination ="Vienna";
		G29FlightTicketImplStub.GetFlightInfo request = new G29FlightTicketImplStub.GetFlightInfo();
		request.setDate(date);
		//G29FlightTicketImplStub.GetFlightInfoResponse response = stub.getFlightInfo(request);
		//FlightInfo[] data = response.get_return();
		
		//assertEquals(1, flights.size());
	}

//		public void testGetFlightInfo_NOK() {
//			FlightTicketDate date = new FlightTicketDate();
//			date.setDay(22);
//			date.setMonth(12);
//			date.setYear(2013);
//			String departure = "Bilbao";
//			String destination ="Vienna1";
//			List<FlightInfo> flights = flight.getFlightInfo(departure, destination, date);
//			assertEquals(0, flights.size());
//		}
		
		
		
		
//		public void testBookFlight1() throws FlightUnAvailableException {
//			FlightTicketDate date = new FlightTicketDate();
//			date.setDay(22);
//			date.setMonth(12);
//			date.setYear(2013);
//			String bookingId = flight.bookFlight("flightone", date, 2);
//			assertTrue(bookingId.length() != 0);
//			flight.cancelBooking(bookingId);
//		}

//		public void testBookFlight2() throws FlightUnAvailableException {
//			FlightTicketDate date = new FlightTicketDate();
//			date.setDay(22);
//			date.setMonth(12);
//			date.setYear(2013);
//			String bookingId = flight.bookFlight("flightone", date, 5);
//			assertTrue(bookingId.length() != 0);
//			flight.cancelBooking(bookingId);
//		}
		
//		// flight inexistent
//		@Test (expected = FlightUnAvailableException.class)
//		public void testBookFlightException1() throws FlightUnAvailableException {
//			FlightTicketDate date = new FlightTicketDate();
//			date.setDay(22);
//			date.setMonth(12);
//			date.setYear(2013);
//			String bookingId = flight.bookFlight("flightone1", date, 2);
//			assertTrue(bookingId.length() != 0);
//		}
		
//		// insufficient places
//		@Test (expected = FlightUnAvailableException.class)
//		public void testBookFlightException2() throws FlightUnAvailableException {
//			FlightTicketDate date = new FlightTicketDate();
//			date.setDay(22);
//			date.setMonth(12);
//			date.setYear(2013);
//			String bookingId = flight.bookFlight("flightone", date, 6);
//			assertTrue(bookingId.length() != 0);
//		}
		
//		// insufficient places
//		@Test (expected = FlightUnAvailableException.class)
//		public void testBookFlight3() throws FlightUnAvailableException {
//			FlightTicketDate date = new FlightTicketDate();
//			date.setDay(17);
//			date.setMonth(1);
//			date.setYear(2014);
//			String bookingId = flight.bookFlight("flightone", date, 5);
//			assertTrue(bookingId.length() != 0);
//			flight.cancelBooking(bookingId);
//		}
		
//		// invalid date
//		@Test (expected = FlightUnAvailableException.class)
//		public void testBookFlightException3() throws FlightUnAvailableException {
//			FlightTicketDate date = new FlightTicketDate();
//			date.setDay(2);
//			date.setMonth(12);
//			date.setYear(2013);
//			String bookingId = flight.bookFlight("flightone", date, 1);
//			assertTrue(bookingId.length() != 0);
//		}
		
//		@Test
//		public void testCancelBooking() {
//			FlightTicketDate date = new FlightTicketDate();
//			date.setDay(22);
//			date.setMonth(12);
//			date.setYear(2013);
//			String bookingId = "";
//			try {
//				bookingId = flight.bookFlight("flightone", date, 2);
//			} catch (FlightUnAvailableException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			assertTrue(bookingId.length() != 0);
//			flight.cancelBooking(bookingId);
//			
//		}

		
	
	
	

}
