package com.example.todo_app.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigClass {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CustomerDetailsService customerDetailsService) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                //httpBasic below was commented when the app was hosted in render. while running in localhost, uncomment this line
//                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(httpRequests -> httpRequests
                        .requestMatchers(
                                "/users/create-user",
                                "/api/login",
                                "/login",
                                "/error",
                                "/",
                                "/public/**",
                                "/api/auth/check",
                                "/logout",
                                "/todo/delete/**"
                        ).permitAll()
                        .requestMatchers(
                                "/users/get-user-by-email",
                                "/todo/*",
                                "/hello-world",
                                "/get-todos"
                        ).authenticated()
                )
                .userDetailsService(customerDetailsService)
                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
//                            response.getWriter().write("{\"message\":\"Login successful\"}");
//                            response.setContentType("application/json");
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\":\"Invalid credentials\"}");
                            response.setContentType("application/json");
                        })
                                
                )
                .logout(logout -> {
                    logout.logoutUrl("/logout")
                            .invalidateHttpSession(true)
                            .clearAuthentication(true)
                            .deleteCookies("JSESSIONID");

                })
                .csrf(csrf -> csrf.disable())
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
