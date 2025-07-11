package diego.soro.model2.core.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Set<UUID> roleIds;
    private Set<UUID> branchIds;
}