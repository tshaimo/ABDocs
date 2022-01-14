package za.co.africanbank.datascience.abdocs.controllers;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.africanbank.datascience.abdocs.entities.Email_View;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsEmailViewRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsUsersRepository;
import za.co.africanbank.datascience.abdocs.repositories.TotalRepository;

@Controller
public class ErrorsController implements ErrorController  {
@Autowired
private ABDocsUsersRepository user;
@Autowired
ABDocsEmailViewRepository view;
@Autowired
TotalRepository total;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request,Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        model.addAttribute("FullNames",user.getUserDetail(request.getRemoteUser()).getFullNames());
    	List<Email_View> EmailList = view.AllMails(request.getRemoteUser());
		model.addAttribute("EmailList", EmailList);
		model.addAttribute("Role",user.getUserDetail(request.getRemoteUser()).getRole());
		model.addAttribute("Counter",total.Count().getRowNumber());
		model.addAttribute("Complete","");
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
        
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
            	return "pages/mailbox/404";
            }
            else if(statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()){
            	return "pages/mailbox/405";
            }
            
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
            	return "pages/mailbox/500";
            }
        }
        return "error";
    }

    public String getErrorPath() {
        return null;
    }
}