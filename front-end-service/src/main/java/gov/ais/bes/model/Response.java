
package gov.ais.bes.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class Response.
 */
@XmlRootElement(name = "Response", namespace = "http://www.ibm.com/rules/decisionservice/Bes_deployment/Bes_decision_service/param")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {

	private String __DecisionID__;

	/** The applicant. */
	@XmlElement(namespace = "http://www.ibm.com/rules/decisionservice/Bes_deployment/Bes_decision_service/param")
	private Applicant applicant;

	/**
	 * Gets the applicant.
	 *
	 * @return the applicant
	 */
	public Applicant getApplicant() {
		return this.applicant;
	}

	/**
	 * Sets the applicant.
	 *
	 * @param applicant the new applicant
	 */
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public String get__DecisionID__() {
		return __DecisionID__;
	}

	public void set__DecisionID__(String __DecisionID__) {
		this.__DecisionID__ = __DecisionID__;
	}

}
