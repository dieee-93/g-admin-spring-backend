package diego.soro.model.stock;

import diego.soro.model.raw_material.RawMaterial;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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

    // Cantidad total actual disponible (suma de StockEntry)
    @Column(nullable = false)
    private Double totalQuantity;

    // Momento en que se actualizó el snapshot por última vez
    @Column(nullable = true, updatable = false)
    private String measurementUnit;
}