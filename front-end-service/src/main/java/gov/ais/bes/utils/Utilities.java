package gov.ais.bes.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

// TODO: Auto-generated Javadoc
/**
 * The Class Utilities.
 * @author dan44862
 */
public class Utilities {

	/** The Constant BES_DATA_QUEUE_NAME. */
	public static final String BES_DATA_QUEUE_NAME = "BES_IN";

	/** The Constant BES_STATUS_QUEUE_NAME. */
	public static final String BES_STATUS_QUEUE_NAME = "DEMO_STATUS";

	/**
	 * Jaxb object to XML.
	 *
	 * @param target the target
	 * @param type   the type
	 * @return the string
	 */
	public static String jaxbObjectToXML(Object target, Class<?> type) {
		String xmlContent = null;

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(type);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(target, sw);
			xmlContent = sw.toString();

		} catch (JAXBException var6) {
			var6.printStackTrace();
		}

		return xmlContent;
	}

	/**
	 * Jaxb object to JSON.
	 *
	 * @param target the target
	 * @return the string
	 */
	public static String jaxbObjectToJSON(Object target) {
		String jsonContent = null;

		try {

			ObjectMapper mapper = (new ObjectMapper()).enable(SerializationFeature.INDENT_OUTPUT);
			jsonContent = mapper.writeValueAsString(target);
		} catch (JsonProcessingException var3) {
			var3.printStackTrace();
		}

		return jsonContent;
	}

	/**
	 * Gets the XML value.
	 *
	 * @param <T>  the generic type
	 * @param xml  the xml
	 * @param type the type
	 * @return the XML value
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlStringToObject(String xml, Class<?> type) {

		T obj = null;

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(type);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			T value = (T) jaxbUnmarshaller.unmarshal(new StringReader(xml));
			obj = value;
		} catch (JAXBException var6) {
			var6.printStackTrace();
		}

		return obj;
	}

	/**
	 * Json string to object.
	 *
	 * @param <T> the generic type
	 * @param json the json
	 * @param type the type
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonStringToObject(String json, Class<?> type) {
		
		T obj = null;
		ObjectMapper objectMapper = new ObjectMapper();	
		try {
			obj = (T) objectMapper.readValue(json, type);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
	}

}
