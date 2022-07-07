package za.co.africanbank.datascience.abdocs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_MsgLookup;

@Repository
public interface ABDocs_MsgLookupRepository extends CrudRepository <ABDocs_MsgLookup,String> {
	@Query(value="SELECT * FROM ABDocs_MsgLookup WHERE MsgType =?1 AND purpose = ?2",nativeQuery = true)
	List<ABDocs_MsgLookup> MessageLookUp(@Param("MsgType") String MsgType,@Param("purpose") String purpose);
}
