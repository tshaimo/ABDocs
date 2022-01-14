package za.co.africanbank.datascience.abdocs.constant;

public enum constant {

	PROPERTYFILE("C:/ABDocs_Config/conf/ABDocs.properties"),
	SMSPREFIX("<exp:RequestDocument xmlns:exp=\"http://exposeservice.documentservice.abil/\">"),
	SMSSUFFIX("</exp:RequestDocument>"),
	GREATERTHANHTML("&gt;"), 
	LESSTHANHTML("&lt;"),
	AMPGREATERTHANHTML("&amp;gt;"),
	AMPLESSTHANHTML("&amp;lt;"),
	AMPMULTI(";amp;"),
	FORWARDSLASHHTML("&#xD;"),
	AMPORWARDSLASHHTML("&amp;#xD;"),
	GREATERTHAN(">"),
	SPACE("&nbsp;"),
	LESSTHAN("<"),
	LESSTHANEQUAL("<="),
	ENCODING("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");	


	private String value;

	constant(String constant) {
		this.value = constant;
	}

	public String value() {
		return value;
	}

}
