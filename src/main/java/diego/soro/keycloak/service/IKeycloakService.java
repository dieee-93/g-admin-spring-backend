package diego.soro.keycloak.service;


import diego.soro.model.user.UserDTO;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IKeycloakService {

    List<UserRepresentation> findAllUsers();

    List<UserRepresentation> searchUserByUsername(String username);

    String createUser(UserDTO userDTO);

    Optional<String> registerUser(UserDTO userDTO);

    List<RoleRepresentation> getRolesToAssign(Set<String> roleNames, RealmResource realm);

    void deleteUser(String userId);

    void updateUser(String userId, UserDTO userDTO);
}
