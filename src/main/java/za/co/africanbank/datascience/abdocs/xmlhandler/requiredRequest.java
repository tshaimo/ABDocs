package za.co.africanbank.datascience.abdocs.xmlhandler;

public class requiredRequest {
	
  public String smsRequest(String Email, String Subject, String TextBody) {
	  
	  String inputXML="<DocumentRequest>\r\n" + 
	  		"                <systemType>BLAZE</systemType>\r\n" + 
	  		"                <documentType>NOTIFICATION</documentType>\r\n" + 
	  		"                <keyValue>\r\n" + 
	  		"                    <key>NOTIFICATIONSUBJECT</key>\r\n" + 
	  		"                    <value>"+Subject+"</value>\r\n"+ 
	  		"                </keyValue>\r\n" + 
	  		"                <keyValue>\r\n" + 
	  		"                    <key>NOTIFICATIONBODY</key>\r\n" + 
	  		"                    <value>"+TextBody+"</value>\r\n" + 
	  		"                </keyValue>\r\n" + 
	  		"                <loanId>0</loanId>\r\n" + 
	  		"                <accountReference></accountReference>\r\n" + 
	  		"                <communicationDetail>\r\n" + 
	  		"                    <channel>EMAIL</channel>\r\n" + 
	  		"                    <emailAddress>"+Email+"</emailAddress>\r\n" + 
	  		"                </communicationDetail>\r\n" + 
	  		"                <accountType>LOAN</accountType>\r\n" + 
	  		"                <processID>0</processID>\r\n" + 
	  		"            </DocumentRequest>";
	  return inputXML;
  }
  
}
