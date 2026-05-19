import org.junit.Test;
import org.ulpgc.model.WeatherType;
import org.ulpgc.model.utils.WeatherParser;

import static org.junit.Assert.assertEquals;

public class WeatherParserTest {
    @Test
    public void shouldParseCloudsAsCloudy() {

        WeatherType weather =
                WeatherParser.parse("few clouds");

        assertEquals(
                WeatherType.NUBLADO,
                weather
        );
    }

    @Test
    public void shouldParseRainCorrectly() {

        WeatherType weather =
                WeatherParser.parse("light rain");

        assertEquals(
                WeatherType.LLUVIOSO,
                weather
        );
    }

    @Test
    public void shouldParseClearSkyAsSunny() {

        WeatherType weather =
                WeatherParser.parse("clear sky");

        assertEquals(
                WeatherType.SOLEADO,
                weather
        );
    }

    @Test
    public void shouldReturnUnknownWeatherForNull() {

        WeatherType weather =
                WeatherParser.parse(null);

        assertEquals(
                WeatherType.DESCONOCIDO,
                weather
        );
    }
}
