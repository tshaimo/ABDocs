package za.co.africanbank.datascience.abdocs.authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomAuthenticationProvider customAuthProvider;
	@Autowired
    private DeniedHandler accessDeniedHandler;
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
		.antMatchers("/resources/**").permitAll() 
		.antMatchers("/dist/css/**").permitAll()
		.antMatchers("/dist/img/**").permitAll()
		.antMatchers("/dist/js/**").permitAll()
		.antMatchers("/plugins/**").permitAll()
		.antMatchers("/build/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/ABDocs")
		.permitAll()
		.and()
		.logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
	    .clearAuthentication(true).permitAll().and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and().sessionManagement().maximumSessions(1)
        .expiredUrl("/ABDocs?expiredsession")
        .maxSessionsPreventsLogin(false);
       // and().invalidSessionUrl("/Retentions?sessionexpired");
       
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthProvider).inMemoryAuthentication();
	}
	
	  
}