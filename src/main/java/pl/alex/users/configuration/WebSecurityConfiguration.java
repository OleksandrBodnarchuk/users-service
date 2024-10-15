package pl.alex.users.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

  private final Environment environment;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> {
              auth.requestMatchers(HttpMethod.GET, "/users/test").permitAll();
              auth.requestMatchers(HttpMethod.POST, "/users/register").permitAll();
              auth.requestMatchers(HttpMethod.POST, "/users/*")
                  .access(new WebExpressionAuthorizationManager(
                      "hasIpAddress('" + environment.getProperty("gateway.ip") + "')")
                  )
                  .anyRequest().authenticated();
            }
        )
        .addFilterBefore(new AuthenticationFilter(authenticationManager, jwtService),
            UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }

}
