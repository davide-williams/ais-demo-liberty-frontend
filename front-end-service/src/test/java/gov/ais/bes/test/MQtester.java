package gov.ais.bes.test;

import static org.junit.Assert.*;

import java.util.Iterator;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;

import org.junit.Test;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import gov.ais.bes.service.MQMessageSender;

public class MQtester {

	/** The context. */
	private JMSContext context = null;

	/** The destination. */
	private Destination destination = null;

	/** The producer. */
	private JMSProducer producer = null;

	@Test
	public void test() throws Exception {
		JmsFactoryFactory ff = JmsFactoryFactory.getInstance("com.ibm.msg.client.wmq");
		JmsConnectionFactory cf = ff.createConnectionFactory();

		try {
		//	System.out.println(getBigString());
			//char[] data = new 
			
			System.setProperty("javax.net.ssl.keyStore", "C:\\Development\\workspaces\\ais-demo-frontend\\front-end-service\\jks\\demo_qmgr.jks");
			System.setProperty("javax.net.ssl.keyStorePassword", "clientpass");
			System.setProperty("javax.net.ssl.trustStore", "C:\\Development\\workspaces\\ais-demo-frontend\\front-end-service\\jks\\demo_qmgr.jks");
			System.setProperty("javax.net.ssl.trustStorePassword", "clientpass");
			
			cf.setStringProperty("XMSC_WMQ_HOST_NAME",
					"ala74618");
			cf.setIntProperty("XMSC_WMQ_PORT", 12200);
			cf.setStringProperty("XMSC_WMQ_CHANNEL", "DEMO.SVRCONN");
			cf.setIntProperty("XMSC_WMQ_CONNECTION_MODE", WMQConstants.WMQ_CM_CLIENT);
			cf.setStringProperty("XMSC_WMQ_QUEUE_MANAGER", "DEMO_QMGR");
			cf.setStringProperty("XMSC_WMQ_APPNAME", "JmsPutGet (JMS)");
			cf.setStringProperty("XMSC_USERID", "aaDavid.E.Williams");
			cf.setStringProperty("XMSC_PASSWORD", "Q3D1j|a'NYk)9[Z");

	//		cf.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, "SSL_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
		    cf.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, "SSL_RSA_WITH_AES_128_CBC_SHA256");

			for (int i = 0; i < 5; i++) {

				this.context = cf.createContext();
				this.destination = this.context.createQueue("queue:///" + "IN");
//	

//				TextMessage message = this.context.createTextMessage("Test message from java app: " + i);
			    TextMessage message = this.context.createTextMessage(getBigString());
				this.producer = this.context.createProducer();
				this.producer.send(this.destination, message);
				System.out.println("\n------- Sent MQ message: -------\n" + "Test message from java app: " + i);
				// Thread.sleep(10000);

//		
				// System.out.println("\n------- Sent MQ message: -------\n" + messageText);

				this.context.close();
			}
		} catch (Exception var9) {
			var9.printStackTrace();
			throw var9;
		}
	}

	
	public String getBigString() {
		StringBuilder stringB = new StringBuilder(2000000); //for the 2mb one
		String paddingString = "abcdefghijklmnopqrs";

		while (stringB.length() + paddingString.length() < 2000000)
		 stringB.append(paddingString);

		//use it
		return stringB.toString();
		
	
	}
}
