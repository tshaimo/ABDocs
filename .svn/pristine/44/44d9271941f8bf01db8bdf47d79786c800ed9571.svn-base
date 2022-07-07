package za.co.africanbank.datascience.abdocs.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.Email_View;

@Repository
public interface ABDocsEmailViewRepository extends CrudRepository <Email_View, String>{
	@Query(value="SELECT TOP(4000) t.*\r\n" + 
			"FROM\r\n" + 
			"(\r\n" + 
			"    (SELECT IDNumber,\r\n" + 
			"        FromAddress,\r\n" + 
			"        Username,\r\n" + 
			"        COUNT(IDNumber) AS RowNumber,\r\n" + 
			"        MIN(SQLCreationDate) as EmailDate\r\n" + 
			"    FROM ABDocs_Emails\r\n" + 
			"    WHERE Username =?1 AND Status IN('Incomplete')\r\n" + 
			"    GROUP BY IDNumber,FromAddress,Username)\r\n" + 
			"    UNION\r\n" + 
			"    (SELECT IDNumber,\r\n" + 
			"        FromAddress,\r\n" + 
			"        Username,\r\n" + 
			"        COUNT(IDNumber) AS RowNumber,\r\n" + 
			"        MIN(SQLCreationDate) as EmailDate\r\n" + 
			"    FROM ABDocs_Emails\r\n" + 
			"    WHERE Username IS NULL AND Status IN('Incomplete')\r\n" + 
			"    GROUP BY IDNumber,FromAddress,Username)\r\n" + 
			") t\r\n" + 
			"GROUP BY IDNumber,FromAddress,Username,RowNumber,EmailDate\r\n" + 
			"ORDER BY Username desc,MIN(EmailDate) ASC",nativeQuery = true)
	List<Email_View> AllMails(@Param("Username")String Username);
	
	
	
	

}
