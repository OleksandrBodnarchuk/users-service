package pl.alex.users.configuration;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.alex.users.service.UsersService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersAuthenticationManager implements AuthenticationManager {

  private final UsersService usersService;
  private final PasswordEncoder bCryptPasswordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    log.debug("Authenticating user {}", authentication.getName());
    UserDetails user = usersService.loadUserByUsername(authentication.getName());
    if (bCryptPasswordEncoder.matches(authentication.getCredentials().toString(),
        user.getPassword())) {
      List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
      return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
          authentication.getCredentials(), grantedAuthorityList);
    } else {
      throw new BadCredentialsException("Wrong credentials");
    }
  }
}
