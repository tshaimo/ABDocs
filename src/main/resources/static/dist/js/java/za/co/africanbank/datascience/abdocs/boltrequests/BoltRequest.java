package za.co.africanbank.datascience.abdocs.boltrequests;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BoltRequest {

	@SuppressWarnings("unchecked")
	public String ClientSearch(String Idnumber) {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		obj.put("fieldname", "idnumber");
		obj.put("operator", "=");
		obj.put("value", Idnumber);
		obj.put("join", "");
		array.add(obj);
		return array.toString();
		
	}
	@SuppressWarnings("unchecked")
	public String ApplicationsSerach(String clientNumber) {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		obj.put("fieldname", "iclientnumber");
		obj.put("operator", "=");
		obj.put("Value", clientNumber);
		array.add(obj);
		return array.toString();
	}
}
