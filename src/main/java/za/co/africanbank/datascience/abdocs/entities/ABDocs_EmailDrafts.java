package za.co.africanbank.datascience.abdocs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ABDocs_Emaildrafts", catalog = "ABDocs", schema = "dbo")
public class ABDocs_EmailDrafts {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="System_uuid")
	private String System_UUID;
	@Column(name="Username")
	private String Username;
	@Column(name="Subject")
	private String Subject;
	@Column(name="Email")
	private String Email;
	@Column(name="Emailuuid")
	private String EmailUUID;
	@Column(name="MSG_UUID")
	private String MSG_UUID;
	@Column(name="Param1")
	private String Param1;
	@Column(name="Param2")
	private String Param2;
	@Column(name="Param3")
	private String Param3;
	@Column(name="Param4")
	private String Param4;
	@Column(name="Sqlcreationdate")
	private String SQLCreationDate;
	@Column(name="Status")
	private String Status;
	@Column(name="emailto")
	private String emailto;
	@Column(name="Textbody")
	private String TextBody;

	public ABDocs_EmailDrafts() {
		
	}

	public String getSystem_UUID() {
		return System_UUID;
	}

	public void setSystem_UUID(String system_UUID) {
		System_UUID = system_UUID;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getMSG_UUID() {
		return MSG_UUID;
	}

	public void setMSG_UUID(String mSG_UUID) {
		MSG_UUID = mSG_UUID;
	}

	public String getParam1() {
		return Param1;
	}

	public void setParam1(String param1) {
		Param1 = param1;
	}

	public String getParam2() {
		return Param2;
	}

	public void setParam2(String param2) {
		Param2 = param2;
	}

	public String getParam3() {
		return Param3;
	}

	public void setParam3(String param3) {
		Param3 = param3;
	}

	public String getParam4() {
		return Param4;
	}

	public void setParam4(String param4) {
		Param4 = param4;
	}

	public String getSQLCreationDate() {
		return SQLCreationDate;
	}

	public void setSQLCreationDate(String sQLCreationDate) {
		SQLCreationDate = sQLCreationDate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getEmailto() {
		return emailto;
	}

	public void setEmailto(String emailto) {
		this.emailto = emailto;
	}

	public String getTextBody() {
		return TextBody;
	}

	public void setTextBody(String textBody) {
		TextBody = textBody;
	}

	public String getEmailUUID() {
		return EmailUUID;
	}

	public void setEmailUUID(String emailUUID) {
		EmailUUID = emailUUID;
	}
	
	
	
	

}
