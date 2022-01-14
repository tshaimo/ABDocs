package za.co.africanbank.datascience.abdocs.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_EmailDrafts;

@Repository
public interface ABDocsEmailDraftsRepository extends CrudRepository <ABDocs_EmailDrafts,String> {

}
