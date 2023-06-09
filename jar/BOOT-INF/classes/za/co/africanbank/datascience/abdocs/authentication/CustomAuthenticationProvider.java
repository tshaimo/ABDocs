package za.co.africanbank.datascience.abdocs.authentication;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import za.co.africanbank.datascience.abdocs.dao.ABDocsDao;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsUsersActivityRepository;
import za.co.africanbank.datascience.abdocs.repositories.ABDocsUsersRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	private static final String DOMAIN_NAME = "@AfricanBank.net";
	@Value("${ldap.url.for.server1}")
	private String ldapUrlForServer1; 
	@Value("${ldap.jndi.context.factory}")
	private String ldapContextFactory;
	@Value("${ldap.authentication.type}")
	private String ldapAuthenticationType;
	@Autowired
	private ABDocsUsersRepository user;
	@Autowired
	ABDocsDao dao;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {


		if (isLdapRegisteredUser(auth.getName(), auth.getCredentials().toString())) {

			dao.saveUserActivity(auth.getName().toString());
			return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials().toString(), new ArrayList<>());
		} else {
			logger.error("Acces Failed for ", auth.getName());
			return null;
		}

	}


	boolean isLdapRegisteredUser(String username, String password) {
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, ldapContextFactory);
			env.put(Context.PROVIDER_URL, ldapUrlForServer1);
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, username+ DOMAIN_NAME); 
			env.put(Context.SECURITY_CREDENTIALS, password);
			DirContext ctx = new InitialDirContext(env);
			String searchFilter = "(&(objectCategory=Person)(sAMAccountName="+username+")(memberOf=cn="+((System.getenv("ad_group") != null)?System.getenv("ad_group"):"DART_webApplicationAccess")+",cn=Users,dc=Africanbank,dc=net))";
			String[] reqAtt = {"ou","cn","sn","uid","givenName","sAMAccountName"};
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			controls.setReturningAttributes(reqAtt);
			@SuppressWarnings("rawtypes")
			NamingEnumeration users = ctx.search("dc=africanbank,dc=net", searchFilter, controls);

			SearchResult result = null;
			String name ="";
			logger.info("Attempting Login");
			while (users.hasMore() && ctx !=null) {

				result = (SearchResult) users.next();

				Attributes attr = result.getAttributes();
				 name = attr.get("cn").get(0).toString();
				logger.info(name + " Has Logged-In Successfully");
				ctx.close();
				return true;
			}
		}catch(NamingException ae) {
			ae.printStackTrace();
			logger.error("Acces Failed for ", username);
			return false;
		}
		return false; 

	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}



}

