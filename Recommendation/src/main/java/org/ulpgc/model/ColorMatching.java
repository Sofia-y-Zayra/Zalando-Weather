package org.ulpgc.model;

import org.ulpgc.control.utils.ColorType;
import org.ulpgc.control.utils.WeatherType;

import java.util.Arrays;
import java.util.List;

public class ColorMatching {

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
                    ColorType.MORADO,
                    ColorType.AZUL
            );

            case LLUVIOSO -> List.of(
                    ColorType.NEGRO,
                    ColorType.GRIS,
                    ColorType.AZUL,
                    ColorType.MORADO,
                    ColorType.ROJO
            );


            case DESCONOCIDO -> Arrays.asList(
                    ColorType.values()
            );
        };
    }


    public static List<ColorType> matchBottoms(
            ColorType topColor
    ) {

        if (topColor == null) {

            return Arrays.asList(
                    ColorType.values()
            );
        }

        return switch (topColor) {

            case AZUL -> List.of(
                    ColorType.NEGRO,
                    ColorType.GRIS,
                    ColorType.BLANCO,
                    ColorType.MARRON
            );

            case NEGRO -> List.of(
                    ColorType.values()
            );

            case BLANCO -> List.of(
                    ColorType.values()
            );

            case ROJO -> List.of(
                    ColorType.NEGRO,
                    ColorType.GRIS,
                    ColorType.BLANCO
            );

            default -> Arrays.asList(
                    ColorType.values()
            );
        };
    }
}