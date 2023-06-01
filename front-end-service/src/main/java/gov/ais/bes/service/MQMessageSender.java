
package gov.ais.bes.service;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import javax.ejb.Stateless;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class MQMessageSender.
 */
@Stateless
public class MQMessageSender {

	/** The Constant CHANNEL. */
	private static final String CHANNEL = "DEMO.SVRCONN";
	
	/** The context. */
	private JMSContext context = null;
	
	/** The destination. */
	private Destination destination = null;
	
	/** The producer. */
	private JMSProducer producer = null;

	/**
	 * Send message.
	 *
	 * @param messageText the message text
	 * @param qmgrName the qmgr name
	 * @param mqHost the mq host
	 * @param mqPort the mq port
	 * @param QUEUE_NAME the queue name
	 * @throws Exception the exception
	 */
	public void sendMessage(String messageText, String qmgrName, String mqHost, int mqPort, String QUEUE_NAME)
			throws Exception {
		
		JmsFactoryFactory ff = JmsFactoryFactory.getInstance("com.ibm.msg.client.wmq");
		JmsConnectionFactory cf = ff.createConnectionFactory();

		try {
			cf.setStringProperty("XMSC_WMQ_HOST_NAME", mqHost);
			cf.setIntProperty("XMSC_WMQ_PORT", mqPort);
			cf.setStringProperty("XMSC_WMQ_CHANNEL", CHANNEL);
			cf.setIntProperty("XMSC_WMQ_CONNECTION_MODE", 1);
			cf.setStringProperty("XMSC_WMQ_QUEUE_MANAGER", qmgrName);
			cf.setStringProperty("XMSC_WMQ_APPNAME", "JmsPutGet (JMS)");
			
			this.context = cf.createContext();
			this.destination = this.context.createQueue("queue:///" + QUEUE_NAME);
			
			TextMessage message = this.context.createTextMessage(messageText);
			this.producer = this.context.createProducer();
			this.producer.send(this.destination, message);
		
			System.out.println("\n------- Sent MQ message: -------\n" + messageText+"\n\n");
			
			this.context.close();
			
		} catch (Exception var9) {
			var9.printStackTrace();
			throw var9;
		}
	}
}
