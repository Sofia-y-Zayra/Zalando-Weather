import org.junit.Test;
import org.ulpgc.control.utils.ColorType;
import org.ulpgc.control.utils.ColorParser;

import static org.junit.Assert.assertEquals;

public class ColorParserTest {
    @Test
    public void shouldParseBlackColor() {

        ColorType color =
                ColorParser.parse("NEGRO");

        assertEquals(
                ColorType.NEGRO,
                color
        );
    }
    @Test
    public void shouldReturnUnknownColor() {

        ColorType color =
                ColorParser.parse("turquesa");

        assertEquals(
                ColorType.DESCONOCIDO,
                color
        );
    }

}
