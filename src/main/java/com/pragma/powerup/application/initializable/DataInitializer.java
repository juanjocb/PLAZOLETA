package com.pragma.powerup.application.initializable;

import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.usecase.CategoryUseCase;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final CategoryUseCase categoryUseCase;

    public DataInitializer(CategoryUseCase categoryUseCase) {
        this.categoryUseCase = categoryUseCase;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCategories();
    }

    private void createCategories() {

        Category category1 = new Category(null, "Entradas", "Platos pequeños servidos al principio de una comida");
        Category category2 = new Category(null, "Platos Principales", "Platos principales que constituyen la mayor parte de una comida");
        Category category3 = new Category(null, "Postres", "Platos dulces que se sirven al final de una comida");
        Category category4 = new Category(null, "Bebidas", "Variedad de bebidas para acompañar una comida");
        Category category5 = new Category(null, "Aperitivos", "Comidas ligeras y pequeñas para abrir el apetito antes de la comida principal");

        categoryUseCase.saveCategory(category1);
        categoryUseCase.saveCategory(category2);
        categoryUseCase.saveCategory(category3);
        categoryUseCase.saveCategory(category4);
        categoryUseCase.saveCategory(category5);
    }
}
