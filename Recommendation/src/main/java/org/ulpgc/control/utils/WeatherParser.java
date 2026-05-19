package org.ulpgc.control.utils;

public class WeatherParser {

    public static WeatherType parse(String desc) {

        if (desc == null) {
            return WeatherType.DESCONOCIDO;
        }

        desc = desc.toLowerCase();

        if (desc.contains("sun")
                || desc.contains("soleado")
                || desc.contains("clear")
                || desc.contains("cielo claro"))
        {

            return WeatherType.SOLEADO;
        }

        if (desc.contains("cloud")
                || desc.contains("nube")
                || desc.contains("nublado")
                || desc.contains("overcast")
                || desc.contains("nuboso"))
        {

            return WeatherType.NUBLADO;
        }

        if (desc.contains("rain")
                || desc.contains("lluvia")
                || desc.contains("drizzle")) {

            return WeatherType.LLUVIOSO;
        }

        return WeatherType.DESCONOCIDO;
    }
}