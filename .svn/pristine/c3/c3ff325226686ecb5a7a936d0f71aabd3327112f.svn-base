package za.co.africanbank.datascience.abdocs.authentication;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
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
    
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
  
   
     if (isLdapRegisteredUser(auth.getName(), auth.getCredentials().toString()) && user.getUserDetail(auth.getName()).getUsername().equalsIgnoreCase(auth.getName())) {
            return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials().toString(), new ArrayList<>());
        } else {
        	logger.error("Acces Failed for ", auth.getName());
            return null;
        }
    
    }
  
    
    boolean isLdapRegisteredUser(String username, String password) {
		try
		{
			Hashtable<String, String> env = new Hashtable<String, String>();
	        env.put(Context.INITIAL_CONTEXT_FACTORY, ldapContextFactory);
	        env.put(Context.PROVIDER_URL, ldapUrlForServer1);
	        env.put(Context.SECURITY_AUTHENTICATION, "simple");
	        env.put(Context.SECURITY_PRINCIPAL, username+ DOMAIN_NAME); 
	        env.put(Context.SECURITY_CREDENTIALS, password);
	        DirContext ctx = new InitialDirContext(env);
	        if(ctx != null)
	            ctx.close();
	       return true; 
		}catch(NamingException ae)		{
			
			logger.error("Acces Failed for ", username);
			return false;
		} 

    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }



}

