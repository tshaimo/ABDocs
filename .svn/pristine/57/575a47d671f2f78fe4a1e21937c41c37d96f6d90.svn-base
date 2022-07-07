package za.co.africanbank.datascience.abdocs.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.*;

@Repository
public interface ABDocsLoggingRepository extends CrudRepository <ABDocs_Logging, String> {
	
@Query(value="SELECT * FROM ABDocs_Logging WITH (NOLOCK) WHERE Email_UUID = ?1",nativeQuery = true)
List<ABDocs_Logging> findByEmail_UUID(@Param("Email_UUID")String Email_UUID);

@Query(value="select * from [ABDocs].[dbo].[ABDocs_Logging] where SQLCreationDate <dateadd(minute, -60,GETDATE()) and CompletedDate IS NULL AND Reassigned IS NULL",nativeQuery=true)
List<ABDocs_Logging> inComplete();

}
