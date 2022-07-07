package za.co.africanbank.datascience.abdocs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ABDocs_usersactivity ", catalog = "ABDocs", schema = "dbo")
public class ABDocs_UsersActivity {
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid") 
	@Column(name="System_uuid")
	private String System_UUID;		
	@Column(name=" Username")
	private String Username;
	@Column(name="Login_time")
	private String Login_Time;
	@Column(name="Logout_time")
	private String Logout_Time;
	
	public ABDocs_UsersActivity() {
		
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

	public String getLogin_Time() {
		return Login_Time;
	}

	public void setLogin_Time(String login_Time) {
		Login_Time = login_Time;
	}

	public String getLogout_Time() {
		return Logout_Time;
	}

	public void setLogout_Time(String logout_Time) {
		Logout_Time = logout_Time;
	}
	
	

}
