package za.co.africanbank.datascience.abdocs.controllers;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import za.co.africanbank.datascience.abdocs.constant.constant;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_EmailDrafts;
import za.co.africanbank.datascience.abdocs.entities.ABDocs_MsgLookup;
import za.co.africanbank.datascience.abdocs.processes.ABDocsServiceClient;
import za.co.africanbank.datascience.abdocs.properties.properties;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsEmailDraftsRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsUsersRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocs_MsgLookupRepository;
import za.co.africanbank.datascience.abdocs.repositories.TotalRepository;
import za.co.africanbank.datascience.abdocs.utilities.Utility;
import za.co.africanbank.datascience.abdocs.xmlhandler.requiredRequest;
import za.co.africanbank.datascience.abdocs.xmlhandler.requiredResponse;

@Controller
public class EmailSendController {
	@Autowired
	ABDocs_MsgLookupRepository msgRepo;
	@Autowired
	private ABDocsUsersRepository user;
	@Autowired
	TotalRepository total;
	@Autowired 
	ABDocsEmailDraftsRepository abDocs;
	private static final Logger logger = LoggerFactory.getLogger(EmailSendController.class);
	@PostMapping("/LookUpMessage")
	public String LookUpMessage(HttpServletRequest request,@RequestParam(name="IDNumber",required =false)String IDNumber,@RequestParam(name="UUID",required =false)String UUID,Model model,@RequestParam(name="MsgType",required =false) String MsgType,@RequestParam(name="Payslip",required =false) String Payslip,@RequestParam(name="Bankstatement",required =false) String Bankstatement,@RequestParam(name="purpose",required =false) String purpose,@RequestParam(name="ID",required =false) String ID,@RequestParam(name="Email",required =false)String Email) {

		List<ABDocs_MsgLookup> msgLook = null;
		String TextBody="";
		String MSG_UUID="";
		String Subject="";


		if(MsgType !=null) {

			msgLook = msgRepo.MessageLookUp(MsgType, purpose);
			for(ABDocs_MsgLookup abMsg: msgLook ){
				TextBody=abMsg.getTextBody();
				MSG_UUID=abMsg.getMSG_UUID();
				Subject=abMsg.getSubject();
			}

		}
	

		model.addAttribute("msgLook", msgLook);
		model.addAttribute("TextBody", TextBody);
		model.addAttribute("MSG_UUID", MSG_UUID);
		model.addAttribute("Subject", Subject);
		model.addAttribute("UUID", UUID);
        model.addAttribute("IDNumber",IDNumber);
		model.addAttribute("Subject", Subject);
		model.addAttribute("Email", Email);
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		model.addAttribute("Counter",total.Count().getRowNumber());
		return "pages/mailbox/compose";



	}
	@PostMapping("/SaveMessage")
	public String  SaveMessage(RedirectAttributes redirAttrs,@RequestParam(name="IDNumber",required =false)String IDNumber,@RequestParam(name="UUID",required =false)String UUID,HttpServletRequest request,Model model,@RequestParam(name="Email2",required =false)String Email,@RequestParam(name="TextBody",required =false)String TextBody,@RequestParam(name="Subject",required =false)String Subject,@RequestParam(name="MSG_UUID",required =false)String MSG_UUID) throws JDOMException {

		ABDocs_EmailDrafts emlD = new ABDocs_EmailDrafts();
		try {
			ABDocsServiceClient client = new ABDocsServiceClient(new properties(constant.PROPERTYFILE.value()).read("EmailURL"));
			String response = new requiredResponse().playResponse((client.play(constant.SMSPREFIX.value()+new requiredRequest().smsRequest(Email, Subject,TextBody.replace(constant.LESSTHAN.value(),constant.LESSTHANHTML.value()).replace(constant.GREATERTHAN.value(), constant.GREATERTHANHTML.value()).replace(constant.SPACE.value(),""))+constant.SMSSUFFIX.value())));
			logger.info(constant.SMSPREFIX.value()+new requiredRequest().smsRequest(Email, Subject, TextBody.replace(constant.LESSTHAN.value(), constant.LESSTHANHTML.value()).replace(constant.GREATERTHAN.value(), constant.GREATERTHANHTML.value()))+constant.SMSSUFFIX.value());
			if(response.equalsIgnoreCase("Successful")) {
				redirAttrs.addFlashAttribute("sent", "Sent");
				emlD.setEmail(Email);
				emlD.setEmailto(Email);
				emlD.setEmailUUID(UUID);
				emlD.setMSG_UUID(MSG_UUID);
				emlD.setTextBody(TextBody);
				emlD.setStatus("SUCCESS");
				emlD.setSubject(Subject);
				emlD.setSQLCreationDate(new Utility().getCreationDate());
				emlD.setUsername(request.getRemoteUser());
				abDocs.save(emlD);
			}else {
				redirAttrs.addFlashAttribute("sendFail", "sendFail");
				emlD.setEmail(Email);
				emlD.setEmailto(Email);
				emlD.setMSG_UUID(MSG_UUID);
				emlD.setTextBody(TextBody);
				emlD.setStatus("FAILED");
				emlD.setSubject(Subject);
				emlD.setEmailUUID(UUID);
				emlD.setSQLCreationDate(new Utility().getCreationDate());
				emlD.setUsername(request.getRemoteUser());
				abDocs.save(emlD);
			}

			System.out.println(response);
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		redirAttrs.addAttribute("Email", Email);
		redirAttrs.addAttribute("UUID", UUID);
		redirAttrs.addAttribute("IDNumber",IDNumber);
		redirAttrs.addFlashAttribute("Email2", Email);
		
		model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
		model.addAttribute("Counter",total.Count().getRowNumber());

		return "redirect:/WrapUp";


	}


}
