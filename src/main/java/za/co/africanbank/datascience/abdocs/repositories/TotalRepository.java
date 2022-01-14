package za.co.africanbank.datascience.abdocs.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.Total;

@Repository
public interface TotalRepository extends CrudRepository <Total,String>{
	@Query(value="SELECT COUNT(*) AS RowNumber FROM ABDocs_Emails WHERE Status='Incomplete'",nativeQuery = true)
	Total Count();

}
