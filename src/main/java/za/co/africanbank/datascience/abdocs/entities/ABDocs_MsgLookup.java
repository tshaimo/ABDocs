package za.co.africanbank.datascience.abdocs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = " ABDocs_MsgLookup", catalog = "ABDocs", schema = "dbo")
public class ABDocs_MsgLookup {

	@Id
	@Column(name="MSG_UUID")
	private String MSG_UUID;
	@Column(name="Textbody")
	private String TextBody;
	@Column(name="Sqlcreationdate")
	private String SQLCreationDate;
	@Column(name="Msgtype")
	private String MsgType;
	@Column(name="purpose")
	private String purpose;
	@Column(name="subject")
	private String subject;

	public ABDocs_MsgLookup() {

	}

	public String getMSG_UUID() {
		return MSG_UUID;
	}

	public void setMSG_UUID(String mSG_UUID) {
		MSG_UUID = mSG_UUID;
	}

	public String getTextBody() {
		return TextBody;
	}

	public void setTextBody(String textBody) {
		TextBody = textBody;
	}

	public String getSQLCreationDate() {
		return SQLCreationDate;
	}

	public void setSQLCreationDate(String sQLCreationDate) {
		SQLCreationDate = sQLCreationDate;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	
	
	

}
