package diego.soro.model2.core.Company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompanyDTO {
    private String name;
    private String taxId;
    private String email;
    private String phone;
    private String address;
    private String country;
    private String timezone;
}
