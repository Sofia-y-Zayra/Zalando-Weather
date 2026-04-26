package org.ulpgc.dacd.utils;

import org.junit.jupiter.api.Test;
import org.ulpgc.dacd.model.util.Color;
import org.ulpgc.dacd.model.util.ColorUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorUtilsTest {
    @Test
    public void testExtractColor() {

        ColorUtils utils = new ColorUtils();

        assertEquals(Color.NEGRO, utils.extractColor("Camiseta black"));
        assertEquals(Color.BLANCO, utils.extractColor("Top white"));
        assertEquals(Color.MARRON, utils.extractColor("Jersey chocolate"));
        assertEquals(Color.NARANJA, utils.extractColor("orange top"));
    }
}
