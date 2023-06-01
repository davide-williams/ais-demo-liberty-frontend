
package gov.ais.bes.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class Request.
 */
@XmlRootElement(
   name = "Request",
   namespace = "http://www.ibm.com/rules/decisionservice/Bes_deployment/Bes_decision_service/param"
)
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {
   
   /** The applicant. */
   @XmlElement(
      namespace = "http://www.ibm.com/rules/decisionservice/Bes_deployment/Bes_decision_service/param"
   )
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

   /**
    * To string.
    *
    * @return the string
    */
   public String toString() {
      return "Request [applicant=" + this.applicant + "]";
   }
}
