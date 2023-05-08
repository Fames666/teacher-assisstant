//package by.ezubkova.teacher_assistant.user_management.config;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableMethodSecurity
//@AllArgsConstructor
//public class SecurityConfiguration {
//
////  private final UserRepository userRepository;
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//    return httpSecurity
//        .csrf(CsrfConfigurer::disable)
//        .cors(CorsConfigurer::disable)
//        .formLogin(withDefaults())
//        .authorizeHttpRequests(authorization -> authorization
//            .requestMatchers("/login").permitAll()
//            .anyRequest().permitAll())
//        .build();
//  }
//
////  @Bean
////  @Profile("dev")
////  @Transactional
////  UserDetailsService userDetailsService_dev() {
////    var adminRole = new Role("ROLE_ADMIN");
////    var admin = User.builder()
////                    .id("437cf64f-bb47-47f4-8d4a-6ed709d1233d")
////                    .username("admin")
////                    .password("admin")
////                    .enabled(true)
////                    .accountNonLocked(true)
////                    .roles(singleton(adminRole))
////                    .build();
////    var manager = userRepository.getEntityManager();
////    manager.setFlushMode(COMMIT);
////    manager.persist(adminRole);
////    manager.persist(admin);
////    return username -> userRepository.findUserByUsername(username)
////                                     .orElseThrow(() -> new RuntimeException(""));
////  }
////
////  @Bean
////  @Profile("!dev")
////  UserDetailsService userDetailsService() {
////    return username -> userRepository.findUserByUsername(username)
////                                     .orElseThrow(() -> new RuntimeException(""));
////  }
//
//  @Bean
//  AuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
//                                                   PasswordEncoder passwordEncoder) {
//    var provider = new DaoAuthenticationProvider();
//    provider.setPasswordEncoder(passwordEncoder);
//    provider.setUserDetailsService(userDetailsService);
//    return provider;
//  }
//
//  @Bean
//  PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//}
