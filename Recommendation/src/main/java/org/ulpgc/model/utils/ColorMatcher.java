package org.ulpgc.model.utils;

import java.util.List;

public class ColorMatcher {

    public static List<String> matchTops(String pantColor) {

        if (pantColor == null) return List.of("negro");

        pantColor = pantColor.toLowerCase();

        if (pantColor.contains("azul"))
            return List.of("negro", "marron", "rojo");

        if (pantColor.contains("negro"))
            return List.of("gris", "blanco");

        if (pantColor.contains("rojo"))
            return List.of("negro", "gris");

        if (pantColor.contains("blanco"))
            return List.of("negro", "azul");

        return List.of("negro");
    }
}
