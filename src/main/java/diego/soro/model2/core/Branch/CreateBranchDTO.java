package diego.soro.model2.core.Branch;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBranchDTO {
    private String name;
    private String code;
    private String address;
    private String phone;
    private String email;
    private String companyId; // UUID como String, por convención
    private Boolean isMain;
}
