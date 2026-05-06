package org.ulpgc.model.utils;

import java.util.List;

public class ColorMatcher {

    public static List<String> colorsByWeather(String desc) {

        if (desc == null) return List.of("negro");

        desc = desc.toLowerCase();

        if (desc.contains("sun") || desc.contains("soleado") || desc.contains("clear")) {
            return List.of("azul", "blanco", "verde");
        }

        if (desc.contains("cloud") || desc.contains("nublado")) {
            return List.of("gris", "negro", "marron");
        }

        if (desc.contains("rain") || desc.contains("lluvia")) {
            return List.of("negro", "gris", "azul");
        }

        return List.of("negro");
    }

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