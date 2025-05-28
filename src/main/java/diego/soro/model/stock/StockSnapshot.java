package diego.soro.model.stock;

import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.recipe_item.RecipeItem;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stock_snapshots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StockSnapshot {

    // Usamos el mismo identificador de la materia prima,
    // ya que cada snapshot corresponde a una única materia prima.
    @Id
    private Long rawMaterialId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "raw_material_id", nullable = false)
    private RawMaterial rawMaterial;

    // Costo total actual disponible (suma de StockEntry)
    @Column(nullable = false)
    private BigDecimal cost;
    // Cantidad total actual disponible (suma de StockEntry)
    @Column(nullable = false)
    private BigDecimal quantity;

    // Momento en que se actualizó el snapshot por última vez
    @Column(nullable = true, updatable = false)
    private String measurementUnit;
    @OneToMany(mappedBy = "stockSnapshot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeItem> recipe;
}