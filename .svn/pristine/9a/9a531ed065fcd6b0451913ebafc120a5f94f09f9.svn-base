package za.co.africanbank.datascience.abdocs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ABDocs_Logging", catalog = "ABDocs", schema = "dbo")
public class ABDocs_Logging {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="System_uuid")
	private String System_UUID;
	@Column(name="Email_uuid")
	private String Email_UUID;
	@Column(name="Sqlcreationdate")
	private String SQLCreationDate;
	@Column(name="Completeddate")
	private String CompletedDate;
	@Column(name="Username")
	private String Username;


	public ABDocs_Logging() {
		// TODO Auto-generated constructor stub
	}


	public String getSystem_UUID() {
		return System_UUID;
	}


	public void setSystem_UUID(String system_UUID) {
		System_UUID = system_UUID;
	}


	public String getEmail_UUID() {
		return Email_UUID;
	}


	public void setEmail_UUID(String email_UUID) {
		Email_UUID = email_UUID;
	}


	public String getSQLCreationDate() {
		return SQLCreationDate;
	}


	public void setSQLCreationDate(String sQLCreationDate) {
		SQLCreationDate = sQLCreationDate;
	}


	public String getCompletedDate() {
		return CompletedDate;
	}


	public void setCompletedDate(String completedDate) {
		CompletedDate = completedDate;
	}


	public String getUsername() {
		return Username;
	}


	public void setUsername(String username) {
		Username = username;
	}



}
