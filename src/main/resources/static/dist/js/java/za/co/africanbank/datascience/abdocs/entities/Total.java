package za.co.africanbank.datascience.abdocs.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "Total", catalog = "ABDocs", schema = "dbo")
public class Total {
	@Id
	@Column(name="Rownumber")
	private String RowNumber;
	
	public Total() {
		
	}
	public String getRowNumber() {
		return RowNumber;
	}
	public void setRowNumber(String rowNumber) {
		RowNumber = rowNumber;
	}
	
	

}
