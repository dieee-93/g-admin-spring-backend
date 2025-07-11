package diego.soro.model2.core.Branch;
import diego.soro.model2.core.BaseEntity.BaseEntity;
import diego.soro.model2.core.Company.Company;
import diego.soro.model2.core.User.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
public class Branch extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;

    private String address;
    private String phone;
    private String email;

    @ManyToOne(optional = false)
    private Company company;

    @Column(nullable = false)
    private Boolean isMain = false;

    @ManyToMany(mappedBy = "branches")
    private Set<User> users = new HashSet<>();
}