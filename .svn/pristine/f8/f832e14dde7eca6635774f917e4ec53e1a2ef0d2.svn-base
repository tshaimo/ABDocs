package za.co.africanbank.datascience.abdocs.utilities;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class Test {

	private static final String DOMAIN_NAME = "@AfricanBank.net";
	public static void main(String[] args) {
		try
		{
			Hashtable<String, String> env = new Hashtable<String, String>();
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL, "ldap://10.30.124.10:389");
	        env.put(Context.SECURITY_AUTHENTICATION, "simple");
	        env.put(Context.SECURITY_PRINCIPAL, "Pmabunda2"+ DOMAIN_NAME); 
	        env.put(Context.SECURITY_CREDENTIALS, "");
	        DirContext ctx = new InitialDirContext(env);
	        //New code..........................................
	        
	        String searchFilter = "(&(objectCategory=Person)(sAMAccountName="+DOMAIN_NAME+")(memberOf=cn=DART_webApplicationAccess,cn=Users,dc=Africanbank,dc=net))";
			String[] reqAtt = {"ou","cn","sn","uid","givenName","sAMAccountName"};
			SearchControls controls = new SearchControls();
	        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			controls.setReturningAttributes(reqAtt);

			@SuppressWarnings("rawtypes")
			NamingEnumeration users = ctx.search("dc=africanbank,dc=net", searchFilter, controls);

			SearchResult result = null;
			while (users.hasMore()) {
				
				result = (SearchResult) users.next();
				
				Attributes attr = result.getAttributes();
				String name = attr.get("cn").get(0).toString();
				System.out.println(name);
			}
	        
	        
	        
	        
	        //End New Code
	        if(ctx != null)
	            ctx.close();
	     
		}catch(NamingException ae)		{
			
			
		} 


	}

}
