package za.co.africanbank.datascience.abdocs.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationProvider customAuthProvider;
    @Autowired
    private DeniedHandler accessDeniedHandler;

    @Bean
    public HttpFirewall allowSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.httpFirewall(allowSemicolonHttpFirewall());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/resources/**").permitAll() 
            .antMatchers("/dist/css/**").permitAll()
            .antMatchers("/dist/img/**").permitAll()
            .antMatchers("/dist/js/**").permitAll()
            .antMatchers("/plugins/**").permitAll()
            .antMatchers("/build/**").permitAll()
            .antMatchers("/options").authenticated()
            .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/ABDocs")
                .permitAll()
            .and()
            .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .clearAuthentication(true).permitAll()
            .and()
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
            .and()
            .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/ABDocs?expiredsession")
                .maxSessionsPreventsLogin(false)
            .and().disable();
            //.csrf().disable();

        http.headers()
        .httpStrictTransportSecurity()
            .includeSubDomains(true)
            .preload(true)
            .maxAgeInSeconds(31536000)
            .requestMatcher(r -> true)
        .and()
        .contentTypeOptions()
        .and()
        .frameOptions()
            .disable()
        .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider).inMemoryAuthentication();
    }
}
