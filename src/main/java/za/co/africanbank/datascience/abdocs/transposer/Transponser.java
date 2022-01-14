package za.co.africanbank.datascience.abdocs.transposer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.africanbank.datascience.abdocs.dto.Root;

public class Transponser {
	
	public Root play(String myJsonString) throws JsonMappingException, JsonProcessingException
	{
		ObjectMapper om = new ObjectMapper();
		om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		Root root = om.readValue((myJsonString), Root.class);
		return root;
	}
}
