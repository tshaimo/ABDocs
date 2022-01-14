package za.co.africanbank.datascience.abdocs.controllers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import za.co.africanbank.datascience.abdocs.boltrequests.BoltRequest;
import za.co.africanbank.datascience.abdocs.constant.constant;
import za.co.africanbank.datascience.abdocs.dao.ABDocsDao;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Documents;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Emails;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Logging;
import za.co.africanbank.datascience.abdocs.entities.Email_View;
import za.co.africanbank.datascience.abdocs.jerseyclient.JerseyClient;
import za.co.africanbank.datascience.abdocs.properties.properties;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsDocumentsRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsEmailViewRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsEmailsRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsLoggingRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsUsersRepository;
import za.co.africanbank.datascience.abdocs.repositories.TotalRepository;
import za.co.africanbank.datascience.abdocs.utilities.Utility;


@Controller
public class Controllers {

	@Autowired
	ABDocsDocumentsRepository docs;
	@Autowired
	ABDocsEmailViewRepository view;
	@Autowired
	ABDocsEmailsRepository viewDistinc;
	@Autowired
	TotalRepository total;
	@Autowired
	ABDocsEmailsRepository mail;
    @Autowired
	private ABDocsUsersRepository user;
    @Autowired
	ABDocsDao dao;
    @Autowired
	ABDocsLoggingRepository logging;
   
	int count =0;



	@GetMapping("/")
	public String index(Model model,HttpServletRequest request) {
		List<Email_View> EmailList = view.AllMails(request.getRemoteUser());
		model.addAttribute("EmailList", EmailList);
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("Complete","");
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		return "pages/mailbox/mailbox";
	}

	@GetMapping("/Mailbox")
	public String Mailbox(Model model,HttpServletRequest request) {
		List<Email_View> EmailList = view.AllMails(request.getRemoteUser());
		model.addAttribute("EmailList", EmailList);
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("Complete","");
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		return "pages/mailbox/mailbox";
	}

	@GetMapping("/ReadDistinc")
	public String ReadDistinc(Model model,@RequestParam(name="IDNumber",required =false)String IDNumber,HttpServletRequest request) {
		List<ABDocs_Emails> DistincEmailList = viewDistinc.DistinctEmails(IDNumber);
		
		for(ABDocs_Emails mail : DistincEmailList ) {
			
			ABDocs_Logging log = new ABDocs_Logging();
			ABDocs_Emails eml = viewDistinc.findById(mail.getUUID()).get();
			eml.setUsername(request.getRemoteUser());
			viewDistinc.save(eml);
			log.setEmail_UUID(mail.getSystem_UUID());
			log.setUsername(request.getRemoteUser());
			log.setSQLCreationDate(new Utility().getCreationDate());
			logging.save(log);
		
		}
		
		model.addAttribute("DistincEmailList", DistincEmailList);
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		//viewDistinc.assignMails(request.getRemoteUser(), IDNumber);
		return "pages/mailbox/distinc-mail";
	}

	@PostMapping("/BoltUpload")
	public String BoltUpload(Model model,@RequestParam(name="IDNumber",required =false)String IDNumber,HttpServletRequest request) throws UniformInterfaceException, ClientHandlerException, IOException {
		String output=new JerseyClient().play(new properties(constant.PROPERTYFILE.value()).read("ClientSearch"),new BoltRequest().ClientSearch("6005055275088"));
		System.out.print(output);
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		return "redirect:/Mailbox";
	}

