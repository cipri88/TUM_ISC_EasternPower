
/**
 * G29FlightTicketImplCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package SC13Project.Milestone1.FlightTicket.Client;

    /**
     *  G29FlightTicketImplCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class G29FlightTicketImplCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public G29FlightTicketImplCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public G29FlightTicketImplCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for bookFlight method
            * override this method for handling normal response from bookFlight operation
            */
           public void receiveResultbookFlight(
                    SC13Project.Milestone1.FlightTicket.Client.G29FlightTicketImplStub.BookFlightResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from bookFlight operation
           */
            public void receiveErrorbookFlight(java.lang.Exception e) {
            }
                
               // No methods generated for meps other than in-out
                
           /**
            * auto generated Axis2 call back method for getFlightInfo method
            * override this method for handling normal response from getFlightInfo operation
            */
           public void receiveResultgetFlightInfo(
                    SC13Project.Milestone1.FlightTicket.Client.G29FlightTicketImplStub.GetFlightInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFlightInfo operation
           */
            public void receiveErrorgetFlightInfo(java.lang.Exception e) {
            }
                


    }
    