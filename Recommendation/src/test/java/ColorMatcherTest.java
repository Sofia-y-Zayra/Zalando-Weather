import org.junit.Test;
import org.ulpgc.model.ColorType;
import org.ulpgc.model.WeatherType;
import org.ulpgc.control.ColorMatcher;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ColorMatcherTest {
    @Test
    public void shouldReturnBrightColorsForSunnyWeather() {

        List<ColorType> colors =
                ColorMatcher.colorsByWeather(
                        WeatherType.SOLEADO
                );

        assertTrue(colors.contains(ColorType.BLANCO));
        assertTrue(colors.contains(ColorType.AZUL));
        assertTrue(colors.contains(ColorType.VERDE));
    }
    @Test
    public void shouldReturnDarkColorsForCloudyWeather() {

        List<ColorType> colors =
                ColorMatcher.colorsByWeather(
                        WeatherType.NUBLADO
                );

        assertTrue(colors.contains(ColorType.NEGRO));
        assertTrue(colors.contains(ColorType.GRIS));
        assertTrue(colors.contains(ColorType.MARRON));
    }
    @Test
    public void shouldReturnRainColorsForRainyWeather() {

        List<ColorType> colors =
                ColorMatcher.colorsByWeather(
                        WeatherType.LLUVIOSO
                );

        assertTrue(colors.contains(ColorType.NEGRO));
        assertTrue(colors.contains(ColorType.GRIS));
    }
    @Test
    public void shouldReturnDefaultColorsForUnknownWeather() {

        List<ColorType> colors =
                ColorMatcher.colorsByWeather(
                        WeatherType.DESCONOCIDO
                );

        assertTrue(colors.contains(ColorType.BEIGE));
        assertTrue(colors.contains(ColorType.GRIS));
    }

    @Test
    public void shouldMatchBlackPantsWithNeutralTops() {

        List<ColorType> colors =
                ColorMatcher.matchTops(ColorType.NEGRO);

        assertTrue(colors.contains(ColorType.BLANCO));
        assertTrue(colors.contains(ColorType.GRIS));
    }

    @Test
    public void shouldMatchBluePantsWithWarmColors() {

        List<ColorType> colors =
                ColorMatcher.matchTops(ColorType.AZUL);

        assertTrue(colors.contains(ColorType.ROJO));
        assertTrue(colors.contains(ColorType.MARRON));
    }

    @Test
    public void shouldMatchWhitePantsCorrectly() {

        List<ColorType> colors =
                ColorMatcher.matchTops(ColorType.BLANCO);

        assertTrue(colors.contains(ColorType.AZUL));
        assertTrue(colors.contains(ColorType.NEGRO));
    }
}
