package diego.soro.keycloak.service;


import diego.soro.model.user.UserDTO;
import diego.soro.utils.KeycloakProvider;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class KeycloakServiceImpl implements IKeycloakService {

    /**
     * Metodo para listar todos los usuarios de Keycloak
     *
     * @return List<UserRepresentation>
     */
    public List<UserRepresentation> findAllUsers() {
        return KeycloakProvider.getRealmResource()
                .users()
                .list();
    }

    /**
     * Metodo para buscar un usuario por su username
     *
     * @return List<UserRepresentation>
     */
    public List<UserRepresentation> searchUserByUsername(String username) {
        return KeycloakProvider.getRealmResource()
                .users()
                .searchByUsername(username, true);
    }


    /**
     * Metodo para crear un usuario en keycloak
     *
     * @return String
     */
    public String createUser(@NonNull UserDTO userDTO) {

        int status = 0;
        UsersResource usersResource = KeycloakProvider.getUserResource();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setFirstName(userDTO.getFirstName());
        userRepresentation.setLastName(userDTO.getLastName());

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("address", Collections.singletonList(userDTO.getFullAddress()));
        attributes.put("zipCode", Collections.singletonList(userDTO.getZipCode()));
        attributes.put("phone", Collections.singletonList(userDTO.getPhone()));
        attributes.put("registerDate", Collections.singletonList(userDTO.getRegisterDate()));

        userRepresentation.setAttributes(attributes);

        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        //Response response = usersResource.create(userRepresentation);
        Response response1 = usersResource.create(userRepresentation);
        status = response1.getStatus();

        if (status == 201) {
            String path = response1.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(userDTO.getPassword());

            usersResource.get(userId).resetPassword(credentialRepresentation);

            RealmResource realmResource = KeycloakProvider.getRealmResource();

            List<RoleRepresentation> rolesRepresentation = null;

            if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
                rolesRepresentation = List.of(realmResource.roles().get("user").toRepresentation());
            } else {
                rolesRepresentation = realmResource.roles()
                        .list()
                        .stream()
                        .filter(role -> userDTO.getRoles()
                                .stream()
                                .anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName())))
                        .toList();
            }

            realmResource.users().get(userId).roles().realmLevel().add(rolesRepresentation);

            return "Success";

        } else if (status == 409) {
            log.error("User exist already!");
            return "UserExists";
        } else {
            log.error("Error creating user, please contact with the administrator.");
            return "ServiceError";
        }
    }

    public String createAdmin(@NonNull UserDTO userDTO) {

        int status = 0;
        UsersResource usersResource = KeycloakProvider.getUserResource();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setFirstName(userDTO.getFirstName());
        userRepresentation.setLastName(userDTO.getLastName());

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("address", Collections.singletonList(userDTO.getFullAddress()));
        attributes.put("zipCode", Collections.singletonList(userDTO.getZipCode()));
        attributes.put("phone", Collections.singletonList(userDTO.getPhone()));
        attributes.put("registerDate", Collections.singletonList(userDTO.getRegisterDate()));

        userRepresentation.setAttributes(attributes);

        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        //Response response = usersResource.create(userRepresentation);
        Response response1 = usersResource.create(userRepresentation);
        status = response1.getStatus();

        if (status == 201) {
            String path = response1.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(userDTO.getPassword());

            usersResource.get(userId).resetPassword(credentialRepresentation);

            RealmResource realmResource = KeycloakProvider.getRealmResource();

            List<RoleRepresentation> rolesRepresentation = null;

            if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
                rolesRepresentation = List.of(realmResource.roles().get("admin").toRepresentation());
            } else {
                rolesRepresentation = realmResource.roles()
                        .list()
                        .stream()
                        .filter(role -> userDTO.getRoles()
                                .stream()
                                .anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName())))
                        .toList();
            }

            realmResource.users().get(userId).roles().realmLevel().add(rolesRepresentation);

            return "Success";

        } else if (status == 409) {
            log.error("User exist already!");
            return "UserExists";
        } else {
            log.error("Error creating user, please contact with the administrator.");
            return "ServiceError";
        }
    }

    public String registerUser(@NonNull UserDTO userDTO) {

        int status = 0;
        UsersResource usersResource = KeycloakProvider.getUserResource();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userDTO.getEmail());
        userRepresentation.setEmail(userDTO.getEmail());
        userRepresentation.setFirstName(userDTO.getFirstName());
        userRepresentation.setLastName(userDTO.getLastName());

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("address", Collections.singletonList(userDTO.getFullAddress()));
        attributes.put("zipCode", Collections.singletonList(userDTO.getZipCode()));
        attributes.put("phone", Collections.singletonList(userDTO.getPhone()));
        attributes.put("registerDate", Collections.singletonList(userDTO.getRegisterDate()));

        userRepresentation.setAttributes(attributes);

        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);


        Response response = usersResource.create(userRepresentation);
        status = response.getStatus();

        if (status == 201) {
            String path = response.getLocation().getPath();
            String userId = path.substring(path.lastIndexOf("/") + 1);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(userDTO.getPassword());

            usersResource.get(userId).resetPassword(credentialRepresentation);

            RealmResource realmResource = KeycloakProvider.getRealmResource();

            List<UserRepresentation> userList = KeycloakProvider.getRealmResource()
                    .users()
                    .list();
            List<RoleRepresentation> rolesRepresentation = null;


            rolesRepresentation = userList.size() == 1 ? List.of(realmResource.roles().get("admin").toRepresentation()) : List.of(realmResource.roles().get("user").toRepresentation());

            realmResource.users().get(userId).roles().realmLevel().add(rolesRepresentation);

            return "Success";

        } else if (status == 409) {
            log.error("User exist already!");
            return "UserExists";
        } else {
            log.error("Error creating user, please contact with the administrator.");
            log.error(String.valueOf(response.getStatusInfo()));
            return "ServiceError";
        }
    }


    /**
     * Metodo para borrar un usuario en keycloak
     *
     * @return void
     */
    public void deleteUser(String userId) {
        KeycloakProvider.getUserResource()
                .get(userId)
                .remove();
    }


    /**
     * Metodo para actualizar un usuario en keycloak
     *
     * @return void
     */
    public void updateUser(String userId, @NonNull UserDTO userDTO) {

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(OAuth2Constants.PASSWORD);
        credentialRepresentation.setValue(userDTO.getPassword());

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setCredentials(Collections.singletonList(credentialRepresentation));

        UserResource usersResource = KeycloakProvider.getUserResource().get(userId);
        usersResource.update(user);
    }
}
