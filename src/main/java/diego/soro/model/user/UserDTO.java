package diego.soro.model.user;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

@Value
@RequiredArgsConstructor
@Builder
public class UserDTO implements Serializable {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String fullAddress;
    private String zipCode;
    private String phone;
    private String registerDate;
    private Set<String> roles;
}