package com.qindel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Configuración de seguridad para la aplicación.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configuración de reglas de seguridad HTTP.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Desactivar CSRF (Cross-Site Request Forgery) para simplificar
        http.csrf().disable()
                // Permitir acceso sin autenticación a ciertos endpoints
                .authorizeRequests().antMatchers("/swagger-ui/**", "/v2/api-docs", "/swagger-resources/**", "/webjars/**","/generate-token", "/getPrice").permitAll()
                // Exigir autenticación para cualquier otra solicitud
                .anyRequest().authenticated()
                .and()
                // Configurar la gestión de sesiones como sin estado (stateless)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Bean para proporcionar el filtro de autenticación JWT.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}
