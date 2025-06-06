package diego.soro.keycloak.service;


import diego.soro.model.user.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface IKeycloakService {

    List<UserRepresentation> findAllUsers();

    List<UserRepresentation> searchUserByUsername(String username);

    String createUser(UserDTO userDTO);

    String registerUser(UserDTO userDTO);

    void deleteUser(String userId);

    void updateUser(String userId, UserDTO userDTO);
}
