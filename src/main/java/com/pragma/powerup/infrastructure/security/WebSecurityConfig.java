package com.pragma.powerup.infrastructure.security;

import com.pragma.powerup.infrastructure.security.JWTAuthenticationFilter;
import com.pragma.powerup.infrastructure.security.JWTAuthorizationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {

        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("login");


        return http
                .csrf(csrf->csrf.disable())
                .authorizeRequests().antMatchers().permitAll()
                        .anyRequest()
                        .authenticated().and()
                .httpBasic().and()
                .sessionManagement(sessionM->sessionM.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.sessionManagement() //Gestion de Sesiones
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(); //Creamos una instancia
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin123")) //Usamos la implementacion del bcrypt para encriptar la contraseña
                .roles()
                .build());
        return manager;
    }

    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class) //Obtenemos la misma clase para usar las implementaciones
                .userDetailsService(userDetailsService) //Usamos el detailsService que contiene a nuestro admin
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
        //Usamos el encriptador de contraseñas
    }

    //Implementacion de PasswordEncoder para encriptar las contraseñas
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
