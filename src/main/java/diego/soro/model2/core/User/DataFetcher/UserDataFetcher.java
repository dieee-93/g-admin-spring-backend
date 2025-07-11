package diego.soro.model2.core.User.DataFetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

import com.netflix.graphql.dgs.*;

import diego.soro.exception.InvalidArgument;
import diego.soro.graphql.generated.types.CreateUserInput;
import diego.soro.graphql.generated.types.CreateUserResponse;
import diego.soro.graphql.generated.types.RegisterUserResponse;
import diego.soro.keycloak.service.IKeycloakService;
import diego.soro.model.user.UserDTO;
import diego.soro.model2.core.User.User;
import diego.soro.model2.core.User.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;


@DgsComponent
@RequiredArgsConstructor
public class UserDataFetcher {
    private final UserService userService;
    private final IKeycloakService keycloakService;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern ROLE_CODE_PATTERN = Pattern.compile("^ROLE_[A-Z0-9_]+$");

    @DgsMutation
    public CreateUserResponse createUser(@InputArgument("input") CreateUserInput input) {
        validateInput(input);

        Set<String> roles = new HashSet<>();
        if (input.getRoles() != null) {
            for (String code : input.getRoles()) {
                if (!ROLE_CODE_PATTERN.matcher(code).matches()) {
                    throw new InvalidArgument("Invalid role code: " + code + ". Must start with 'ROLE_'.");
                }
                roles.add(code);
            }
        }

        UserDTO dto = UserDTO.builder()
                .username(input.getEmail())
                .email(input.getEmail())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .password(input.getPassword())
                .fullAddress(input.getFullAddress())
                .zipCode(input.getZipCode())
                .phone(input.getPhone())
                .registerDate(input.getRegisterDate())
                .roles(roles)
                .build();

        Optional<String> keycloakId = keycloakService.registerUser(dto);

        if (keycloakId.isEmpty()) {
            return CreateUserResponse.newBuilder()
                    .status("ERROR")
                    .message("User registration failed or already exists")
                    .build();
        }


        String keycloakIdStr = keycloakId.get();

        // 3. Validar UUID Keycloak
        UUID keycloakUUID;
        try {
            keycloakUUID = UUID.fromString(keycloakIdStr);
        } catch (IllegalArgumentException e) {
            return CreateUserResponse.newBuilder()
                    .status("ERROR")
                    .message("Invalid Keycloak ID received: " + keycloakIdStr)
                    .build();
        }

        userService.create(User.builder()
                .keycloakId(keycloakUUID)
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .tenantId(null)
                .build());

        return CreateUserResponse.newBuilder()
                .status("CREATED")
                .message("User registered successfully")
                .build();
    }

    private void validateInput(CreateUserInput input) {
        if (input.getEmail() == null || !EMAIL_PATTERN.matcher(input.getEmail()).matches()) {
            throw new InvalidArgument("Invalid or missing email");
        }
        if (input.getPassword() == null || input.getPassword().length() < 8) {
            throw new InvalidArgument("Password must be at least 8 characters long");
        }
        // Otros campos opcionales: firstName/lastName pueden ser null o vacíos según requerimiento
    }
}
