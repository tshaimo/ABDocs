package za.co.africanbank.datascience.abdocs.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.*;

@Repository
public interface ABDocsUsersRepository extends CrudRepository <ABDocs_Users,String > {
	@Query(value="SELECT * FROM ABDocs_Users WITH (NOLOCK) WHERE Username = ?1",nativeQuery = true)
	ABDocs_Users getUserDetail(@Param("Username")String Username);

	
	@Query(value="SELECT * FROM ABDocs_Users WITH (NOLOCK)",nativeQuery = true)
	List<ABDocs_Users> getAll();
	
	@Query(value="SELECT * FROM ABDocs_Users WITH (NOLOCK) WHERE FullNames = ?1",nativeQuery = true)
	ABDocs_Users getUsername(@Param("FullNames")String FullNames);
}
