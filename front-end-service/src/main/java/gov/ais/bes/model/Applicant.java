
package gov.ais.bes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class Applicant.
 */
@XmlRootElement(namespace = "http://www.ibm.com/rules/decisionservice/Bes_deployment/Bes_decision_service/param")
@XmlAccessorType(XmlAccessType.FIELD)
public class Applicant {

	/** The first name. */
	@XmlElement
	@NotEmpty(message = "First Name cannot be null")
	private String firstName;

	/** The last name. */
	@XmlElement
	@NotEmpty(message = "Last Name cannot be null")
	private String lastName;

	/** The ssn. */
	@XmlElement
	@NotEmpty(message = "SSN cannot be null")
	private String ssn;

	/** The yearly income. */
	@XmlElement
	@Min(value=0, message = "Yearly income cannot be null" )
	private int yearlyIncome;

	/** The age. */
	@XmlElement
	@Min(value=0, message = "Age cannot be null" )
	private int age;

	/** The married. */
	@XmlElement
	@NotNull(message = "Married Status cannot be null")
	private boolean married;

	/** The street. */
	@XmlElement
	@NotEmpty(message = "Street cannot be null")
	private String street;

	/** The city. */
	@XmlElement
	@NotEmpty(message = "City cannot be null")
	private String city;

	/** The state. */
	@XmlElement
	@NotEmpty(message = "State cannot be null")
	private String state;

	/** The zip code. */
	@XmlElement
	@Min(value=0, message = "Zipcode cannot be null" )
	private int zipCode;

	/** The messages. */
	private Collection<String> messages;
	
	/** The approved. */
	private boolean approved;

	/**
	 * Instantiates a new applicant.
	 */
	public Applicant() {
		this.messages = new ArrayList<String>();
		this.approved = true;
	}

	/**
	 * Instantiates a new applicant.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param ssn the ssn
	 * @param yearlyIncome the yearly income
	 * @param age the age
	 * @param married the married
	 * @param street the street
	 * @param city the city
	 * @param state the state
	 * @param zipCode the zip code
	 */
	public Applicant(String firstName, String lastName, String ssn, int yearlyIncome, int age, boolean married,
			String street, String city, String state, int zipCode) {
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
		this.yearlyIncome = yearlyIncome;
		this.age = age;
		this.married = married;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the ssn.
	 *
	 * @return the ssn
	 */
	public String getSsn() {
		return this.ssn;
	}

	/**
	 * Sets the ssn.
	 *
	 * @param ssn the new ssn
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * Gets the yearly income.
	 *
	 * @return the yearly income
	 */
	public int getYearlyIncome() {
		return this.yearlyIncome;
	}

	/**
	 * Sets the yearly income.
	 *
	 * @param yearlyIncome the new yearly income
	 */
	public void setYearlyIncome(int yearlyIncome) {
		this.yearlyIncome = yearlyIncome;
	}

	/**
	 * Gets the age.
	 *
	 * @return the age
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * Sets the age.
	 *
	 * @param age the new age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Checks if is married.
	 *
	 * @return true, if is married
	 */
	public boolean isMarried() {
		return this.married;
	}

	/**
	 * Sets the married.
	 *
	 * @param married the new married
	 */
	public void setMarried(boolean married) {
		this.married = married;
	}

	/**
	 * Gets the messages.
	 *
	 * @return the messages
	 */
	public Collection<String> getMessages() {
		return this.messages;
	}

	/**
	 * Removes the from messages.
	 *
	 * @param argument the argument
	 */
	public void removeFromMessages(String argument) {
		this.messages.remove(argument);
	}

	/**
	 * Adds the to messages.
	 *
	 * @param argument the argument
	 */
	public void addToMessages(String argument) {
		this.messages.add(argument);
	}

	/**
	 * Checks if is approved.
	 *
	 * @return true, if is approved
	 */
	public boolean isApproved() {
		return this.approved;
	}

	/**
	 * Sets the approved.
	 *
	 * @param approved the new approved
	 */
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	/**
	 * Reject applicant.
	 */
	public void rejectApplicant() {
		this.approved = false;
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return this.street;
	}

	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the zip code.
	 *
	 * @return the zip code
	 */
	public int getZipCode() {
		return this.zipCode;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode the new zip code
	 */
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Sets the messages.
	 *
	 * @param messages the new messages
	 */
	public void setMessages(Collection<String> messages) {
		this.messages = messages;
	}

	/**
	 * Gets the approval status.
	 *
	 * @return the approval status
	 */
	@JsonIgnore
	public String getApprovalStatus() {
		return String.valueOf(this.approved) + " " + this.messages.toString();
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return "Applicant [firstName=" + this.firstName + ", lastName=" + this.lastName + ", ssn=" + this.ssn
				+ ", yearlyIncome=" + this.yearlyIncome + ", age=" + this.age + ", married=" + this.married
				+ ", street=" + this.street + ", city=" + this.city + ", state=" + this.state + ", zipCode="
				+ this.zipCode + ", messages=" + this.messages + ", approved=" + this.approved + "]";
	}

}
