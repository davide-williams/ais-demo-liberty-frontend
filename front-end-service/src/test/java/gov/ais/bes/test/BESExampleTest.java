package gov.ais.bes.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import gov.ais.bes.model.Applicant;

public class BESExampleTest {

	@Test
	public void applicanApprovalStatus() {
		
		//Instantiate a new applicant
		Applicant applicant = new Applicant();

		// Assert the new Applicant is approved by default
		assertTrue("Applicant is Approved", applicant.isApproved());
	}

}
