package za.co.africanbank.datascience.abdocs.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "ABDocs_Documents", catalog = "ABDocs", schema = "dbo")
public class ABDocs_Documents {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="System_uuid")
	private String System_UUID;
	@Column(name="Idnumber")
	private String IDNumber;
	@Column(name="Attachement")
	private String  Attachement;
	@Column(name="Filename")
	private String FileName;
	@Column(name="Category")
	private String Category;
	@Column(name="Sqlcreationdate")
	private String SQLCreationDate;
	@Column(name="Uuid")
	private String UUID;
	@Column(name="Status")
	private String Status;

	public ABDocs_Documents() {
		
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}

	public String getAttachement() {
		return Attachement;
	}

	public void setAttachement(String string) {
		Attachement = string;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
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
	
	
	
	
	
	

}
