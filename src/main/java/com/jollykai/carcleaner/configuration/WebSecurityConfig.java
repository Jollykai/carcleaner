package com.jollykai.carcleaner.configuration;

import com.jollykai.carcleaner.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Project: CarCleaner
 *
 * @author Dmitry Istomin
 * https://github.com/Jollykai
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${admin.username}")
    private String USERNAME;
    @Value("${admin.password}")
    private String PASSWORD;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/product/**", "/orders").hasRole(Role.ADMIN.getName())
                .antMatchers("/order/**", "/products").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable()
                .cors();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(USERNAME)
                .password(PASSWORD)
                .roles(Role.ADMIN.getName())
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}