package org.simon.pascal;

import org.hdiv.config.annotation.ExclusionRegistry;
import org.hdiv.config.annotation.configuration.HdivWebSecurityConfigurerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableAutoConfiguration
public class DemoHdivApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoHdivApplication.class, args);
	}
        
        @Bean
	public WebConfig webConfig() {
		return new WebConfig();
	}
	
	protected static class WebConfig extends WebMvcConfigurerAdapter{
		
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/login").setViewName("login");
		}
	}
	
	@Bean
	public ApplicationSecurity applicationSecurity() {
		return new ApplicationSecurity();
	}

	protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth
	            .inMemoryAuthentication()
	                .withUser("david").password("david").roles("USER","ADMIN").and()
	                .withUser("alex").password("alex").roles("USER").and()
	                .withUser("tim").password("tim").roles("USER");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests().anyRequest().fullyAuthenticated().and()
				.formLogin()
					.loginPage("/login")
					.failureUrl("/login?error").permitAll();
		}
	}
	
	@Bean
	public ApplicationWebSecurity applicationWebSecurity() {
		return new ApplicationWebSecurity();
	}

	protected static class ApplicationWebSecurity extends HdivWebSecurityConfigurerAdapter {

		@Override
		public void addExclusions(ExclusionRegistry registry) {
			registry.addUrlExclusions("/login");
		}
	}
}
