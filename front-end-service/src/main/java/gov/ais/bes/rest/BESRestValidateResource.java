package gov.ais.bes.rest;

import java.util.Date;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import gov.ais.bes.model.Applicant;
import gov.ais.bes.service.MQMessageSender;
import gov.ais.bes.service.ODMRestClient;
import gov.ais.bes.utils.Utilities;

// TODO: Auto-generated Javadoc
/**
 * The Class BESRestValidateResource.
 */
@Path("/application")
public class BESRestValidateResource {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The odm rest client. */
	@Inject
	private ODMRestClient odmRestClient;

	/** The mq message sender. */
	@Inject
	MQMessageSender mqMessageSender;

	/** The odm server. */
	private String odmServer;
	
	/** The odm rule set. */
	private String odmRuleSet;
	
	/** The mq server. */
	private String mqServer;
	
	/** The mq port. */
	private int mqPort;
	
	/** The mq QMGR. */
	private String mqQMGR;
	
	
	public BESRestValidateResource(){		
		System.out.println("\n\n*** initializing the Rest enpoint ***");
		odmServer = System.getenv("BES_ODM_SERVER");
		odmRuleSet = System.getenv("BES_ODM_RULESET");
		mqServer = System.getenv("BES_MQ_SERVER");
		System.out.println("   ++++++++++++++++++++++++++++++++++++  ");
		mqPort = Integer.parseInt(System.getenv("BES_MQ_PORT"));
		mqQMGR = System.getenv("BES_MQ_QMGR");
		System.out.println("*** .Finished initializing the Rest endpoint ***\n\n");
		}
	
	private void showEnvironment() {
		System.out.println(" __________Environment __________");
		System.out.println("ODM Server: " + odmServer);
		System.out.println("ODM Ruleset: " + odmRuleSet);
		System.out.println("MQ Server: " + mqServer);
		System.out.println("MQ Port: " + mqPort);
		System.out.println("MQ qmgr: " + mqQMGR);
		System.out.println(" __________Environment __________\n");
	}
	
	/**
	 * Ping.
	 *
	 * @return the response
	 */
	@GET
	@Path("/ping")
	public Response ping() {
		showEnvironment();
		System.out.println("--- applicant REST api pinged ----");
		return Response.ok().entity("Applicant api online").build();
	}

	/**
	 * Post notification.
	 *
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postNotification(gov.ais.bes.model.Request request) {
		
		String beanValidationMessagaes = null;
		String jsonResponse = null;
		
		try {
			System.out.println(request);
			beanValidationMessagaes = validateRequest(request.getApplicant());
			if(!beanValidationMessagaes.trim().isEmpty()) {
				return Response.status(Response.Status.BAD_REQUEST).entity("You request has the following errors:\n"+beanValidationMessagaes).build();
			}

			jsonResponse = validate(request);
			return Response.status(201).entity(jsonResponse).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	
	public String validate(gov.ais.bes.model.Request request) throws Exception{
		mqMessageSender.sendMessage((new Date()).toString() + " - \n" + "+++ Preparing to execute BES ODM Ruleset +++", mqQMGR, mqServer, mqPort,
				"DEMO_STATUS");
		mqMessageSender.sendMessage((new Date()).toString() + " - \n" + "++ ODM rest call REQUEST DATA: ++ \n"
				+ Utilities.jaxbObjectToJSON(request), mqQMGR, mqServer, mqPort, "DEMO_STATUS");
		
		String odmRestCallResult = this.odmRestClient.callBESRuleSet(request, odmServer, odmRuleSet, mqQMGR,
				mqServer, mqPort);
		System.out.println(odmRestCallResult);
		
		mqMessageSender.sendMessage((new Date()).toString() + " - \n" + "+++ Finished executing BES ODM Ruleset +++", mqQMGR, mqServer, mqPort,
				"DEMO_STATUS");
		
		return odmRestCallResult;
	}
	
	/**
	 * Validate request.
	 *
	 * @param request the request
	 * @return the string
	 */
	private String validateRequest(Applicant applicant) {
		String validationMessages = new String();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();		
		Set<ConstraintViolation<Applicant>> violations = validator.validate(applicant);
		
		for (ConstraintViolation<Applicant> violation : violations) {
			validationMessages+="\n";
			validationMessages+= violation.getMessage(); 
		}
		
		return validationMessages;
	}
}
