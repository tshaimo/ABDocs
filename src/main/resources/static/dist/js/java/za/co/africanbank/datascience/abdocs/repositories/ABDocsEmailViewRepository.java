package za.co.africanbank.datascience.abdocs.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.Email_View;

@Repository
public interface ABDocsEmailViewRepository extends CrudRepository <Email_View, String>{
	@Query(value="SELECT IDNumber,FromAddress,COUNT(IDNumber) AS RowNumber\r\n" + 
			"FROM ABDocs_Emails WHERE Status='Incomplete' AND Username IS NULL OR Username=?1 GROUP BY IDNumber,FromAddress",nativeQuery = true)
	List<Email_View> AllMails(@Param("Username")String Username);
	
	
	
	

}
