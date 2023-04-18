package za.co.africanbank.datascience.abdocs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ABDocs_Userstatus", catalog = "ABDocs", schema = "dbo")
public class ABDocs_UserStatus {


	@Id
	@Column(name="Username")
	private String Username;
	@Column(name="Roles")
	private String Role; 
	@Column(name="Fullnames")
	private String FullNames;
	@Column(name="Status")
	private String Status;
	@Column(name="SQLupdatedate")
	private String SQLUpdateDate;


	public ABDocs_UserStatus() {

	}

	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
	public String getFullNames() {
		return FullNames;
	}
	public void setFullNames(String fullNames) {
		FullNames = fullNames;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String Status) {
		this.Status = Status;
	}
	public String getSQLUpdateDate() {
		return SQLUpdateDate;
	}
	public void setSQLUpdateDate(String SQLUpdateDate) {
		this.SQLUpdateDate = SQLUpdateDate;
	}




}
