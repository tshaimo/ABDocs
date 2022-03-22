package za.co.africanbank.datascience.abdocs.controllers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Comments;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Documents;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Emails;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Links;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Logging;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_Users;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_UsersActivity;
import za.co.africanbank.datascience.abdocs.entities.Email_View;
import za.co.africanbank.datascience.abdocs.jerseyclient.JerseyClient;
import za.co.africanbank.datascience.abdocs.properties.properties;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsCommentsRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsDocumentsRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsEmailViewRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsEmailsRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsLinksRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsLoggingRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsUsersActivityRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsUsersRepository;
import za.co.africanbank.datascience.abdocs.repositories.CountRepository;
import za.co.africanbank.datascience.abdocs.repositories.TotalRepository;
import za.co.africanbank.datascience.abdocs.utilities.Utility;
import org.springframework.beans.factory.annotation.Value;


@Controller
public class Controllers {
	@Autowired
	CountRepository countr;
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
	@Autowired
	ABDocsUsersActivityRepository activity;
	@Autowired
	ABDocsLinksRepository links;
	@Autowired
	ABDocsCommentsRepository comments;
	private Set<String> sessionIds = Collections.synchronizedSet(new HashSet<>());
	int count =0;
	@Value("${neptune.location}")
    private String neptune;



	@GetMapping("/")
	public String index(Model model,HttpSession session,HttpServletRequest request) {
		this.sessionIds.add(session.getId());
		List<Email_View> EmailList = view.AllMails(request.getRemoteUser());
		model.addAttribute("EmailList", EmailList);
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("Complete","");
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		return "pages/mailbox/mailbox";
	}

	@GetMapping("/Mailbox")
	public String Mailbox(Model model,HttpServletRequest request) {

		List<Email_View> EmailList = view.AllMails(request.getRemoteUser());
		model.addAttribute("EmailList", EmailList);
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("Complete","");
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		return "pages/mailbox/mailbox";
	}

	@GetMapping("/ReadDistinc")
	public String ReadDistinc(RedirectAttributes redirAttrs,Model model,@RequestParam(name="IDNumber",required =false)String IDNumber,HttpServletRequest request,@RequestParam(name="System_uuid",required =false) String System_uuid) {

		List<ABDocs_Emails> DistincEmailList = viewDistinc.DistinctEmails(IDNumber,request.getRemoteUser());
		List<ABDocs_Emails> CheckAssigned = viewDistinc.CheckAssigned(IDNumber);

		if(DistincEmailList.isEmpty()) {
			for(ABDocs_Emails mail : CheckAssigned) {	
				ABDocs_Emails eml = viewDistinc.findById(mail.getUUID()).get();
				if(eml.getUsername()!=null && !eml.getUsername().equalsIgnoreCase(request.getRemoteUser())) {
					redirAttrs.addFlashAttribute("Userbusy",user.getUserDetail(eml.getUsername()).getFullNames());
					redirAttrs.addFlashAttribute("Assigned","Assigned");
					return "redirect:/Mailbox";
				}
			}
		}

		for(ABDocs_Emails mail : DistincEmailList ){
			ABDocs_Emails eml = viewDistinc.findById(mail.getUUID()).get();
			if(eml.getUsername()==null || eml.getUsername()=="") {

				eml.setUsername(request.getRemoteUser());
				viewDistinc.save(eml);
			}

		}

		model.addAttribute("DistincEmailList", DistincEmailList);
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		//viewDistinc.assignMails(request.getRemoteUser(), IDNumber);
		return "pages/mailbox/distinc-mail";
	}

	@PostMapping("/BoltUpload")
	public String BoltUpload(Model model,@RequestParam(name="IDNumber",required =false)String IDNumber,HttpServletRequest request) throws UniformInterfaceException, ClientHandlerException, IOException {
		String output=new JerseyClient().play(new properties(constant.PROPERTYFILE.value()).read("ClientSearch"),new BoltRequest().ClientSearch("6005055275088"));
		System.out.print(output);
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		return "redirect:/Mailbox";
	}


