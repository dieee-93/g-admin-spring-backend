package diego.soro;
import diego.soro.model.category.Category;
import diego.soro.model.category.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataSeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Si no existe categoría con ID 0, la creo
        if (categoryRepository.count() == 0) {
            Category category = new Category();
            category.setName("ROOT");
            categoryRepository.save(category);
            System.out.println("Categoría inicial creada.");
        }
    }
}