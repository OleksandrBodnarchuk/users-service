package pl.alex.users.service;

import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.alex.users.data.entity.UserEntity;
import pl.alex.users.data.repository.UserRepository;
import pl.alex.users.shared.UserDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("loadUserByUsername {}", username);
    Optional<UserEntity> userEntity = userRepository.findByEmail(username);
    if (userEntity.isPresent()) {
      return new User(userEntity.get().getEmail(), userEntity.get().getEncryptedPassword(),
          new ArrayList<>());
    }
    log.error("User not found");
    throw new UsernameNotFoundException(username);
  }

  @Override
  public UserDto saveUser(UserDto userDto) {

    val save = userRepository.save(
        UserEntity.of(userDto, passwordEncoder.encode(userDto.getPassword()))
    );

    return UserDto.builder()
        .userId(save.getUserId())
        .name(userDto.getEmail())
        .surname(userDto.getSurname())
        .email(userDto.getEmail())
        .encryptedPassword(save.getEncryptedPassword())
        .build();
  }
}
