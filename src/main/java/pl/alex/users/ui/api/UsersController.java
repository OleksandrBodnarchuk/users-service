package pl.alex.users.ui.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.alex.users.service.UsersService;
import pl.alex.users.shared.UserDto;
import pl.alex.users.ui.model.LoginRequest;
import pl.alex.users.ui.model.RegistrationRequest;
import pl.alex.users.ui.model.RegistrationResponse;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

  private final UsersService usersService;

  @GetMapping("/test")
  public String test() {
    return "Hello from [users-service]";
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok("LOGIN SUCCESS");
  }

  @PostMapping("/register")
  public ResponseEntity<RegistrationResponse> register(
      @Valid @RequestBody RegistrationRequest registrationRequest) {

    val userDto = usersService.saveUser(UserDto.from(registrationRequest));

    return ResponseEntity.status(HttpStatus.CREATED).body(RegistrationResponse.from(userDto));
  }
}
