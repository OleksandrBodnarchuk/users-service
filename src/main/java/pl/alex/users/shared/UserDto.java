package pl.alex.users.shared;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import pl.alex.users.ui.model.RegistrationRequest;

@Getter
@Builder
public class UserDto implements Serializable {

  private String userId;
  private String name;
  private String surname;
  private String email;
  private String password;
  private String encryptedPassword;

  public static UserDto from(RegistrationRequest registrationRequest) {
    return UserDto.builder()
        .name(registrationRequest.name())
        .surname(registrationRequest.surname())
        .email(registrationRequest.email())
        .password(registrationRequest.password())
        .build();
  }
}
