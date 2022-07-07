package za.co.africanbank.datascience.abdocs.authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomAuthenticationProvider customAuthProvider;
	@Autowired
	private DeniedHandler accessDeniedHandler;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
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

		http.headers()
		.httpStrictTransportSecurity()
		.includeSubDomains(true)
		.preload(true)
		.maxAgeInSeconds(31536000)
		.requestMatcher(r -> true);
		http.headers().contentTypeOptions();
		http.headers().frameOptions().disable();
		http.headers().referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER);

		//httpSecurity.headers().httpStrictTransportSecurity()
		//.requestMatcher(AnyRequestMatcher.INSTANCE);
		// and().invalidSessionUrl("/Retentions?sessionexpired");

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthProvider).inMemoryAuthentication();
	}


}