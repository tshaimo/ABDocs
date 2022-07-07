package za.co.africanbank.datascience.abdocs.jerseyclient;
import java.io.IOException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import za.co.africanbank.datascience.abdocs.constant.*;
import za.co.africanbank.datascience.abdocs.properties.properties;


public class JerseyClient {
	
	public String play(String Url, String InputJson) throws UniformInterfaceException, ClientHandlerException, IOException {
		Client restClient = Client.create();
		WebResource webResource = restClient.resource(Url);
		ClientResponse resp = webResource.type("application/json").header("Authorization", "Bearer " + new properties(constant.PROPERTYFILE.value()).read("Token")).accept("application/json").post(ClientResponse.class,InputJson);
		if(resp.getStatus() != 200){
	
		}
		String output = resp.getEntity(String.class);
        return output;
	}
}
