package za.co.africanbank.datascience.abdocs.processes;

import java.io.IOException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;
import za.co.africanbank.datascience.abdocs.constant.*;
import za.co.africanbank.datascience.abdocs.properties.properties;

public class ABDocsServiceClient extends Thread{
	

	private String targetURL;
	private WebServiceTemplate wsclient;

	public ABDocsServiceClient(String inTargetURL) throws NumberFormatException, IOException{
		targetURL = inTargetURL;        
		HttpComponentsMessageSender sender = new HttpComponentsMessageSender();
		sender.setConnectionTimeout(new Integer(new properties(constant.PROPERTYFILE.value()).read("connection_timeout")));
		sender.setReadTimeout(new Integer(new properties(constant.PROPERTYFILE.value()).read("read_timeout")));        
		wsclient = new WebServiceTemplate();
		wsclient.setMessageSender(sender);
	}

	public String play(String inputXML){
		StringResult res = new StringResult();
		wsclient.sendSourceAndReceiveToResult(targetURL, new StringSource(inputXML), res);
		return res.getWriter().toString();
	}
}