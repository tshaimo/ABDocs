package za.co.africanbank.datascience.abdocs.dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Root{
	
    @JsonProperty("ID_number") 
    public String iD_number;
    public List<Attachment> attachments_;
    public String cleaned_body;
    public String date_sent;
    public String flag;
    public String from_address;
    public String identifier;
    public int mimecast;
    public List<String> release_links;
    public int scenario;
    public String body;
    
	public String getiD_number() {
		return iD_number;
	}
	public void setiD_number(String iD_number) {
		this.iD_number = iD_number;
	}
	public List<Attachment> getAttachments_() {
		return attachments_;
	}
	public void setAttachments_(List<Attachment> attachments_) {
		this.attachments_ = attachments_;
	}
	public String getCleaned_body() {
		return cleaned_body;
	}
	public void setCleaned_body(String cleaned_body) {
		this.cleaned_body = cleaned_body;
	}
	public String getDate_sent() {
		return date_sent;
	}
	public void setDate_sent(String date_sent) {
		this.date_sent = date_sent;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFrom_address() {
		return from_address;
	}
	public void setFrom_address(String from_address) {
		this.from_address = from_address;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public int getMimecast() {
		return mimecast;
	}
	public void setMimecast(int mimecast) {
		this.mimecast = mimecast;
	}
	public List<String> getRelease_links() {
		return release_links;
	}
	public void setRelease_links(List<String> release_links) {
		this.release_links = release_links;
	}
	public int getScenario() {
		return scenario;
	}
	public void setScenario(int scenario) {
		this.scenario = scenario;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
    
    
    
    
    
}