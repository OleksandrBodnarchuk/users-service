package pl.alex.app.users.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @GetMapping("/status/check")
    public String status() {
        return "users-service is working";
    }
}
