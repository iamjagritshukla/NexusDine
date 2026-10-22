
package com.canteen.digitalcanteen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder encoder,
            @Value("${APP_ADMIN_USERNAME:admin}") String adminUser,
            @Value("${APP_ADMIN_PASSWORD:admin123-change-this}") String adminPass,
            @Value("${APP_COOK_USERNAME:cook}") String cookUser,
            @Value("${APP_COOK_PASSWORD:cook123-change-this}") String cookPass) {

        return new InMemoryUserDetailsManager(
                User.withUsername(adminUser)
                        .password(encoder.encode(adminPass))
                        .roles("ADMIN")
                        .build(),
                User.withUsername(cookUser)
                        .password(encoder.encode(cookPass))
                        .roles("COOK")
                        .build()
        );
    }

    @Bean
    public AuthenticationSuccessHandler roleRedirect() {
        return (request, response, authentication) -> {
            boolean isAdmin = authentication.getAuthorities()
                    .stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                response.sendRedirect("/admin");
            } else {
                response.sendRedirect("/cook");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationSuccessHandler roleRedirect) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Public pages and static resources
                        .requestMatchers(
                                "/", "/menu", "/login",
                                "/css/**", "/js/**", "/images/**",
                                "/qr-code.png",
                                "/actuator/health", "/actuator/info"
                        ).permitAll()

                        // Customer APIs: deny access to Cook accounts
                        .requestMatchers(
                                "/api/menu",
                                "/api/orders",
                                "/api/orders/**",
                                "/orders/**"
                        ).access((authentication, context) -> {
                            Authentication current = authentication.get();

                            boolean isCook = current.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_COOK"));

                            return new AuthorizationDecision(!isCook);
                        })

                        // Admin pages
                        .requestMatchers("/admin", "/admin/**", "/api/admin/**", "/qr")
                        .hasRole("ADMIN")

                        // Cook pages
                        .requestMatchers("/cook", "/cook/**", "/api/cook/**")
                        .hasAnyRole("COOK", "ADMIN")

                        // Other routes
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(roleRedirect)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                );

        return http.build();
    }
}
