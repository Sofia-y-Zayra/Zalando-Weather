package org.ulpgc.dacd.model.util;

public class ColorUtils {
    public Color extractColor(String name) {

        name = name.toLowerCase();

        if (name.contains("black")) return Color.NEGRO;
        if (name.contains("white") || name.contains("ivory")) return Color.BLANCO;
        if (name.contains("blue") || name.contains("navy")) return Color.AZUL;
        if (name.contains("red") || name.contains("burgundy")) return Color.ROJO;
        if (name.contains("green") || name.contains("olive")) return Color.VERDE;
        if (name.contains("grey") || name.contains("gray") || name.contains("charcoal")) return Color.GRIS;
        if (name.contains("brown") || name.contains("chocolate") || name.contains("beige") || name.contains("sand")) return Color.MARRON;
        if (name.contains("pink")) return Color.ROSA;
        if (name.contains("yellow") || name.contains("mustard")) return Color.AMARILLO;
        if (name.contains("purple") || name.contains("lila")) return Color.MORADO;
        if (name.contains("orange")) return Color.NARANJA;

        return Color.DESCONOCIDO;
    }
}
