package diego.soro.model.recipe_item;

import diego.soro.model.product.Product;
import diego.soro.model.raw_material.RawMaterial;
import diego.soro.model.stock.StockEntry;
import diego.soro.model.stock.StockSnapshot;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "recipe_items")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RecipeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "stock_entry_id")
    private StockEntry stockEntry;
    @ManyToOne
    @JoinColumn(name = "stock_snapshot_id")
    private StockSnapshot stockSnapshot;

    @Column(nullable = true)
    private BigDecimal quantity;
    @Column(nullable = true)
    private BigDecimal cost;
    @Column(nullable = true)
    private String measurementUnit;
}
