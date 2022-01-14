package za.co.africanbank.datascience.abdocs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ABDocs_Links", catalog = "ABDocs", schema = "dbo")
public class ABDocs_Links {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="System_uuid")
	private String System_UUID;
	@Column(name="Idnumber")
	private String IDNumber;
	@Column(name="Releaselinks")
	private String ReleaseLinks;
	@Column(name="Releaselinksid")
	private String ReleaseLinksID;
	@Column(name="Sqlcreationdate")
	private String SQLCreationDate;
	@Column(name="Uuid")
	private String UUID;
	@Column(name="Status")
	private String Status;

	public ABDocs_Links() {
	
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}

	public String getReleaseLinks() {
		return ReleaseLinks;
	}

	public void setReleaseLinks(String releaseLinks) {
		ReleaseLinks = releaseLinks;
	}

	public String getReleaseLinksID() {
		return ReleaseLinksID;
	}

	public void setReleaseLinksID(String releaseLinksID) {
		ReleaseLinksID = releaseLinksID;
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
