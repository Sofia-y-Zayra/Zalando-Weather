package org.ulpgc.dacd.model.util;

public class CategoryUtils {
    public boolean isClothing(String name) {

        name = name.toLowerCase();

        return name.contains("camiseta") ||
                name.contains("shirt") ||
                name.contains("top") ||
                name.contains("pantalon") ||
                name.contains("pants") ||
                name.contains("jeans") ||
                name.contains("chaqueta") ||
                name.contains("jacket") ||
                name.contains("coat");
    }

    public Category getCategory(String url, String name) {

        boolean isMujer = url.contains("mujer");
        name = name.toLowerCase();

        if (name.contains("camiseta") || name.contains("shirt") || name.contains("top")) {
            return isMujer ? Category.MUJER_CAMISETAS : Category.HOMBRE_CAMISETAS;
        }

        if (name.contains("pantalon") || name.contains("pants") || name.contains("jeans")) {
            return isMujer ? Category.MUJER_PANTALONES : Category.HOMBRE_PANTALONES;
        }

        if (name.contains("chaqueta") || name.contains("jacket") || name.contains("coat")) {
            return isMujer ? Category.MUJER_CHAQUETAS : Category.HOMBRE_CHAQUETAS;
        }

        return Category.OTRO;
    }
}
