package za.co.africanbank.datascience.abdocs.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ABDocs_Emails", catalog = "ABDocs", schema = "dbo")
public class ABDocs_Emails {
	@Id
	@Column(name="Uuid")
	private String UUID;
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="System_uuid")
	private String System_UUID;
	@Column(name="Idnumber")
	private String IDNumber;
	@Column(name="Body")
	private String Body;
	@Column(name="Cleanedbody")
	private String CleanedBody;
	@Column(name="Datesent")
	private String DateSent;
	@Column(name="Flag")
	private String Flag;
	@Column(name="Fromaddress")
	private String FromAddress;
	@Column(name="Identifier")
	private String Identifier;
	@Column(name="Mimecast")
	private String Mimecast;
	@Column(name="Scenario")
	private String Scenario;
	@Column(name="Sqlcreationdate")
	private String SQLCreationDate;
	@Column(name="Status")
	private String Status;
	@Column(name="Username")
	private String Username;


	public ABDocs_Emails() {
		
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	public String getBody() {
		return Body;
	}

	public void setBody(String body) {
		Body = body;
	}

	public String getCleanedBody() {
		return CleanedBody;
	}

	public void setCleanedBody(String cleanedBody) {
		CleanedBody = cleanedBody;
	}

	public String getDateSent() {
		return DateSent;
	}


	public void setDateSent(String dateSent) {
		DateSent = dateSent;
	}


	public String getFlag() {
		return Flag;
	}


	public void setFlag(String flag) {
		Flag = flag;
	}


	public String getFromAddress() {
		return FromAddress;
	}


	public void setFromAddress(String fromAddress) {
		FromAddress = fromAddress;
	}


	public String getIdentifier() {
		return Identifier;
	}

	public void setIdentifier(String identifier) {
		Identifier = identifier;
	}


	public String getMimecast() {
		return Mimecast;
	}

	public void setMimecast(String mimecast) {
		Mimecast = mimecast;
	}


	public String getScenario() {
		return Scenario;
	}

	public void setScenario(String scenario) {
		Scenario = scenario;
	}

	public String getSQLCreationDate() {
		return SQLCreationDate;
	}

	public void setSQLCreationDate(String sQLCreationDate) {
		SQLCreationDate = sQLCreationDate;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getSystem_UUID() {
		return System_UUID;
	}

	public void setSystem_UUID(String system_UUID) {
		System_UUID = system_UUID;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}
	
	
	
	
	
	
	
	

}