	@GetMapping("/WrapUp")
	public String WrapUp(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam(name="IDNumber",required =false)String IDNumber,@RequestParam(name="UUID",required =false)String UUID,RedirectAttributes redirAttrs,@RequestParam(name="Wrapcode",required =false)String Wrapcode,@RequestParam(name="Email",required =false)String Email,@RequestParam(name="Reply",required =false)String Reply) throws InterruptedException {


		if(Reply !=null && Reply !="") {
			model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
			model.addAttribute("Counter",total.Count().getRowNumber());
			model.addAttribute("Email", Email);
			model.addAttribute("UUID",UUID);
			model.addAttribute("IDNumber",IDNumber);
			return "pages/mailbox/compose";
		}



		List<ABDocs_Emails> DistincEmailList = viewDistinc.DistinctEmails(IDNumber,request.getRemoteUser());
		ABDocs_Emails mai;
		List<ABDocs_Documents> AllDocList = docs.AllDocument(UUID);
		List<ABDocs_Logging> log = logging.findByEmail_UUID(UUID);
		mai= mail.findById(UUID).get();


		if(Wrapcode !=null && Wrapcode !=""){

			for(ABDocs_Logging logg : log) {
				logg.setCompletedDate(new Utility().getCreationDate());
				logging.save(logg);
			}

			mai.setStatus("Complete");
			mai.setWrapUpCode(Wrapcode);
			mai.setUsername(request.getRemoteUser());
			mail.save(mai);

			DistincEmailList = viewDistinc.DistinctEmails(IDNumber,request.getRemoteUser());

			if(!DistincEmailList.isEmpty()) {

				model.addAttribute("complete", "Complete");
				model.addAttribute("DistincEmailList", DistincEmailList);
				model.addAttribute("Counter",total.Count().getRowNumber());
				model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
				model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
				return "pages/mailbox/distinc-mail";
			}
			if(DistincEmailList.isEmpty()){

				redirAttrs.addFlashAttribute("complete", "Complete");
				redirAttrs.addFlashAttribute("IDNumber", IDNumber);
				redirAttrs.addFlashAttribute("Email",mai.getFromAddress());
				return "redirect:/Mailbox";

			}
		}


		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("UUID", UUID);
		model.addAttribute("Body", viewDistinc.findEmail(UUID).getBody());
		model.addAttribute("Email", viewDistinc.findEmail(UUID).getFromAddress());
		model.addAttribute("IDNumber", viewDistinc.findEmail(UUID).getIDNumber());
		model.addAttribute("Time", viewDistinc.findEmail(UUID).getSQLCreationDate());
		model.addAttribute("Counter",total.Count().getRowNumber());

		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		model.addAttribute("AllDocList",AllDocList);
		return "pages/mailbox/read-mail3";

	}

