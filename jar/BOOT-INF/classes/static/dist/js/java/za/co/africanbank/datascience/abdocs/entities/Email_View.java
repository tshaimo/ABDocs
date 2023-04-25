package za.co.africanbank.datascience.abdocs.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Email_View", catalog = "ABDocs", schema = "dbo")
public class Email_View {
	@Id
	@Column(name="Idnumber")
	private String IDNumber;
	@Column(name="Fromaddress")
	private String FromAddress;
	@Column(name="Rownumber")
	private String RowNumber;
	
	public Email_View() {
		
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}

	public String getFromAddress() {
		return FromAddress;
	}

	public void setFromAddress(String fromAddress) {
		FromAddress = fromAddress;
	}

	public String getRowNumber() {
		return RowNumber;
	}

	public void setRowNumber(String rowNumber) {
		RowNumber = rowNumber;
	}
	
	

	

}
