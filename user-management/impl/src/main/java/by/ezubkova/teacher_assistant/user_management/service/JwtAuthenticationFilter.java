//package by.ezubkova.teacher_assistant.auth.service;
//
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.validation.constraints.NotNull;
//import java.io.IOException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//  public static final String BEARER_PREFIX = "Bearer ";
//
//  @Override
//  protected void doFilterInternal(@NotNull HttpServletRequest request,
//                                  @NotNull HttpServletResponse response,
//                                  @NotNull FilterChain filterChain) throws ServletException,
//                                                                           IOException {
//    var authorization = request.getHeader(AUTHORIZATION);
//    if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
//      filterChain.doFilter(request, response);
//      return;
//    }
//
//    var jwt = authorization.substring(BEARER_PREFIX.length());
//  }
//}
