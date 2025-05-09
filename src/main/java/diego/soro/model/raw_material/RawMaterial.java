package diego.soro.model.raw_material;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import diego.soro.model.category.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class RawMaterial {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Long id;
    @Version
    protected Long version;
    @Column(nullable = false, unique = true)
    protected String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    protected Category category;
    @Column(nullable = false)
    protected String materialType;


}
