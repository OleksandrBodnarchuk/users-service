package pl.alex.users.shared;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import pl.alex.users.ui.model.UserRegistrationRequest;

@Getter
@Builder
public class UserDto implements Serializable {

  private String userId;
  private String name;
  private String surname;
  private String email;
  private String password;
  private String encryptedPassword;

  public static UserDto from(UserRegistrationRequest userRegistrationRequest) {
    return UserDto.builder()
        .name(userRegistrationRequest.name())
        .surname(userRegistrationRequest.surname())
        .email(userRegistrationRequest.email())
        .password(userRegistrationRequest.password())
        .build();
  }
}
