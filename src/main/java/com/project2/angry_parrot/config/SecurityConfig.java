package com.project2.angry_parrot.config;

import com.project2.angry_parrot.config.jwt.JwtAuthenticationFilter;
import com.project2.angry_parrot.config.jwt.JwtAuthorizationFilter;
import com.project2.angry_parrot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final CorsConfig corsConfig;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic(AbstractHttpConfigurer :: disable)
            .csrf(AbstractHttpConfigurer:: disable)
            .formLogin(AbstractHttpConfigurer :: disable);
        http.sessionManagement(sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
//        http.apply(new MyCustomDsl());
        http.with(new MyCustomDsl(), c -> c.getClass());
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login", "/join").permitAll()
            .requestMatchers("/api/v1/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
            .requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN")
            .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
//            .anyRequest().authenticated()
            .anyRequest().permitAll()
        );
        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                .addFilter(corsConfig.corsFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManager))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
