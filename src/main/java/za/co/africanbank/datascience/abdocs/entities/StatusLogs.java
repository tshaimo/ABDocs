package za.co.africanbank.datascience.abdocs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ABDocsstatuslogs", catalog = "ABDocs", schema = "dbo")
public class StatusLogs {
	@Id
	@Column(name="uniqueid")
	private String uniqueID;
	@Column(name="actiontype")
	private int actionType; 
	@Column(name="username")
	private String userName;
	@Column(name="fromstatus")
	private String fromStatus;
	@Column(name="tostatus")
	private String toStatus;
	@Column(name="sqlupdatedate")
	private String sqlUpdateDate;
	
	public String getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFromStatus() {
		return fromStatus;
	}
	public void setFromStatus(String fromStatus) {
		this.fromStatus = fromStatus;
	}
	public String getToStatus() {
		return toStatus;
	}
	public void setToStatus(String toStatus) {
		this.toStatus = toStatus;
	}
	public String getSqlUpdateDate() {
		return sqlUpdateDate;
	}
	public void setSqlUpdateDate(String sqlUpdateDate) {
		this.sqlUpdateDate = sqlUpdateDate;
	}
}
