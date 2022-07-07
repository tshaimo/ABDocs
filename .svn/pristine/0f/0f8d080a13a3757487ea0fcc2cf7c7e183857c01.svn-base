package za.co.africanbank.datascience.abdocs.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Documents;

@Repository
public interface ABDocsDocumentsRepository extends CrudRepository <ABDocs_Documents,String> {
	@Query(value="SELECT TOP(1)* FROM ABDocs_Documents WITH (NOLOCK) WHERE Status IN ('Incomplete','Pending') AND UUID= ?1",nativeQuery = true)
	ABDocs_Documents findDocument(@Param("UUID") String UUID);
	
	@Query(value="SELECT * FROM ABDocs_Documents WITH (NOLOCK) WHERE UUID = ?1",nativeQuery = true)
	List<ABDocs_Documents> AllDocument(@Param("UUID") String UUID);
	
	@Query(value="SELECT * FROM ABDocs_Documents WITH (NOLOCK) WHERE System_UUID = ?1",nativeQuery = true)
	ABDocs_Documents Document(@Param("System_UUID") String System_UUID);
	

}