
/**
 * FlightTicketImplFlightUnAvailableExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package SC13Project.Milestone1.FlightTicket.Client;

public class FlightTicketImplFlightUnAvailableExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1384433416390L;
    
    private SC13Project.Milestone1.FlightTicket.Client.G29FlightTicketImplStub.FlightTicketImplFlightUnAvailableException faultMessage;

    
        public FlightTicketImplFlightUnAvailableExceptionException() {
            super("FlightTicketImplFlightUnAvailableExceptionException");
        }

        public FlightTicketImplFlightUnAvailableExceptionException(java.lang.String s) {
           super(s);
        }

        public FlightTicketImplFlightUnAvailableExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public FlightTicketImplFlightUnAvailableExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(SC13Project.Milestone1.FlightTicket.Client.G29FlightTicketImplStub.FlightTicketImplFlightUnAvailableException msg){
       faultMessage = msg;
    }
    
    public SC13Project.Milestone1.FlightTicket.Client.G29FlightTicketImplStub.FlightTicketImplFlightUnAvailableException getFaultMessage(){
       return faultMessage;
    }
}
    