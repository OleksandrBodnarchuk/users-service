package pl.alex.users.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.alex.users.data.entity.UserEntity;
import pl.alex.users.data.repository.UserRepository;
import pl.alex.users.shared.UserDto;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDto saveUser(UserDto userDto) {

    val save = userRepository.save(
        // FIXME: fix password encoding
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
