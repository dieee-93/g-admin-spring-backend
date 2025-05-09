package diego.soro.keycloak;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello-admin")
    @PreAuthorize("hasRole('admin')")
    public String helloAdmin() {
        return "Hello Sprig Boot With Keycloak with ADMIN";
    }

    @GetMapping("/hello-staff")
    @PreAuthorize("hasRole('staff') or hasRole('admin')")
    public String helloStaff() {
        return "Hello Sprig Boot With Keycloak with STAFF";
    }

    @GetMapping("/hello-user")
    @PreAuthorize("hasRole('user') or hasRole('admin') or hasRole('staff')")
    public String helloUser() {
        return "Hello Sprig Boot With Keycloak with USER";
    }
}
