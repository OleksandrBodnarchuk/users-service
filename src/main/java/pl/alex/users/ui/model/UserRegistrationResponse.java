package pl.alex.users.ui.model;


import lombok.Builder;
import pl.alex.users.shared.UserDto;

@Builder
public record UserRegistrationResponse(String userId, String name, String surname, String email) {

  public static UserRegistrationResponse from(UserDto userDto) {
    return UserRegistrationResponse.builder()
        .userId(userDto.getUserId())
        .name(userDto.getName())
        .surname(userDto.getSurname())
        .email(userDto.getEmail())
        .build();
  }
}
