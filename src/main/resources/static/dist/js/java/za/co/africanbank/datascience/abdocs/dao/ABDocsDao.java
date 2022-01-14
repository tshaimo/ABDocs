package za.co.africanbank.datascience.abdocs.dao;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.africanbank.datascience.abdocs.dto.Attachment;
import za.co.africanbank.datascience.abdocs.dto.Root;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Comments;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Documents;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Emails;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Links;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Users;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsCommentsRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsDocumentsRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsEmailsRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsLinksRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsUsersRepository;
import za.co.africanbank.datascience.abdocs.utilities.Utility;

@Component
public class ABDocsDao {

	@Autowired
	ABDocsDocumentsRepository document;
	@Autowired
	ABDocsEmailsRepository emails;
	@Autowired
	ABDocsUsersRepository users;
	@Autowired
	ABDocsLinksRepository links;
	@Autowired
	private ABDocsCommentsRepository comment;
	
	
	public void saveDocuments(Root root,UUID uuid) {
		
		ABDocs_Documents doc; 
		List<Attachment> attach = root.getAttachments_();
		
		

		for(Attachment att : attach) {
			
            doc= new ABDocs_Documents();
			doc.setIDNumber(root.getiD_number());
			doc.setAttachement(att.getRaw());
			doc.setCategory(att.getCategory());
			doc.setFileName(att.getFilename());
			doc.setUUID(uuid.toString());
			doc.setSQLCreationDate(new Utility().getCreationDate());
			doc.setStatus("Incomplete");
			document.save(doc);
		}


	}
	public void saveEmails(	Root root,UUID uuid) {

		ABDocs_Emails em = new ABDocs_Emails();
		em.setBody(root.getBody());
		em.setCleanedBody(root.getCleaned_body());
		em.setDateSent(root.getDate_sent());
		em.setFlag(root.getFlag());
		em.setFromAddress(root.getFrom_address());
		em.setIdentifier(root.getIdentifier());
		em.setIDNumber(root.getiD_number());
		em.setMimecast(String.valueOf(root.getMimecast()));
		em.setScenario(String.valueOf(root.getScenario()));
		em.setUUID(uuid.toString());
		em.setSQLCreationDate(new Utility().getCreationDate());
		em.setStatus("Incomplete");
		emails.save(em);
	}
	public void saveUsers(Root root) {
		ABDocs_Users us = new ABDocs_Users();
	}
	
	public void saveComments(String Document_UUID, String Comment,String Username) {
		
		ABDocs_Comments com = new ABDocs_Comments();
		com.setDocument_UUID(Document_UUID);
		com.setComment(Comment);
		com.setUsername(Username);
		com.setSQLCreationDate(new Utility().getCreationDate());
		comment.save(com);
		
	}
	public void saveLinks(Root root,UUID uuid) {
		
		ABDocs_Links li;
		List<String> LinksList = root.getRelease_links();
		for(String Links : LinksList) {
			
			li =  new ABDocs_Links();
			li.setIDNumber(root.getiD_number());
			li.setReleaseLinks(Links);
			li.setUUID(uuid.toString());
			li.setSQLCreationDate(new Utility().getCreationDate());
			li.setStatus("Incomplete");
			links.save(li);
		}
	}



}
