package org.ulpgc.utils;

import org.ulpgc.model.ColorType;


public class ColorParser {

    public static ColorType parse(String value) {

        value = value.toLowerCase();

        if (value.contains("negro"))
            return ColorType.NEGRO;

        if (value.contains("blanco"))
            return ColorType.BLANCO;

        if (value.contains("azul"))
            return ColorType.AZUL;

        if (value.contains("rojo"))
            return ColorType.ROJO;

        if (value.contains("verde"))
            return ColorType.VERDE;

        if (value.contains("gris"))
            return ColorType.GRIS;

        if (value.contains("marron"))
            return ColorType.MARRON;

        if (value.contains("rosa"))
            return ColorType.ROSA;

        if (value.contains("amarillo"))
            return ColorType.AMARILLO;

        if (value.contains("morado"))
            return ColorType.MORADO;

        if (value.contains("naranja"))
            return ColorType.NARANJA;
        return ColorType.DESCONOCIDO;
    }
}

