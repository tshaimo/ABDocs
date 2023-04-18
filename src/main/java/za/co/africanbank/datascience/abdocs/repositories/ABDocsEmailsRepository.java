/*
 * package za.co.africanbank.datascience.abdocs.repositories; import
 * java.util.List; import org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.CrudRepository; import
 * org.springframework.data.repository.query.Param; import
 * org.springframework.stereotype.Repository; import
 * za.co.africanbank.datascience.abdocs.entities.ABDocs_Emails;
 * 
 * @Repository public interface ABDocsEmailsRepository extends CrudRepository
 * <ABDocs_Emails ,String >{
 * 
 * @Query(
 * value="SELECT * FROM ABDocs_Emails WITH (NOLOCK) WHERE (IDNumber  = ?1 AND Username IS NULL AND Status='Incomplete')\r\n"
 * +
 * "OR (IDNumber  =?1 AND Username=?2 AND Status='Incomplete') ORDER BY SQLCreationDate ASC"
 * ,nativeQuery = true) List<ABDocs_Emails> DistinctEmails(@Param("IDNumber")
 * String IDNumber,@Param("Username") String Username);
 * 
 * @Query(
 * value="SELECT * FROM ABDocs_Emails WITH (NOLOCK) WHERE Status='Incomplete' AND IDNumber  = ?1"
 * ,nativeQuery = true) List<ABDocs_Emails> CheckAssigned(@Param("IDNumber")
 * String IDNumber);
 * 
 * @Query(
 * value="SELECT * FROM ABDocs_Emails WITH (NOLOCK) WHERE Status='Incomplete' AND UUID  = ?1"
 * ,nativeQuery = true) ABDocs_Emails findEmail(@Param("UUID") String UUID);
 * 
 * @Query(
 * value="SELECT TOP(1)* FROM ABDocs_Emails WITH (NOLOCK) WHERE System_UUID  = ?1"
 * ,nativeQuery = true) ABDocs_Emails findEmailLog(@Param("System_UUID") String
 * System_UUID);
 * 
 * @Query(value="UPDATE ABDocs_Emails SET Username=?1 WHERE IDNumber=?2 "
 * ,nativeQuery = true) void assignMails(@Param("Username") String
 * Username,@Param("IDNumber") String IDNumber);
 * 
 * }
 */
package za.co.africanbank.datascience.abdocs.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Emails;

@Repository
public interface ABDocsEmailsRepository extends CrudRepository<ABDocs_Emails, String> {

    @Query(value = "SELECT * FROM ABDocs_Emails WITH (NOLOCK) WHERE (IDNumber  = ?1 AND Username IS NULL AND Status='Incomplete') OR (IDNumber  =?1 AND Username=?2 AND Status='Incomplete') ORDER BY SQLCreationDate ASC", nativeQuery = true)
    List<ABDocs_Emails> DistinctEmails(@Param("IDNumber") String idNumber, @Param("Username") String username);

    @Query(value = "SELECT * FROM ABDocs_Emails WITH (NOLOCK) WHERE Status='Incomplete' AND IDNumber  = ?1", nativeQuery = true)
    List<ABDocs_Emails> CheckAssigned(@Param("IDNumber") String idNumber);
    
    @Query(value = "SELECT * FROM ABDocs_Emails WITH (NOLOCK) "
    		+ "WHERE Status='Incomplete' "
    		+ "and IDNumber = (SELECT top 1 IDNumber "
    							+ "FROM ABDocs_Emails WITH (NOLOCK) "
    							+ "WHERE Status='Incomplete' order by DateSent desc)", nativeQuery = true)
    List<ABDocs_Emails> AssignedFIFO();

    @Query(value = "SELECT * FROM ABDocs_Emails WITH (NOLOCK) WHERE Status='Incomplete' AND UUID  = ?1", nativeQuery = true)
    ABDocs_Emails findEmail(@Param("UUID") String uuid);

    @Query(value = "SELECT TOP(1) * FROM ABDocs_Emails WITH (NOLOCK) WHERE System_UUID  = ?1", nativeQuery = true)
    ABDocs_Emails findEmailLog(@Param("System_UUID") String systemUuid);

    @Query(value = "UPDATE ABDocs_Emails SET Username=?1 WHERE IDNumber=?2 ", nativeQuery = true)
    void assignMails(@Param("Username") String username, @Param("IDNumber") String idNumber);

    @Query(value = "SELECT DISTINCT * FROM ABDocs_Emails WITH (NOLOCK) WHERE (IDNumber = ?1 AND Username IS NULL AND Status='Incomplete') OR (IDNumber = ?1 AND Username = ?2 AND Status='Incomplete') ORDER BY SQLCreationDate ASC", nativeQuery = true)
    List<ABDocs_Emails> distinctEmails(@Param("IDNumber") String idNumber, @Param("Username") String username);

    
    @Query(value = "SELECT * FROM ABDocs_Emails WITH (NOLOCK) WHERE IDNumber = ?1 AND FromAddress = ?2 AND Username = ?3", nativeQuery = true)
    ABDocs_Emails findByIDNumberAndFromAddressAndUsername(@Param("IDNumber") String idNumber, @Param("FromAddress") String fromAddress, @Param("Username") String username);
}
