package com.company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Authentication
        auth.inMemoryAuthentication()
                .withUser("Ali").password("{bcrypt}$2a$10$V93CWoH3NxAPC7VzPd9ouuU8PWvZWYdoo94H3HOZ8kFSkBAvYssEe").roles("ADMIN")
                .and()
                .withUser("Vali").password("{noop}valish123").roles("PROFILE")
                .and()
                .withUser("Admin").password("{noop}123").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization
        http.authorizeRequests()
                .antMatchers("/article/public/*").permitAll()
                .antMatchers("/attach/**").permitAll()
                .antMatchers("/home", "/home/*").permitAll()
                .antMatchers("/article/adm/*").hasAnyRole("PROFILE", "ADMIN")
                .antMatchers("/profile", "/profile/*").hasAnyRole("PROFILE", "ADMIN")
                .antMatchers("/admin", "/admin/*").hasRole("ADMIN")
                .antMatchers("/region/adm/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic();

        http.cors().disable();
        http.csrf().disable();
    }

}