	@PostMapping("/NeptuneUpload")
	public String NeptuneUpload(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam(name="IDNumber",required =false)String IDNumber,@RequestParam(name="UUID",required =false)String UUID,RedirectAttributes redirAttrs) throws IOException {
		ABDocs_Emails mai;
		String path = new properties(constant.PROPERTYFILE.value()).read("NeptuneDownload") + request.getRemoteUser() + "\\";
		path = path + IDNumber;
		File file = new File(path);
		file.mkdirs();
		List<ABDocs_Documents> AllDocList = docs.AllDocument(UUID);
		System.out.println(UUID);

		for(ABDocs_Documents document : AllDocList) {
			System.out.println(document.getCategory());
			File fil=null;
			System.out.println(path +"\\"+ document.getCategory()+".pdf");
			fil = new File(path +"\\"+ document.getCategory()+".pdf");
			for (int i = 1; fil.exists(); i++) {
				fil = new File(path +"\\"+ document.getCategory()+"_"+i+".pdf");
			}
			response.setContentType("application/pdf");
		    response.setHeader("Cache-control", "private, max-age=0");
			OutputStream os = response.getOutputStream();
			try {
				os = new FileOutputStream(fil);
				String b64 = document.getAttachement();
				byte[] decoder = Base64.getDecoder().decode(b64);
				os.write(decoder);
				System.out.println("PDF File Saved");
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		mai= mail.findById(UUID).get();
		mai.setStatus("Complete");
		mail.save(mai);
		redirAttrs.addFlashAttribute("complete", "Complete");
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());

		System.out.println(path); 
		return "redirect:/Mailbox";


	}



	@GetMapping("/ABDocs")
	public String login(HttpServletRequest request, HttpSession session,Model model) {
		session.setAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		return "index";
	}

	@PostMapping("/ReadMail")
	public String ReadMail(HttpServletResponse resp,Model model,@RequestParam(name="UUID",required =false)String UUID,@RequestParam(name="DocumentType",required =false)String DocumentType,RedirectAttributes redirectAttributes,HttpServletRequest request,@RequestParam(name="Comment",required =false)String Comment,@RequestParam(name="Accept",required =false)String Accept) throws IOException {

		ABDocs_Documents DocsList;
		DocsList = docs.findDocument(UUID);
		List<ABDocs_Documents> AllDocList = docs.AllDocument(UUID);
		ABDocs_Documents docu;
		String System_UUID="";
		String File="";
		
		System.out.println("===============" + Accept);
		System.out.println("===============" + UUID);
		

		if(docs.findDocument(UUID) !=null && docs.findDocument(UUID).getSystem_UUID() !=null ) {
			System_UUID = DocsList.getSystem_UUID();
			File = DocsList.getAttachement();
		}
		if(DocumentType !=null && DocumentType !="") {
			docu = docs.findById(System_UUID).get();
			docu.setCategory(DocumentType);
			docu.setStatus(Accept);
			docs.save(docu);
		}
		
		
		if(Comment !=null && Comment !="") {
			dao.saveComments(System_UUID, Comment, request.getRemoteUser());
			
		}

		model.addAttribute("file",File);
		model.addAttribute("Count", count);
		model.addAttribute("UUID", UUID);
		model.addAttribute("Body", viewDistinc.findEmail(UUID).getCleanedBody());
		model.addAttribute("Email", viewDistinc.findEmail(UUID).getFromAddress());
		model.addAttribute("IDNumber", viewDistinc.findEmail(UUID).getIDNumber());
		model.addAttribute("Time", viewDistinc.findEmail(UUID).getSQLCreationDate());
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("AllDocList",AllDocList);
		model.addAttribute("DocsList",DocsList);
		redirectAttributes.addAttribute("UUID",UUID);
		redirectAttributes.addFlashAttribute("DocumentType",DocumentType);
		redirectAttributes.addFlashAttribute("Comment",Comment);
		redirectAttributes.addAttribute("Accept",Accept);
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		File="";
		return "redirect:/Redirect";


	}


	@PostMapping("/ReadSpecificProcessor")
	public String ReadSpecificProcessor(HttpServletResponse resp,Model model,@RequestParam(name="UUID",required =false)String UUID,@RequestParam(name="DocumentType",required =false)String DocumentType,RedirectAttributes redirectAttributes,@RequestParam(name="System_UUID",required =false)String System_ID,HttpServletRequest request,@RequestParam(name="Comment",required =false)String Comment,@RequestParam(name="Accept",required =false)String Accept) throws IOException {

		ABDocs_Documents DocsList;
		DocsList = docs.Document(System_ID);
		List<ABDocs_Documents> AllDocList = docs.AllDocument(UUID);
		ABDocs_Documents docu;
		String File="";
		
		System.out.println("===============" + Accept);

		if(docs.findDocument(UUID) !=null && docs.findDocument(UUID).getSystem_UUID() !=null ) {

			File = DocsList.getAttachement();
		}
		if(DocumentType !=null && DocumentType !="") {
			docu = docs.findById(System_ID).get();
			docu.setCategory(DocumentType);
			docu.setStatus(Accept);
			docs.save(docu);
		}
		
		
		if(Comment !=null && Comment !="") {
			dao.saveComments(System_ID, Comment, request.getRemoteUser());
			
		}

		model.addAttribute("file",File);
		model.addAttribute("Count", count);
		model.addAttribute("UUID", UUID);
		model.addAttribute("Body", viewDistinc.findEmail(UUID).getCleanedBody());
		model.addAttribute("Email", viewDistinc.findEmail(UUID).getFromAddress());
		model.addAttribute("IDNumber", viewDistinc.findEmail(UUID).getIDNumber());
		model.addAttribute("Time", viewDistinc.findEmail(UUID).getSQLCreationDate());
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("AllDocList",AllDocList);
		redirectAttributes.addAttribute("UUID",UUID);
		redirectAttributes.addFlashAttribute("DocumentType",DocumentType);
		redirectAttributes.addFlashAttribute("Comment",Comment);
		redirectAttributes.addAttribute("Accept",Accept);
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		File="";
		return "redirect:/Redirect";


	}


	@RequestMapping(value="/Redirect", method = { RequestMethod.POST, RequestMethod.GET })
	public String Redirect(HttpServletResponse resp,Model model,@RequestParam(name="UUID",required =false)String UUID,@RequestParam(name="DocumentType",required =false)String DocumentType,HttpServletRequest request,@RequestParam(name="Comment",required =false)String Comment,@RequestParam(name="Accept",required =false)String Accept) throws IOException {

		ABDocs_Documents DocsList;
		DocsList = docs.findDocument(UUID);
		List<ABDocs_Documents> AllDocList = docs.AllDocument(UUID);
		ABDocs_Documents docu;
		String System_UUID="";
		String File="";

		if(docs.findDocument(UUID) !=null && docs.findDocument(UUID).getSystem_UUID() !=null ) {
			System_UUID = DocsList.getSystem_UUID();
			File = DocsList.getAttachement();
		}

		if(DocumentType !=null && DocumentType !=""){

			docu = docs.findById(System_UUID).get();
			docu.setCategory(DocumentType);
			docu.setStatus(Accept);
			docs.save(docu); 
		}
		
		
		
		if(Comment !=null && Comment !="") {
			dao.saveComments(System_UUID, Comment, request.getRemoteUser());
			
		}

		model.addAttribute("file",File);
		model.addAttribute("Count", count);
		model.addAttribute("UUID", UUID);
		model.addAttribute("Body", viewDistinc.findEmail(UUID).getCleanedBody());
		model.addAttribute("Email", viewDistinc.findEmail(UUID).getFromAddress());
		model.addAttribute("IDNumber", viewDistinc.findEmail(UUID).getIDNumber());
		model.addAttribute("Time", viewDistinc.findEmail(UUID).getSQLCreationDate());
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("AllDocList", AllDocList);
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		File="";
		return "pages/mailbox/read-mail";


	}

	@GetMapping("/ReadSpecific")
	public String ReadSpecific(HttpServletResponse resp,Model model,@RequestParam(name="Specific",required =false)String UUID,HttpServletRequest request) throws IOException {
		System.out.println(UUID);
		ABDocs_Documents document = docs.Document(UUID);
		List<ABDocs_Documents> AllDocList = docs.AllDocument(document.getUUID());
		model.addAttribute("file",document.getAttachement());
		model.addAttribute("Status",document.getStatus());
		model.addAttribute("Count", count);
		model.addAttribute("UUID", document.getUUID());
		model.addAttribute("Body", viewDistinc.findEmail(document.getUUID()).getCleanedBody());
		model.addAttribute("Email", viewDistinc.findEmail(document.getUUID()).getFromAddress());
		model.addAttribute("IDNumber", viewDistinc.findEmail(document.getUUID()).getIDNumber());
		model.addAttribute("Time", viewDistinc.findEmail(document.getUUID()).getSQLCreationDate());
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("System_UUID",document.getSystem_UUID());
		model.addAttribute("AllDocList",AllDocList);
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		return "pages/mailbox/read-mail2";



	}

	@GetMapping("/SendMail")
	public String SendMail(Model model,HttpServletRequest request) {
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		return "pages/mailbox/compose";
	}
	
	private String getErrorMessage(HttpServletRequest request, String key) {
		Exception exception = (Exception) request.getSession().getAttribute(key);
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}
		return error;
	}




}
