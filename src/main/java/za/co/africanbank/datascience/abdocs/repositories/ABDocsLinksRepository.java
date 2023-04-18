package za.co.africanbank.datascience.abdocs.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.*;

@Repository
public interface ABDocsLinksRepository  extends CrudRepository <ABDocs_Links,String > {
	@Query(value="SELECT * FROM ABDocs_Links WITH (NOLOCK) WHERE UUID = ?1",nativeQuery = true)
	List<ABDocs_Links> AllLinks(@Param("UUID") String UUID);
	@Query(value = "SELECT DISTINCT FROM ABDocs_Emails WITH (NOLOCK) WHERE (IDNumber = ?1 AND Username IS NULL AND Status='Incomplete') OR (IDNumber = ?1 AND Username = ?2 AND Status='Incomplete') ORDER BY SQLCreationDate ASC", nativeQuery = true)
	List<ABDocs_Emails> distinctEmails(@Param("IDNumber") String idNumber, @Param("Username") String username);

}
