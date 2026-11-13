package com.zidio.keystone.config;
import com.zidio.keystone.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

 private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
  
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

    http
        .cors(org.springframework.security.config.Customizer.withDefaults())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth

            // Public endpoints
            .requestMatchers(
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
                "/api/auth/login"
            ).permitAll()

            // User administration
            .requestMatchers("/api/users/**")
                .hasRole(RoleAccessConfig.ADMIN)

            // Reports
            .requestMatchers("/api/reports/**")
                .hasAnyRole(
                    RoleAccessConfig.ADMIN,
                    RoleAccessConfig.MANAGER
                )

            // Customer and site management
            .requestMatchers("/api/customers/**", "/api/sites/**")
                .hasAnyRole(
                    RoleAccessConfig.ADMIN,
                    RoleAccessConfig.MANAGER,
                    RoleAccessConfig.TECHNICIAN
                )

            // Remaining endpoints require authentication
            .anyRequest().authenticated()
        );

     http.addFilterBefore(
        jwtAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class
    );

    return http.build();
 }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}