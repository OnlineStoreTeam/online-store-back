package com.store.configuration;

import com.store.security.CustomAuthenticationProvider;
import com.store.security.DatabaseLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomAuthenticationProvider authProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DatabaseLoginSuccessHandler databaseLoginSuccessHandler;

    public SecurityConfig(BCryptPasswordEncoder passwordEncoder,
                          DatabaseLoginSuccessHandler databaseLoginSuccessHandler) {

        this.passwordEncoder = passwordEncoder;
        this.databaseLoginSuccessHandler = databaseLoginSuccessHandler;
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

//    @Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailService);
//		authProvider.setPasswordEncoder(passwordEncoder);
//
//		return authProvider;
//	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/users/registration","/home").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(Customizer.withDefaults())
//            .formLogin(formLogin -> formLogin
//                            .loginPage("/login")
////						.failureUrl("/authentication/login?failed") // default is /login?error
////						.loginProcessingUrl("/authentication/login/process") // default is /login
//                            .usernameParameter("username")
//                            .passwordParameter("password")
//                            .defaultSuccessUrl("/*")
//                            .permitAll()
//            )
                .logout((logout) -> logout
                        .deleteCookies("JSESSIONID")
                        .permitAll())

                .csrf(csrf -> csrf.disable());
        return http.build();
    }

}
