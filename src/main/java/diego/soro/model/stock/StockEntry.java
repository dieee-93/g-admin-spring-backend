package diego.soro.model.stock;

import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.recipe_item.RecipeItem;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stock_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StockEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "raw_material_id")
    private RawMaterial rawMaterial;

    @Column(nullable = false)
    private BigDecimal quantity; // Positiva para ingreso, negativa para egreso

    @Column(nullable = false)
    private BigDecimal cost;// Costo por unidad, ml gr)A


    @OneToMany(mappedBy = "stockEntry")
    private List<RecipeItem> recipe;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime timestamp;


}
