
package gov.ais.bes.service;

import gov.ais.bes.model.Request;
import gov.ais.bes.utils.Utilities;

import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

// TODO: Auto-generated Javadoc
/**
 * The Class ODMRestClient.
 */
@ApplicationScoped
public class ODMRestClient {
   
   /** The mq message sender. */
   @Inject
   MQMessageSender mqMessageSender;
   
   /** The client. */
   Client client = ClientBuilder.newClient();

   /**
    * Call BES rule set.
    *
    * @param request the request
    * @param server the server
    * @param ruleSet the rule set
    * @param mqQMGR the mq QMGR
    * @param mqServer the mq server
    * @param mqPort the mq port
    * @return the string
    * @throws Exception the exception
    */
   public String callBESRuleSet(Request request, String server, String ruleSet, String mqQMGR, String mqServer, int mqPort) throws Exception {
      String odmURL = server + "/DecisionService/rest/v1/" + ruleSet;
      String stringResponse = "no-respone";

      try {
    	  
       //  Response response = (Response)this.client.target(odmURL).request(new String[]{"application/xml"}).post(Entity.entity(request, "application/xml"), Response.class);
         
         Response response = (Response)this.client.target(odmURL).request(new String[]{"application/json"}).post(Entity.entity(Utilities.jaxbObjectToJSON(request), "application/json"), Response.class);
      
         this.mqMessageSender.sendMessage((new Date()).toString() + " - \n" + "++ ODM rest call RESPONSE STATUS CODE: ++ \n" + response.getStatus(), mqQMGR, mqServer, mqPort, "DEMO_STATUS");
         
         if (response.getStatus() == Status.OK.getStatusCode()) {
            stringResponse = (String)response.readEntity(String.class);
            this.mqMessageSender.sendMessage((new Date()).toString() + " - \n" + "++ ODM rest call RESPONSE DATA: ++ \n"  + stringResponse, mqQMGR, mqServer, mqPort, "DEMO_STATUS");
            this.mqMessageSender.sendMessage("" + stringResponse, mqQMGR, mqServer, mqPort, "BES_IN");
            return stringResponse;
         } else {
            throw new Exception("error invoking rest endpoint");
         }
         
      } catch (Exception var10) {
         var10.printStackTrace();
         throw var10;
      }
   }
}
