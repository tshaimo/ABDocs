package za.co.africanbank.datascience.abdocs.xmlhandler;

import java.io.IOException;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import za.co.africanbank.datascience.abdocs.constant.constant;

public class requiredResponse {

	public String playResponse(String inputXML) throws JDOMException, IOException {
		
		xmlHandler handler = new xmlHandler();
		Element element = handler.stringToJDOMDocument(inputXML.replace(constant.ENCODING.value(),"")).getRootElement().clone();
		if (element.getChild("RequestedDocument") != null){		
			String output = handler.jdomElementToString(element.getChild("RequestedDocument").clone());
				return handler.stringToJDOMDocument(output).getRootElement().getChild("status").getChildText("documentStatusMessage");
			}

		return handler.jdomElementToString(element);
	}

}
