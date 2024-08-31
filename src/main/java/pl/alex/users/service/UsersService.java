package pl.alex.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.alex.users.shared.UserDto;

public interface UsersService extends UserDetailsService {

  UserDto saveUser(UserDto userDetails);

}
