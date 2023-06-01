
package gov.ais.bes.controller;

import gov.ais.bes.model.Applicant;
import gov.ais.bes.model.Request;
import gov.ais.bes.model.Response;
import gov.ais.bes.service.MQMessageSender;
import gov.ais.bes.service.ODMRestClient;
import gov.ais.bes.utils.Utilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class BESValidate.
 */
@WebServlet({ "/bes-validate" })
public class BESValidate extends HttpServlet {

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

	/**
	 * Inits the.
	 *
	 * @param config the config
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {

		System.out.println("\n\n*** initializing the servlet ***");
		odmServer = System.getenv("BES_ODM_SERVER");
		odmRuleSet = System.getenv("BES_ODM_RULESET");
		mqServer = System.getenv("BES_MQ_SERVER");
		System.out.println("   ++++++++++++++++++++++++++++++++++++  ");
		mqPort = Integer.parseInt(System.getenv("BES_MQ_PORT"));
		mqQMGR = System.getenv("BES_MQ_QMGR");
		System.out.println("*** Finished initializing the servlet ***\n\n");
		
		// TODO Auto-generated method stub
		super.init(config);
	}

	/**
	 * Do get.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * Do post.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JsonReader jsonReader = Json.createReader(request.getInputStream());
		JsonObject json = jsonReader.readObject();
		jsonReader.close();
		String result = this.validate(json);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.write(result);
		pw.flush();
		pw.close();
	}

	/**
	 * Validate.
	 *
	 * @param json the json
	 * @return the string
	 */
	public String validate(JsonObject json) {

		JsonObject result = null;
		JsonObject applicationDecision = null;

		try {

			Applicant applicant = new Applicant(json.getString("name"), json.getString("last-name"),
					json.getString("ssn"), json.getInt("yearly-income"), json.getInt("age"),
					json.getString("married").toUpperCase().equals("YES"), json.getString("street"),
					json.getString("city"), json.getString("state"), json.getInt("zipcode"));

			Request request = new Request();
			request.setApplicant(applicant);

			System.out.println("\n\n\n __________Environment __________");
			System.out.println("ODM Server: " + odmServer);
			System.out.println("ODM Ruleset: " + odmRuleSet);
			System.out.println("MQ Server: " + mqServer);
			System.out.println("MQ Port: " + mqPort);
			System.out.println("MQ qmgr: " + mqQMGR);
			System.out.println(" __________Environment __________\n");

			this.mqMessageSender.sendMessage((new Date()).toString() + " - \n" + "+++ Preparing to execute BES ODM Ruleset +++", mqQMGR, mqServer, mqPort,
					"DEMO_STATUS");
			this.mqMessageSender.sendMessage((new Date()).toString() + " - \n" + "++ ODM rest call REQUEST DATA: ++ \n"
					+ Utilities.jaxbObjectToJSON(request), mqQMGR, mqServer, mqPort, "DEMO_STATUS");

			String odmRestCallResult = this.odmRestClient.callBESRuleSet(request, odmServer, odmRuleSet, mqQMGR,
					mqServer, mqPort);

			// Response response = (Response)Utilities.xmlStringToObject(odmRestCallResult,
			// Response.class);
			Response response = (Response) Utilities.jsonStringToObject(odmRestCallResult, Response.class);

			if (response.getApplicant().isApproved()) {
				applicationDecision = Json.createObjectBuilder()
						.add("DECISION", "Application Approved - Your application has been approved").build();
			} else {
				applicationDecision = Json.createObjectBuilder()
						.add("DECISION", response.getApplicant().getMessages().toString()).build();
			}

			result = Json.createObjectBuilder().add("approved", response.getApplicant().isApproved())
					.add("response", applicationDecision).build();
			this.mqMessageSender.sendMessage((new Date()).toString() + " - \n" + "+++ Finished executing BES ODM Ruleset +++", mqQMGR, mqServer, mqPort,
					"DEMO_STATUS");

			return result.toString();

		} catch (Exception var13) {
			var13.printStackTrace();
			return this.errorToJSON(var13);
		}
	}

	/**
	 * Error to JSON.
	 *
	 * @param e the e
	 * @return the string
	 */
	private String errorToJSON(Exception e) {
		JsonObject value = Json.createObjectBuilder().add("error", true).add("text", e.toString()).build();
		return value.toString();
	}
}
