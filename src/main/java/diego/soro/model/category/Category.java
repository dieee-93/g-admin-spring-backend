    package diego.soro.model.category;

    import diego.soro.model.product.Product;
    import diego.soro.model.raw_material.RawMaterial;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import lombok.EqualsAndHashCode;
    import lombok.Getter;
    import lombok.Setter;
    import lombok.ToString;

    import java.util.ArrayList;
    import java.util.List;


    @Entity
    @Table(name = "categories")
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode(of = "id")

    public class Category {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Column(nullable = false, unique = true)
        private String name;

        // Jerarquía
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_category_id")
        private Category parent;

        @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Category> children = new ArrayList<>();

        // Relaciones específicas por tipo
        @OneToMany(mappedBy = "category")
        private List<Product> products = new ArrayList<>();

        @OneToMany(mappedBy = "category")
        private List<RawMaterial> rawMaterials = new ArrayList<>();
    }
