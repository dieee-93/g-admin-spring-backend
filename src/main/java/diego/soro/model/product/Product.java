package diego.soro.model.product;

import diego.soro.model.category.Category;
import diego.soro.model.recipe.RecipeItem;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Table(name = "products")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = true)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(insertable = false, updatable = false)
    private LocalDateTime creationDate;
    @Column(insertable = false, updatable = false)
    private BigDecimal productionCost;
    @Column(insertable = false, updatable = false)
    private BigDecimal sellingPrice;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeItem> recipe;


}
