package org.ulpgc.dacd.utils;

import org.junit.jupiter.api.Test;
import org.ulpgc.dacd.model.util.Category;
import org.ulpgc.dacd.model.util.CategoryUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryUtilsTest {

    CategoryUtils utils = new CategoryUtils();

    @Test
    public void testMujerCamiseta() {
        Category result = utils.getCategory(
                "https://zalando.es/mujer",
                "Camiseta deportiva"
        );
        assertEquals(Category.MUJER_CAMISETAS, result);
    }

    @Test
    public void testHombreJeans() {
        Category result = utils.getCategory(
                "https://zalando.es/hombre",
                "Jeans slim fit"
        );
        assertEquals(Category.HOMBRE_PANTALONES, result);
    }

    @Test
    public void testMujerChaqueta() {
        Category result = utils.getCategory(
                "https://zalando.es/mujer",
                "Chaqueta invierno"
        );
        assertEquals(Category.MUJER_CHAQUETAS, result);
    }

    @Test
    public void testCategoriaDesconocida() {
        Category result = utils.getCategory(
                "https://zalando.es/hombre",
                "Producto raro"
        );
        assertEquals(Category.OTRO, result);
    }
    @Test
    public void testEnglish() {
        Category result = utils.getCategory(
                "https://zalando.es/hombre",
                "T-shirt basic"
        );
        assertEquals(Category.HOMBRE_CAMISETAS, result);
    }
    @Test
    public void testHombrePantalon() {
        Category result = utils.getCategory(
                "https://zalando.es/hombre",
                "pantalon slim fit"
        );
        assertEquals(Category.HOMBRE_PANTALONES, result);
    }
}
