package za.co.africanbank.datascience.abdocs.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.africanbank.datascience.abdocs.entities.Count;
@Repository
public interface CountRepository extends CrudRepository <Count,String>{
	//Overall
	@Query(value="  Select count(*) AS RowCounter from [ABDocs].[dbo].[ABDocs_Emails] where Username is not null and status <> 'Complete'",nativeQuery = true)
	Count mailAttended();
	@Query(value="select count(*) AS RowCounter from [ABDocs].[dbo].[ABDocs_Emails] where Username is null and status <> 'Complete'",nativeQuery = true)
	Count unattendedTo();
	@Query(value="Select count(*) AS RowCounter from [ABDocs].[dbo].[ABDocs_Emails] where status = 'Complete'",nativeQuery = true)
	Count completed();
	@Query(value="select count(*) AS RowCounter from [ABDocs].[dbo].[ABDocs_Emails] where SQLCreationDate > CONVERT(date, getdate())",nativeQuery = true)
	Count daymails();
	@Query(value="select ISNULL( avg(datediff(minute,SQLCreationDate,CompletedDate)),0) AS RowCounter from [ABDocs].[dbo].[ABDocs_Logging] where CompletedDate is not null",nativeQuery = true)
	Count avg();
	
	//User Overall
	@Query(value="Select count(*) AS RowCounter from [ABDocs].[dbo].[ABDocs_Emails] where status = 'Complete' AND\r\n"+ 
	"Username =?1",nativeQuery = true)
	Count userComplete(@Param("Username")String Username);
	@Query(value="Select count(*) AS RowCounter from [ABDocs].[dbo].[ABDocs_Emails] where Username =?1 and status <> 'Complete'",nativeQuery = true)
	Count userAttended(@Param("Username")String Username);
	@Query(value="select ISNULL( avg(datediff(minute,SQLCreationDate,CompletedDate)),0) AS RowCounter from [ABDocs].[dbo].[ABDocs_Logging] where CompletedDate is not null and Username =?1",nativeQuery = true)
	Count userAvg(@Param("Username")String Username);
	
	
	
	
	
	
	
	
}
