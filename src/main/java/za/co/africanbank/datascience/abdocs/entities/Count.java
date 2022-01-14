package za.co.africanbank.datascience.abdocs.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Count", catalog = "ABDocs", schema = "dbo")
public class Count {
	@Id
	@Column(name="Rowcounter")
	private String RowCounter;

	public Count() {
		
	}
	public String getRowCounter() {
		return RowCounter;
	}

	public void setRowCounter(String rowCounter) {
		RowCounter = rowCounter;
	}
	
	

}
