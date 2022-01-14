package za.co.africanbank.datascience.abdocs.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Comments;

@Repository
public interface ABDocsCommentsRepository extends CrudRepository <ABDocs_Comments,String> {
 
}
