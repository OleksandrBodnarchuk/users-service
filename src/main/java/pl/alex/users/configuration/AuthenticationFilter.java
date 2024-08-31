package pl.alex.users.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.alex.users.ui.model.LoginRequest;

@Slf4j
class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final ObjectMapper objectMapper;
  private final JwtService jwtService;

  public AuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
    super(authenticationManager);
    this.objectMapper = new ObjectMapper();
    this.jwtService = jwtService;
    this.setFilterProcessesUrl("/users/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    log.info("Attempting authentication");
    try {
      LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(),
          LoginRequest.class);
      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password(),
              new ArrayList<>()));
    } catch (IOException e) {
      log.error("Error reading input: {}", e.getMessage());
      try {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid login request");
      } catch (IOException ioException) {
        log.error("Error sending response error: {}", ioException.getMessage());
      }
      return null;
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {
    response.setHeader("Content-Type", "application/json");
    response.getOutputStream()
        .write(objectMapper.writeValueAsString(jwtService.generateToken(authResult)).getBytes());
  }
}
