package diego.soro.model.raw_material;


import diego.soro.model.category.Category;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;

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

    @PrePersist
    public void setDefaultCategory() {
        // Solo si la categoría no ha sido establecida
        if (this.category == null) {
            // Crea una nueva instancia de Category y le asigna el ID 1
            // ¡Importante!: Asegúrate de que esta categoría con ID 1 exista en tu base de datos
            // o se creará un error de clave foránea si no existe.
            Category defaultCategory = new Category();
            defaultCategory.setId(1L); // Asignamos el ID 1

            this.category = defaultCategory;
        }
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Long id;
    @Version
    protected Long version;
    @Column(nullable = false, unique = true)
    @NotBlank
    protected String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    protected Category category;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected RawMaterialType rawMaterialType;


}

