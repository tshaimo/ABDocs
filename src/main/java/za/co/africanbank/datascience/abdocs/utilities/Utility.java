package za.co.africanbank.datascience.abdocs.utilities;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Utility {

	public String getCreationDate() {
		
		LocalDateTime now = LocalDateTime.now();  
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		String formatDateTime = now.format(format); 
		return formatDateTime;
	}
}
