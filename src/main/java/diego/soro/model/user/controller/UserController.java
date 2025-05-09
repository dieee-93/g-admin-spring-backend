package diego.soro.model.user.controller;


import diego.soro.keycloak.service.IKeycloakService;
import diego.soro.model.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/keycloak/user")
public class UserController {

    @Autowired
    private IKeycloakService keycloakService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        String response = keycloakService.registerUser(userDTO);
        return ResponseEntity.created(new URI("/keycloak/user/register")).body(response);
    }

}
