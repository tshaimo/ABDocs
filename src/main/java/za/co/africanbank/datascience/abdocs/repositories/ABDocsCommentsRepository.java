package za.co.africanbank.datascience.abdocs.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Comments;

@Repository
public interface ABDocsCommentsRepository extends CrudRepository <ABDocs_Comments,String> {
	@Query(value="SELECT * FROM ABDocs_Comments WITH (NOLOCK) WHERE Document_UUID = ?1",nativeQuery = true)
	List<ABDocs_Comments> findComments(@Param("Document_UUID")String Document_UUID);
	
	@Query(value="SELECT * FROM ABDocs_Comments WITH (NOLOCK) WHERE IDNumber = ?1",nativeQuery = true)
	List<ABDocs_Comments> findEmailComments(@Param("IDNumber")String IDNumber);
 
}
