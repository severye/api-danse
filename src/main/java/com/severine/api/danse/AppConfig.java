package com.severine.api.danse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import com.google.common.collect.ImmutableList;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.severine.api.danse" })
@Import({DomainAndPersistenceConfig.class})
@PropertySources({
	@PropertySource("classpath:auth0.properties"),
	@PropertySource("classpath:application.properties")
})
@Configuration
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;
    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors();
        JwtWebSecurityConfigurer
	        .forRS256(apiAudience, issuer)
	        .configure(http)
	        .authorizeRequests()
	        .antMatchers(HttpMethod.POST, "/pictures").permitAll()
	        .antMatchers(HttpMethod.GET, "/*").authenticated()
	        .antMatchers(HttpMethod.POST, "/*").authenticated()
	        .antMatchers(HttpMethod.DELETE, "/*").authenticated()
	        .antMatchers(HttpMethod.PUT, "/*").authenticated();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD","GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
//    	 CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOrigins(ImmutableList.of("*"));
//         configuration.setAllowedMethods(ImmutableList.of("HEAD","GET", "POST", "PUT", "DELETE", "PATCH","OPTIONS"));
//         configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type", "access-control-allow-origin", "x-api-key"));
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);
//         return source;
    }
    
}