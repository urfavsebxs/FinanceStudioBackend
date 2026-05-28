package com.sebas.finance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitamos CSRF ya que para APIs REST con Stateless no suele ser necesario
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Permitimos el acceso total a todas las rutas
                        .anyRequest().permitAll()
                )
                // Opcional: Si quieres evitar que se muestre el formulario de login por defecto
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults());

        return http.build();
    }
}
