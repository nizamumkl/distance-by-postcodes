package com.nizam.geo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {      
      httpSecurity.csrf(customizer -> customizer.disable())
	  	.authorizeHttpRequests(authorize -> authorize
	          .requestMatchers("/postcodes/**").hasRole("USER")
	          .requestMatchers("/distance/**").hasRole("USER")
	          .anyRequest().authenticated()
	      )
	  	.httpBasic(Customizer.withDefaults());
      
      return  httpSecurity.build();
    }

}