	@PostMapping("/NeptuneUpload")
	public String NeptuneUpload(Model model,HttpServletRequest request,HttpServletResponse response,@RequestParam(name="IDNumber",required =false)String IDNumber,@RequestParam(name="UUID",required =false)String UUID,RedirectAttributes redirAttrs,@RequestParam(name="Wrapcode",required =false)String Wrapcode) throws IOException {
		//ABDocs_Emails mai;
		String path = neptune;
		// + request.getRemoteUser() + "/";
		//String path = new properties(constant.PROPERTYFILE.value()).read("NeptuneDownload") + request.getRemoteUser() + "\\";
		path = path;
		// + IDNumber;
		File file = new File(path);
		file.mkdirs();
		List<ABDocs_Logging> log = logging.findByEmail_UUID(UUID);
		List<ABDocs_Documents> AllDocList = docs.AllDocument(UUID);
		for(ABDocs_Documents document : AllDocList) {
			System.out.println(document.getCategory());
			File fil=null;
			System.out.println(path +"/"+ document.getCategory()+".pdf");
			fil = new File(path +"/"+ document.getCategory()+".pdf");
			for (int i = 1; fil.exists(); i++) {
				fil = new File(path +"/"+ document.getCategory()+"_"+i+".pdf");
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

		for(ABDocs_Logging logg : log) {
			logg.setDownloadDate(new Utility().getCreationDate());
			logging.save(logg);
		}

		/*mai= mail.findById(UUID).get();
		List<ABDocs_Logging> log = logging.findByEmail_UUID(UUID);

		for(ABDocs_Logging logg : log) {
			logg.setCompletedDate(new Utility().getCreationDate());
			logging.save(logg);
		}
		//mai.setStatus("Complete");
		//mai.setWrapUpCode(Wrapcode);
		//mail.save(mai); */

		redirAttrs.addFlashAttribute("complete", "Complete");
		redirAttrs.addAttribute("UUID", UUID);
		redirAttrs.addAttribute("IDNumber", IDNumber);
		//redirAttrs.addFlashAttribute("Email",mai.getFromAddress());
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		redirAttrs.addFlashAttribute("path",path.replace("P:", "\\\\Neptune\\directdoc$"));
		return "redirect:/WrapUp";


	}

	@GetMapping("/ABDocs")
	public String login(HttpServletRequest request, HttpSession session,Model model,@RequestParam(name="logout",required =false)String logout,@RequestParam(name="Login",required =false)String Login) {
		session.setAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		//model.addAttribute("Username", request.getRemoteUser());
		model.addAttribute("Login", Login);


		if(null != logout) {

			ABDocs_UsersActivity act = new ABDocs_UsersActivity();
			this.sessionIds.remove(session.getId());
			//act.setUsername(model.getAttribute("Username").toString());
			act.setLogout_Time(new Utility().getCreationDate());
			activity.save(act);

		}

		return "index";
	}



	@GetMapping("/SignOut")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}

	@PostMapping("/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		return "redirect:/ABDocs?logout";
	}

	@PostMapping("/Reply")
	public String Reply(Model model,@RequestParam(name="Email",required =false)String Email,@RequestParam(name="Reply",required =false)String Reply,HttpServletRequest request) {
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("Email", Email);
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		return "pages/mailbox/compose";
	}

	@PostMapping("/Reassign")
	public String Reassign(Model model,HttpServletRequest request,@RequestParam(name="IDNumber",required =false)String IDNumber,@RequestParam(name="UUID",required =false)String UUID,RedirectAttributes redirectAttributes) {

		ABDocs_Emails mai;
		List<ABDocs_Emails> abList = mail.DistinctEmails(IDNumber,request.getRemoteUser());

		for(ABDocs_Emails list : abList) {

			mai=mail.findById(list.getUUID()).get();
			mai.setUsername(null);
			mail.save(mai);
		}
		redirectAttributes.addFlashAttribute("MailAssigned", "MailAssigned");
		redirectAttributes.addFlashAttribute("IDNumber", IDNumber);
		return "redirect:/Mailbox";

	}

	@PostMapping("/ReadMail")
	public String ReadMail(@RequestParam(name="commentType",required =false)String commentType,@RequestParam(name="ID",required =false)String ID,HttpServletResponse resp,Model model,@RequestParam(name="UUID",required =false)String UUID,@RequestParam(name="DocumentType",required =false)String DocumentType,RedirectAttributes redirectAttributes,HttpServletRequest request,@RequestParam(name="Comment",required =false)String Comment,@RequestParam(name="Accept",required =false)String Accept,@RequestParam(name="Email",required =false)String Email,@RequestParam(name="Reply",required =false)String Reply) throws IOException {
		ABDocs_Logging log = new ABDocs_Logging();
		if(Reply !=null && Reply !="") {

			model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
			model.addAttribute("Counter",total.Count().getRowNumber());
			model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
			model.addAttribute("Email", Email);
			return "pages/mailbox/compose";
		}


		ABDocs_Emails mai;
		ABDocs_Documents DocsList;
		DocsList = docs.findDocument(UUID);
		List<ABDocs_Documents> AllDocList = docs.AllDocument(UUID);

		if(AllDocList.isEmpty()) {
			
			log.setEmail_UUID(UUID);
			log.setUsername(request.getRemoteUser());
			log.setSQLCreationDate(new Utility().getCreationDate());
			logging.save(log);

			redirectAttributes.addAttribute("UUID",UUID);
			redirectAttributes.addAttribute("IDNumber", viewDistinc.findEmail(UUID).getIDNumber());
			return "redirect:/WrapUp";

		}
		List<ABDocs_Links> allLinks = links.AllLinks(UUID);
		ABDocs_Documents docu;
		String System_UUID="";
		String File="";
		String Category="";


		

		log.setEmail_UUID(UUID);
		log.setUsername(request.getRemoteUser());
		log.setSQLCreationDate(new Utility().getCreationDate());
		logging.save(log);


		if(docs.findDocument(UUID) !=null && docs.findDocument(UUID).getSystem_UUID() !=null ) {
			System_UUID = DocsList.getSystem_UUID();
			File = DocsList.getAttachement();
			Category=DocsList.getSystem_UUID();
		}
		if(DocumentType !=null && DocumentType !="") {
			docu = docs.findById(System_UUID).get();
			docu.setCategory(DocumentType);
			docu.setStatus(Accept);
			docs.save(docu);
		}


		List<ABDocs_Comments> commentList = comments.findComments(System_UUID);
		List<ABDocs_Comments> emailComments = comments.findEmailComments(viewDistinc.findEmail(UUID).getIDNumber());

		if(Comment !=null && Comment !="" && commentType !=null && commentType.equalsIgnoreCase("Document")){
			dao.saveDocumentsComments(System_UUID, Comment, request.getRemoteUser(),user.getUserDetail(request.getRemoteUser()).getFullNames(),commentType);
		}
		if(Comment !=null && Comment !="" && commentType !=null && commentType.equalsIgnoreCase("Email")){

			dao.saveEmailComments(viewDistinc.findEmail(UUID).getIDNumber() , Comment, request.getRemoteUser(),user.getUserDetail(request.getRemoteUser()).getFullNames(),commentType);
		}

		if(ID !=null && ID !="") {

			mai= mail.findById(UUID).get();
			mai.setIDNumber(ID);
			mail.save(mai);

			for(ABDocs_Documents dc : AllDocList) {

				dc.setIDNumber(ID);
				docs.save(dc);
			}
			for(ABDocs_Links lin : allLinks) {

				lin.setIDNumber(ID);
				links.save(lin);

			}
		}

		if(isNumeric(viewDistinc.findEmail(UUID).getIDNumber())) { 

		} else {
			redirectAttributes.addFlashAttribute("NonNumeric","NonNumeric");
		}

		model.addAttribute("file",File);
		model.addAttribute("Category",Category);
		model.addAttribute("Count", count);
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("UUID", UUID);
		model.addAttribute("Body", viewDistinc.findEmail(UUID).getBody());
		model.addAttribute("Email", viewDistinc.findEmail(UUID).getFromAddress());
		model.addAttribute("IDNumber", viewDistinc.findEmail(UUID).getIDNumber());
		model.addAttribute("Time", viewDistinc.findEmail(UUID).getSQLCreationDate());
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("AllDocList",AllDocList);
		model.addAttribute("commentList",commentList);
		model.addAttribute("emailComments",emailComments);
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
	public String ReadSpecificProcessor(@RequestParam(name="commentType",required =false)String commentType,@RequestParam(name="ID",required =false)String ID,HttpServletResponse resp,Model model,@RequestParam(name="UUID",required =false)String UUID,@RequestParam(name="DocumentType",required =false)String DocumentType,RedirectAttributes redirectAttributes,@RequestParam(name="System_UUID",required =false)String System_ID,HttpServletRequest request,@RequestParam(name="Comment",required =false)String Comment,@RequestParam(name="Accept",required =false)String Accept,@RequestParam(name="Email",required =false)String Email,@RequestParam(name="Reply",required =false)String Reply) throws IOException {

		if(Reply !=null && Reply !="") {
			model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
			model.addAttribute("Counter",total.Count().getRowNumber());
			model.addAttribute("Email", Email);
			return "pages/mailbox/compose";
		}
		ABDocs_Emails mai;
		ABDocs_Documents DocsList;
		DocsList = docs.Document(System_ID);
		List<ABDocs_Links> allLinks = links.AllLinks(UUID);
		List<ABDocs_Documents> AllDocList = docs.AllDocument(UUID);
		ABDocs_Documents docu;
		String File="";
		String Category="";



		if(docs.findDocument(UUID) !=null && docs.findDocument(UUID).getSystem_UUID() !=null ) {

			File = DocsList.getAttachement();
			Category=DocsList.getSystem_UUID();
		}
		if(DocumentType !=null && DocumentType !="") {
			docu = docs.findById(System_ID).get();
			docu.setCategory(DocumentType);
			docu.setStatus(Accept);
			docs.save(docu);
		}

		List<ABDocs_Comments> commentList = comments.findComments(System_ID);
		List<ABDocs_Comments> emailComments = comments.findEmailComments(viewDistinc.findEmail(UUID).getIDNumber());

		if(Comment !=null && Comment !="" && commentType !=null && commentType.equalsIgnoreCase("Document")){
			dao.saveDocumentsComments(System_ID, Comment, request.getRemoteUser(),user.getUserDetail(request.getRemoteUser()).getFullNames(),commentType);
		}
		if(Comment !=null && Comment !="" &&  commentType !=null && commentType.equalsIgnoreCase("Email")){

			dao.saveEmailComments(viewDistinc.findEmail(UUID).getIDNumber() , Comment, request.getRemoteUser(),user.getUserDetail(request.getRemoteUser()).getFullNames(),commentType);
		}

		if(ID !=null && ID !="") {

			mai= mail.findById(UUID).get();
			mai.setIDNumber(ID);
			mail.save(mai);

			for(ABDocs_Documents dc : AllDocList) {
				dc.setIDNumber(ID);
				docs.save(dc);
			}
			for(ABDocs_Links lin : allLinks) {

				lin.setIDNumber(ID);
				links.save(lin);

			}
		}

		if(isNumeric(viewDistinc.findEmail(UUID).getIDNumber())) { 

		} else {
			redirectAttributes.addFlashAttribute("NonNumeric","NonNumeric");
		}

		model.addAttribute("file",File);
		model.addAttribute("Category",Category);
		model.addAttribute("Count", count);
		model.addAttribute("UUID", UUID);
		model.addAttribute("Body", viewDistinc.findEmail(UUID).getBody());
		model.addAttribute("Email", viewDistinc.findEmail(UUID).getFromAddress());
		model.addAttribute("IDNumber", viewDistinc.findEmail(UUID).getIDNumber());
		model.addAttribute("Time", viewDistinc.findEmail(UUID).getSQLCreationDate());
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("AllDocList",AllDocList);
		model.addAttribute("commentList",commentList);
		model.addAttribute("emailComments",emailComments);
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		redirectAttributes.addAttribute("UUID",UUID);
		redirectAttributes.addFlashAttribute("DocumentType",DocumentType);
		redirectAttributes.addFlashAttribute("Comment",Comment);
		redirectAttributes.addAttribute("Accept",Accept);
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		File="";
		return "redirect:/Redirect";


	}


	@RequestMapping(value="/Redirect", method = { RequestMethod.POST, RequestMethod.GET })
	public String Redirect(@RequestParam(name="commentType",required =false)String commentType,@RequestParam(name="ID",required =false)String ID,RedirectAttributes redirectAttributes,HttpServletResponse resp,Model model,@RequestParam(name="UUID",required =false)String UUID,@RequestParam(name="DocumentType",required =false)String DocumentType,HttpServletRequest request,@RequestParam(name="Comment",required =false)String Comment,@RequestParam(name="Accept",required =false)String Accept) throws IOException {

		ABDocs_Documents DocsList;
		ABDocs_Emails mai;

		DocsList = docs.findDocument(UUID);
		List<ABDocs_Links> allLinks = links.AllLinks(UUID);
		List<ABDocs_Documents> AllDocList = docs.AllDocument(UUID);
		ABDocs_Documents docu;
		String System_UUID="";
		String File="";
		String Category="";

		if(docs.findDocument(UUID) !=null && docs.findDocument(UUID).getSystem_UUID() !=null ) {
			System_UUID = DocsList.getSystem_UUID();
			File = DocsList.getAttachement();
			Category=DocsList.getSystem_UUID();

		}

		if(DocumentType !=null && DocumentType !=""){

			docu = docs.findById(System_UUID).get();
			docu.setCategory(DocumentType);
			docu.setStatus(Accept);
			docs.save(docu); 
		}


		List<ABDocs_Comments> commentList = comments.findComments(System_UUID);
		List<ABDocs_Comments> emailComments = comments.findEmailComments(viewDistinc.findEmail(UUID).getIDNumber());

		if(Comment !=null && Comment !="" && commentType !=null && commentType.equalsIgnoreCase("Document")){
			dao.saveDocumentsComments(System_UUID, Comment, request.getRemoteUser(),user.getUserDetail(request.getRemoteUser()).getFullNames(),commentType);
		}
		if(Comment !=null && Comment !="" && commentType !=null && commentType.equalsIgnoreCase("Email")){

			dao.saveEmailComments(viewDistinc.findEmail(UUID).getIDNumber() , Comment, request.getRemoteUser(),user.getUserDetail(request.getRemoteUser()).getFullNames(),commentType);
		}

		if(isNumeric(viewDistinc.findEmail(UUID).getIDNumber())) { 

		} else {
			redirectAttributes.addFlashAttribute("NonNumeric","NonNumeric");
		}

		if(ID !=null && ID !="") {

			mai= mail.findById(UUID).get();
			mai.setIDNumber(ID);
			mail.save(mai);

			for(ABDocs_Documents dc : AllDocList) {
				dc.setIDNumber(ID);
				docs.save(dc);
			}

			for(ABDocs_Links lin : allLinks) {

				lin.setIDNumber(ID);
				links.save(lin);

			}

		}

		model.addAttribute("file",File);
		model.addAttribute("Category",Category);
		model.addAttribute("Count", count);
		model.addAttribute("UUID", UUID);
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("Body", viewDistinc.findEmail(UUID).getBody());
		model.addAttribute("Email", viewDistinc.findEmail(UUID).getFromAddress());
		model.addAttribute("IDNumber", viewDistinc.findEmail(UUID).getIDNumber());
		model.addAttribute("Time", viewDistinc.findEmail(UUID).getSQLCreationDate());
		model.addAttribute("commentList",commentList);
		model.addAttribute("emailComments",emailComments);
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("AllDocList", AllDocList);
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		File="";
		return "pages/mailbox/read-mail";
	}

	@GetMapping("/ReadSpecific")
	public String ReadSpecific(RedirectAttributes redirectAttributes,HttpServletResponse resp,Model model,@RequestParam(name="Specific",required =false)String UUID,HttpServletRequest request) throws IOException {

		ABDocs_Documents document = docs.Document(UUID);
		List<ABDocs_Documents> AllDocList = docs.AllDocument(document.getUUID());
		List<ABDocs_Comments> commentList = comments.findComments(document.getSystem_UUID());
		List<ABDocs_Comments> emailComments = comments.findEmailComments(viewDistinc.findEmail(document.getUUID()).getIDNumber());

		if(isNumeric(docs.Document(UUID).getIDNumber())){ 

		} else {
			model.addAttribute("NonNumeric","NonNumeric");
		}
		model.addAttribute("file",document.getAttachement());
		model.addAttribute("Category",document.getSystem_UUID());
		model.addAttribute("Status",document.getStatus());
		model.addAttribute("Count", count);
		model.addAttribute("UUID", document.getUUID());
		model.addAttribute("Body", viewDistinc.findEmail(document.getUUID()).getBody());
		model.addAttribute("Email", viewDistinc.findEmail(document.getUUID()).getFromAddress());
		model.addAttribute("IDNumber", viewDistinc.findEmail(document.getUUID()).getIDNumber());
		model.addAttribute("Time", viewDistinc.findEmail(document.getUUID()).getSQLCreationDate());
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("System_UUID",document.getSystem_UUID());
		model.addAttribute("commentList",commentList);
		model.addAttribute("emailComments",emailComments);
		model.addAttribute("AllDocList",AllDocList);
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		return "pages/mailbox/read-mail2";
	}

	@GetMapping("/SendMail")
	public String SendMail(Model model,HttpServletRequest request,@RequestParam(name="Email",required =false)String Email) {

		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("TextBody","");
		model.addAttribute("Email","Email");
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		return "pages/mailbox/compose";
	}

	@GetMapping("/Dashboard")
	public String Dashboard(@RequestParam(name="User",required =false)String User,Model model,HttpServletRequest request,@RequestParam(name="options",required =false)String options) {
		boolean clicked = false;
		List<ABDocs_Users> userList = user.getAll();

		//Daily admin no user
		if(options !=null && options.equalsIgnoreCase("Daily") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("Admin")){
			clicked=true;
			model.addAttribute("Daily", options);
		}
		//Daily admin user provided
		if(options !=null && options.equalsIgnoreCase("Daily") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("Admin") && User !=null){
			clicked=true;
			model.addAttribute("Daily", options);
			model.addAttribute("User", User);
		}

		//Daily General
		if(options !=null && options.equalsIgnoreCase("Daily") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("General")){
			clicked=true;
			model.addAttribute("Daily", options);
		}

		//Weekly general
		if(options !=null && options.equalsIgnoreCase("Weekly") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("General")){
			clicked=true;
			model.addAttribute("Weekly", options);

		}

		//Weekly Admin no user
		if(options !=null && options.equalsIgnoreCase("Weekly") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("Admin"))
		{
			clicked=true;
			model.addAttribute("Weekly", options);

		}

		//Weekly admin user provided
		if(options !=null && options.equalsIgnoreCase("Weekly") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("Admin") && User !=null)
		{
			clicked=true;
			model.addAttribute("Weekly", options);
			model.addAttribute("User", User);

		}

		//Admin Monthly user provided
		if(options !=null && options.equalsIgnoreCase("Monthly") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("Admin") && User !=null){
			clicked=true;
			model.addAttribute("Monthly", options);
			model.addAttribute("User", User);
		}

		//Monthly Admin no user
		if(options !=null && options.equalsIgnoreCase("Monthly") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("Admin")){
			clicked=true;
			model.addAttribute("Monthly", options);
		}

		//Monthly General
		if(options !=null && options.equalsIgnoreCase("Monthly") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("General")){
			clicked=true;
			model.addAttribute("Monthly", options);
		}




		//Overall General
		if(options !=null && options.equalsIgnoreCase("Overall") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("General")){
			clicked=true;
			model.addAttribute("Unattended",countr.unattendedTo().getRowCounter());
			model.addAttribute("Avg",countr.userAvg(request.getRemoteUser()).getRowCounter());
			model.addAttribute("CompletedMail",countr.userComplete(request.getRemoteUser()).getRowCounter());
			model.addAttribute("Attended",countr.userAttended(request.getRemoteUser()).getRowCounter());
			model.addAttribute("Overall", "Overall");
		}

		//Overall admin user provided data load
		if(options !=null && options.equalsIgnoreCase("Overall") && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("Admin") && User !=null ){
			clicked=true;
			model.addAttribute("Unattended",countr.unattendedTo().getRowCounter());
			model.addAttribute("Avg",countr.userAvg(user.getUsername(User).getUsername()).getRowCounter());
			model.addAttribute("CompletedMail",countr.userComplete(user.getUsername(User).getUsername()).getRowCounter());
			model.addAttribute("Attended",countr.userAttended(user.getUsername(User).getUsername()).getRowCounter());
			model.addAttribute("Overall",options);
			model.addAttribute("User", User);
		}
		//General not clicked load function
		if(!clicked && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("General")){
			model.addAttribute("Unattended",countr.unattendedTo().getRowCounter());
			model.addAttribute("Avg",countr.userAvg(request.getRemoteUser()).getRowCounter());
			model.addAttribute("CompletedMail",countr.userComplete(request.getRemoteUser()).getRowCounter());
			model.addAttribute("Attended",countr.userAttended(request.getRemoteUser()).getRowCounter());
			model.addAttribute("Overall", "Overall");
		}
		//Admin not clicked load function
		if(!clicked && user.getUserDetail(request.getRemoteUser()).getRole().equalsIgnoreCase("Admin")){

			model.addAttribute("sessionCount", this.sessionIds.size());
			model.addAttribute("Unattended",countr.unattendedTo().getRowCounter());
			model.addAttribute("Avg",countr.avg().getRowCounter());
			model.addAttribute("Daymails",countr.daymails().getRowCounter());
			model.addAttribute("CompletedMail",countr.completed().getRowCounter());
			model.addAttribute("Attended",countr.mailAttended().getRowCounter());
			model.addAttribute("Live", "Live");
		}

		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("userList", userList);
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		return "pages/mailbox/dashboard";
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

	public static boolean isNumeric(String string) {
		double value;
		if(string == null || string.equals("")) {
			return false;
		}
		try {
			value = Double.parseDouble(string);
			return true;
		} catch (NumberFormatException e){
		}
		return false;
	}




}
