package za.co.africanbank.datascience.abdocs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ABDocs_Users", catalog = "ABDocs", schema = "dbo")
public class ABDocs_Users {


	@Id
	@Column(name="Username")
	private String Username;
	@Column(name="Roles")
	private String Role; 
	@Column(name="Fullnames")
	private String FullNames;
	@Column(name="Usercreatedby")
	private String UserCreatedBy;
	@Column(name="Sqlcreationdate")
	private String SQLCreationDate;


	public ABDocs_Users() {

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
	public String getUserCreatedBy() {
		return UserCreatedBy;
	}
	public void setUserCreatedBy(String userCreatedBy) {
		UserCreatedBy = userCreatedBy;
	}
	public String getSQLCreationDate() {
		return SQLCreationDate;
	}
	public void setSQLCreationDate(String sQLCreationDate) {
		SQLCreationDate = sQLCreationDate;
	}




}
