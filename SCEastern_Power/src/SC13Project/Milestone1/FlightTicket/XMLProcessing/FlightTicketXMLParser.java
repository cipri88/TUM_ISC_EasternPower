package SC13Project.Milestone1.FlightTicket.XMLProcessing;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FlightTicketXMLParser {

	private static String DATASOURCE_IN = "../datasource/ds_29_5.xml";
	private static String DATASOURCE_OUT = "../datasource/ds_29_5.xml";
	private static String NAMESPACE = "flig:";
	
	
	private static TicketInfo tickets = null ;
	private static FlightTicketXMLParser instance = null; 	
	
	public static FlightTicketXMLParser getInstance(){
		if( instance == null){
			instance = new FlightTicketXMLParser(); 
		}
		return instance;
	}
	
	public static TicketInfo getTickets(){
		if (tickets == null){
			tickets = getInstance().readXML();
		}
		return tickets ;
	}
	
	public static void reloadDB(){
		tickets = getInstance().readXML();
		System.out.println("##DATA: reload data from XML datasource");
	}

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	private TransformerFactory tFactory;
	private Transformer transformer;
	private DOMSource domSource;
	private Node rootNode ;
	
	private boolean initXML(TicketInfo tickets){
		try {
			File fXmlFile = new File(DATASOURCE_IN);
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.setXmlStandalone(true);
			doc.getDocumentElement().normalize();
			
			
			
			// Use a Transformer for output
		    tFactory = TransformerFactory.newInstance();
		    transformer = tFactory.newTransformer();
		    if (doc.getDoctype() != null){
				 String systemValue = (new File(doc.getDoctype().getSystemId())).getName();
				 transformer.setOutputProperty( OutputKeys.DOCTYPE_SYSTEM, systemValue);
			}
		    domSource = new DOMSource(doc);
		} 
		catch (Exception e) {
				System.out.println("##DATASOURCE not found!!##");
		    	e.printStackTrace();
		    	// used to avoid null references
		    	tickets.setAirlines(new AirlineList());
				tickets.setBookings(new BookingList());
				return false;
		}
		return true;
	}
	
	private TicketInfo readXML(){
		synchronized(instance){
			TicketInfo tickets = new TicketInfo();
			boolean initialized = initXML(tickets);
			if(!initialized)
				return tickets;
			rootNode = doc.getChildNodes().item(0);
			if (rootNode.hasChildNodes()) {
				tickets = traverseXML(rootNode.getChildNodes());
			}
			return tickets;
		}
	}
		
	private TicketInfo traverseXML(NodeList nodeList) {
		TicketInfo tickets = new TicketInfo();
		for(int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			
			String nodeName = tempNode.getNodeName();
			//System.out.println("\nNode Name =" + nodeName + " [OPEN]");
	 
			if(nodeName.equals(NAMESPACE+"airlines")){
				AirlineList list = this.parseAirlines(tempNode);
				tickets.setAirlines(list);
			}
				
			if(nodeName.equals(NAMESPACE+"bookings")){
				BookingList list = this.parseBookings(tempNode);
				tickets.setBookings(list);
			}
				
	    }
		return tickets ;
	}
	
	private AirlineList parseAirlines(Node node){
		AirlineList airlineList = new AirlineList();
		NodeList nList = node.getChildNodes();
		for(int count = 0; count < nList.getLength(); count++) {
			Node tempNode = nList.item(count);
			if (tempNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
				 
			String nodeName = tempNode.getNodeName();
			if(nodeName.equals(NAMESPACE+"airline")){
				AirlineInfo airline = this.parseAirline(tempNode);
				if(airline!=null)
					airlineList.addAirline(airline);
			}
		}
		return airlineList;
	}
	
	private AirlineInfo parseAirline(Node node){
		AirlineInfo airlineInfo = new AirlineInfo();
		NodeList nList = node.getChildNodes();
		for(int count = 0; count < nList.getLength(); count++) {
			 
			Node tempNode = nList.item(count);
			if (tempNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
				 
			String nodeName = tempNode.getNodeName();
			String nodeValue = tempNode.getTextContent();
			
			if(nodeName.equals(NAMESPACE+"flightNo")){
				airlineInfo.setFlightNo(nodeValue);
				continue;
			}
			if(nodeName.equals(NAMESPACE+"departure")){
				airlineInfo.setDeparture(nodeValue);
				continue;
			}
			if(nodeName.equals(NAMESPACE+"destination")){
				airlineInfo.setDestination(nodeValue);
				continue;
			}
			if(nodeName.equals(NAMESPACE+"seats")){
				try{
					int intValue = Integer.parseInt(nodeValue);
					airlineInfo.setSeats(intValue);
				}
				catch(Exception e){
					return null;
				}
				continue;
			}
			if(nodeName.equals(NAMESPACE+"price")){
				try{
					int intValue = Integer.parseInt(nodeValue);
					airlineInfo.setPrice(intValue);
				}
				catch(Exception e){
					return null;
				}
				continue;
			}
			if(nodeName.equals(NAMESPACE+"dates")){
				DateList dates = parseDatesList(tempNode);
				if(dates == null || dates.size() == 0)
					return null;
				airlineInfo.setDates(dates);
				continue;
			}
			
		}
		
		return airlineInfo;
	}
	
	private BookingList parseBookings(Node node){
		BookingList bookingList = new BookingList();
		NodeList nList = node.getChildNodes();
		for(int count = 0; count < nList.getLength(); count++) {
			Node tempNode = nList.item(count);
			if (tempNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
				 
			String nodeName = tempNode.getNodeName();
			if(nodeName.equals(NAMESPACE+"booking")){
				BookingInfo booking = this.parseBooking(tempNode);
				if(booking!=null)
					bookingList.addBookingInfo(booking);
			}
		}
		return bookingList;
	}
	
	private BookingInfo parseBooking(Node node){
		BookingInfo bookingInfo = new BookingInfo();
		NodeList nList = node.getChildNodes();
		for(int count = 0; count < nList.getLength(); count++) {
			 
			Node tempNode = nList.item(count);
			if (tempNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
				 
			String nodeName = tempNode.getNodeName();
			String nodeValue = tempNode.getTextContent();
			
			if(nodeName.equals(NAMESPACE+"bookingID")){
				bookingInfo.setBookingID(nodeValue);
				continue;
			}
			if(nodeName.equals(NAMESPACE+"flightNo")){
				bookingInfo.setFlightNo(nodeValue);
				continue;
			}
			if(nodeName.equals(NAMESPACE+"seats")){
				try{
					int intValue = Integer.parseInt(nodeValue);
					bookingInfo.setSeats(intValue);
				}
				catch(Exception e){
					return null;
				}
				continue;
			}
			if(nodeName.equals(NAMESPACE+"date")){
				DateInfo date = parseDateInfo(tempNode);
				if(date == null )
					return null;
				bookingInfo.setDate(date);
				continue;
			}
			
		}
		
		return bookingInfo;
	}
	
	private DateList parseDatesList(Node node){
		DateList dateList = new DateList();
		NodeList nList = node.getChildNodes();
		for(int count = 0; count < nList.getLength(); count++) {
			 
			Node tempNode = nList.item(count);
			if (tempNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
				 
			String nodeName = tempNode.getNodeName();
			if(nodeName.equals(NAMESPACE+"date")){
				DateInfo date = null;
				date = parseDateInfo(tempNode);
				if (date != null)
					dateList.addDate(date);
			}
		}
		
		return dateList;
	}
	
	private DateInfo parseDateInfo(Node node){
		DateInfo date = new DateInfo();
		NodeList nList = node.getChildNodes();
		for(int count = 0; count < nList.getLength(); count++) {
			 
			Node tempNode = nList.item(count);
			if (tempNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
				 
			String nodeName = tempNode.getNodeName();
			String nodeValue = tempNode.getTextContent();
			if(nodeName.equals(NAMESPACE+"year")){
				try{
					int value = Integer.parseInt(nodeValue);
					date.setYear(value);
					continue;
				}
				catch (Exception ex) {
					return null;
				}
			}
			if(nodeName.equals(NAMESPACE+"month")){
				try{
					int value = Integer.parseInt(nodeValue);
					date.setMonth(value);
					continue;
				}
				catch (Exception ex) {
					return null;
				}
			}
			if(nodeName.equals(NAMESPACE+"day")){
				try{
					int value = Integer.parseInt(nodeValue);
					date.setDay(value);
					continue;
				}
				catch (Exception ex) {
					return null;
				}
			}
		}
		return date;
	}

	public boolean pushDataToXML(){
		System.out.println("##DATA: writing data to XML datasource");
		File fXmlFile = new File(DATASOURCE_OUT);
		fXmlFile.setWritable(true);
		StreamResult result = new StreamResult(fXmlFile);
		
	    try {
	    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root elements
			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);
			Node newRoot = doc.importNode(this.rootNode, false);			
			NodeList list = this.rootNode.getChildNodes();
			for(int i =0; i<list.getLength(); i++){
				Node n = list.item(i);
				Node newN = doc.importNode(n, false);
				String nodeName = newN.getNodeName();
				if(nodeName.equals(NAMESPACE+"airlines")){
					pushAirlines(doc, newN);
				}
				else if(nodeName.equals(NAMESPACE+"bookings")){
					pushBookings(doc, newN);
				}
				newRoot.appendChild(newN);
			}
			doc.appendChild(newRoot);
			
	    	DOMSource source = new DOMSource(doc);
	    	transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
	    	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    	transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);
		} catch (TransformerException te) {
		    Throwable x = te;
		    if (te.getException() != null)
		        x = te.getException();
		    x.printStackTrace();
		    return false;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
			return false;
		}
	    return true;
	}
	
	private void pushAirlines(Document doc, Node airlines){
		AirlineList data = this.tickets.getAirlines();
		List<AirlineInfo> list = data.getAirlines();
		for(AirlineInfo info : list ){
			Node newSpace = doc.createTextNode("\n		");
			Node newSpace2 = doc.createTextNode("\n			");
			airlines.appendChild(newSpace);
			Element newAirline = doc.createElement("flig:airline");
			
			Element newFlightNo =doc.createElement("flig:flightNo");
			newFlightNo.setTextContent(info.getFlightNo());
			newAirline.appendChild(newSpace2.cloneNode(false));
			newAirline.appendChild(newFlightNo);
			
			Element newDeparture = doc.createElement("flig:departure");
			newDeparture.setTextContent(info.getDeparture());
			newAirline.appendChild(newSpace2.cloneNode(false));
			newAirline.appendChild(newDeparture);
			
			Element newDestination = doc.createElement("flig:destination");
			newDestination.setTextContent(info.getDestination());
			newAirline.appendChild(newSpace2.cloneNode(false));
			newAirline.appendChild(newDestination);
			
			Element newSeats = doc.createElement("flig:seats");
			newSeats.setTextContent(""+info.getSeats());
			newAirline.appendChild(newSpace2.cloneNode(false));
			newAirline.appendChild(newSeats);
			
			Element newPrice = doc.createElement("flig:price");
			newPrice.setTextContent(""+info.getPrice());
			newAirline.appendChild(newSpace2.cloneNode(false));
			newAirline.appendChild(newPrice);
			
			Element newDates = doc.createElement("flig:dates");
			for(DateInfo date : info.getDates().getDates()){
				Element newDate = doc.createElement("flig:date");
				Node newSpace4 = doc.createTextNode("\n					");
				
				Element newYear = doc.createElement("flig:year");
				newYear.setTextContent(""+date.getYear());
				newDate.appendChild(newSpace4.cloneNode(false));
				newDate.appendChild(newYear);
				
				Element newMonth = doc.createElement("flig:month");
				newMonth.setTextContent(""+date.getMonth());
				newDate.appendChild(newSpace4.cloneNode(false));
				newDate.appendChild(newMonth);
				
				Element newDay = doc.createElement("flig:day");
				newDay.setTextContent(""+date.getDay());
				newDate.appendChild(newSpace4.cloneNode(false));
				newDate.appendChild(newDay);
				
				Node newSpace3 = doc.createTextNode("\n				");
				newDate.appendChild(newSpace3.cloneNode(false));
				newDates.appendChild(newSpace3.cloneNode(false));
				newDates.appendChild(newDate);
				//newDates.appendChild(newSpace3.cloneNode(false));
			}
			newDates.appendChild(newSpace2.cloneNode(false));
			newAirline.appendChild(newSpace2.cloneNode(false));
			newAirline.appendChild(newDates);
			newAirline.appendChild(newSpace.cloneNode(false));
			airlines.appendChild(newAirline);
		}
	}
	
	private void pushBookings(Document doc, Node bookings){
		BookingList data = this.tickets.getBookings();
		List<BookingInfo> list = data.getBookings();
		Node newSpace1 = doc.createTextNode("\n		");
		Node newSpace2 = doc.createTextNode("\n	 		");
		for(BookingInfo booking : list){
			
			//bookings.appendChild(newSpace);
			Element newBooking = doc.createElement("flig:booking");
			
			Element newBookingId =doc.createElement("flig:bookingID");
			newBookingId.setTextContent(booking.getBookingID());
			newBooking.appendChild(newSpace2.cloneNode(false));
			newBooking.appendChild(newBookingId);
			
			Element newFlightNo =doc.createElement("flig:flightNo");
			newFlightNo.setTextContent(booking.getFlightNo());
			newBooking.appendChild(newSpace2.cloneNode(false));
			newBooking.appendChild(newFlightNo);
			
			Element newSeats = doc.createElement("flig:seats");
			newSeats.setTextContent(""+booking.getSeats());
			newBooking.appendChild(newSpace2.cloneNode(false));
			newBooking.appendChild(newSeats);
			
			DateInfo date = booking.getDate(); 
			Element newDate = doc.createElement("flig:date");
			Node newSpace4 = doc.createTextNode("\n					");
			
			Element newYear = doc.createElement("flig:year");
			newYear.setTextContent(""+date.getYear());
			newDate.appendChild(newSpace4.cloneNode(false));
			newDate.appendChild(newYear);
			
			Element newMonth = doc.createElement("flig:month");
			newMonth.setTextContent(""+date.getMonth());
			newDate.appendChild(newSpace4.cloneNode(false));
			newDate.appendChild(newMonth);
			
			Element newDay = doc.createElement("flig:day");
			newDay.setTextContent(""+date.getDay());
			newDate.appendChild(newSpace4.cloneNode(false));
			newDate.appendChild(newDay);
			
			Node newSpace3 = doc.createTextNode("\n				");
			newDate.appendChild(newSpace2.cloneNode(false));
			
			newBooking.appendChild(newSpace2.cloneNode(false));
			newBooking.appendChild(newDate);
			
			newBooking.appendChild(newSpace1.cloneNode(false));
			bookings.appendChild(newSpace1.cloneNode(false));
			bookings.appendChild(newBooking);
		}
		//bookings.appendChild(newSpace1.cloneNode(false));
		
	}
}
