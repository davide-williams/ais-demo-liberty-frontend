package gov.ais.bes.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import gov.ais.bes.model.Applicant;

public class BESExampleTest {

	@Test
	public void applicanApprovalStatus() {
		Applicant applicant = new Applicant();

		// Assert
		assertTrue("Applicant is Approved", applicant.isApproved());
	}

}
