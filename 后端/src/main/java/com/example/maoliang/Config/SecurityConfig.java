//package com.example.maoliang.Config;
//
//
//import com.example.maoliang.Service.UsrService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig{
//
//    @Autowired
//    private UsrService userDetailsService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll())
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // 跨域参数
////        http.authorizeRequests(authorize -> authorize
////                        .requestMatchers("/", "/error", "/register", "/choose-register", "/forgot-password", "/success", "/secret-question").permitAll()
////                        .requestMatchers("/buyer", "/buyer-history", "/likes", "/cart", "/buyer-fill-info", "/buyer-shop").hasAuthority("BUYER")
////                        .requestMatchers("/seller/**").hasAuthority("SELLER")
////                        .requestMatchers("/guest").anonymous()
////                        .anyRequest().authenticated())
////                .formLogin(formLogin -> formLogin
////                        .defaultSuccessUrl("/") // 自定义登录页面路径为根路径
////                        .permitAll())
////                .csrf(csrf -> csrf.disable()) // 关闭跨站请求伪造防护
////                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // 跨域参
//        return http.build();
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}