package org.ulpgc.model;

import java.util.List;

public class ColorMatcher {
    public static List<ColorType> colorsByWeather(
            WeatherType weather
    ) {

        return switch (weather) {

            case SOLEADO -> List.of(
                    ColorType.AZUL,
                    ColorType.BLANCO,
                    ColorType.VERDE,
                    ColorType.AMARILLO,
                    ColorType.ROSA
            );

            case NUBLADO -> List.of(
                    ColorType.GRIS,
                    ColorType.ROJO,
                    ColorType.NEGRO,
                    ColorType.MARRON,
                    ColorType.MORADO
            );

            case LLUVIOSO -> List.of(
                    ColorType.NEGRO,
                    ColorType.GRIS,
                    ColorType.AZUL,
                    ColorType.MORADO,
                    ColorType.ROJO
            );

            case DESCONOCIDO -> List.of(
                    ColorType.BLANCO,
                    ColorType.GRIS
            );
        };
    }

    public static List<ColorType> matchTops(ColorType pantColorType) {

        if (pantColorType == null) {
            return List.of(ColorType.NEGRO);
        }

        return switch (pantColorType) {

            case AZUL -> List.of(
                    ColorType.NEGRO,
                    ColorType.MARRON,
                    ColorType.ROJO,
                    ColorType.GRIS
            );

            case NEGRO -> List.of(
                    ColorType.GRIS,
                    ColorType.BLANCO,
                    ColorType.AMARILLO,
                    ColorType.VERDE,
                    ColorType.ROJO,
                    ColorType.MORADO,
                    ColorType.ROSA
            );

            case ROJO -> List.of(
                    ColorType.NEGRO,
                    ColorType.GRIS,
                    ColorType.BLANCO
            );

            case BLANCO -> List.of(
                    ColorType.NEGRO,
                    ColorType.AZUL,
                    ColorType.VERDE,
                    ColorType.ROJO,
                    ColorType.MORADO,
                    ColorType.ROSA
            );

            default -> List.of(ColorType.NEGRO);
        };
    }
}