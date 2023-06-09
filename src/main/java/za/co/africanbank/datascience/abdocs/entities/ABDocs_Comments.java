package za.co.africanbank.datascience.abdocs.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ABDocs_Comments", catalog = "ABDocs", schema = "dbo")
public class ABDocs_Comments {
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="System_uuid")
	private String System_UUID;
	@Column(name="Document_uuid")
	private String Document_UUID;
	@Column(name="Sqlcreationdate")
	private String SQLCreationDate;
	@Column(name="Comment")
	private String Comment;
	@Column(name="Username")
	private String Username;
	@Column(name="Fullnames")
	private String Fullnames;
	@Column(name="Commenttype")
	private String CommentType;
	@Column(name="Idnumber")
	private String IDNumber;

	public ABDocs_Comments() {
	
	}

	public String getSystem_UUID() {
		return System_UUID;
	}

	public void setSystem_UUID(String system_UUID) {
		System_UUID = system_UUID;
	}

	public String getDocument_UUID() {
		return Document_UUID;
	}

	public void setDocument_UUID(String document_UUID) {
		Document_UUID = document_UUID;
	}

	public String getSQLCreationDate() {
		return SQLCreationDate;
	}

	public void setSQLCreationDate(String sQLCreationDate) {
		SQLCreationDate = sQLCreationDate;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getFullnames() {
		return Fullnames;
	}

	public void setFullnames(String fullnames) {
		Fullnames = fullnames;
	}

	public String getCommentType() {
		return CommentType;
	}

	public void setCommentType(String commentType) {
		CommentType = commentType;
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	
	

	
	
	
	
	
	

}
