package upm.daw.easyfilm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import upm.daw.easyfilm.security.CustomAuthenticationProvider;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    public CustomAuthenticationProvider authenticationProvider;

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/").permitAll();
        //Se permite acceso a imagenes y fuentes
        http.authorizeRequests().antMatchers("/img/*").permitAll();
        http.authorizeRequests().antMatchers("/fonts/*").permitAll();

        http.authorizeRequests().anyRequest().authenticated();

        http.formLogin().loginPage("/login").usernameParameter("username")
                .passwordParameter("password").defaultSuccessUrl("/home")
                .failureUrl("/login?error").permitAll();

        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}

