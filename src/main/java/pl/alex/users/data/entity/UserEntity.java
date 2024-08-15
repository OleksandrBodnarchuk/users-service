package pl.alex.users.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.alex.users.shared.UserDto;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 50)
  private String surname;

  @Column(length = 120, unique = true)
  private String email;

  @Column(nullable = false)
  private String encryptedPassword;

  @Column(nullable = false, unique = true)
  private String userId;

  @Builder
  UserEntity(String name, String surname, String email, String encryptedPassword) {
    this.userId = userId;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.encryptedPassword = encryptedPassword;
    this.userId = UUID.randomUUID().toString();
  }

  public static UserEntity of(UserDto userDto, String encodedPassword) {
    return UserEntity.builder()
        .name(userDto.getName())
        .surname(userDto.getSurname())
        .email(userDto.getEmail())
        .encryptedPassword(encodedPassword)
        .build();
  }
}
