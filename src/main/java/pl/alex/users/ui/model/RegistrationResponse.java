package pl.alex.users.ui.model;


import lombok.Builder;
import pl.alex.users.shared.UserDto;

@Builder
public record RegistrationResponse(String userId, String name, String surname, String email) {

  public static RegistrationResponse from(UserDto userDto) {
    return RegistrationResponse.builder()
        .userId(userDto.getUserId())
        .name(userDto.getName())
        .surname(userDto.getSurname())
        .email(userDto.getEmail())
        .build();
  }
}
