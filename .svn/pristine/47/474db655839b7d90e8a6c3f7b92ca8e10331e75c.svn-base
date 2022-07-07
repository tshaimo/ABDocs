package za.co.africanbank.datascience.abdocs.controllers;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController  {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
        
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
            	return "pages/mailbox/mailbox";
            }
            else if(statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()){
            	return "pages/mailbox/mailbox";
            }
            
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
            	return "pages/mailbox/mailbox";
            }
        }
        return "error";
    }

    public String getErrorPath() {
        return null;
    }
}