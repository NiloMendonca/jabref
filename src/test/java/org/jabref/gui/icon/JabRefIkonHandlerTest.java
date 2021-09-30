package org.jabref.gui.icon;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JabRefIkonHandlerTest {

    JabRefIkonHandler jabRefIkonHandler = new JabRefIkonHandler();

    @Test
    void supports() {
        assertEquals(false, jabRefIkonHandler.supports(null));
        assertEquals(false, jabRefIkonHandler.supports("jab"));
        assertEquals(true, jabRefIkonHandler.supports("jab-123"));
    }

    @Test
    void resolve() {
        String icon = jabRefIkonHandler.resolve("jab-copy").toString();

        assertEquals("COPY", icon);
        assertThrows(IllegalArgumentException.class, () -> {
            jabRefIkonHandler.resolve("test");
        });
    }

    @Test
    void getFontResource() {
        String path = jabRefIkonHandler.getFontResource().toString();
        assertEquals(true, path.endsWith("JabRefMaterialDesign.ttf"));
    }

    @Test
    void getFontResourceAsStream() {
        String font = jabRefIkonHandler.getFontResourceAsStream().toString();
        assertEquals("sun.nio.ch.ChannelInputStream@61e45f87", font);
    }

    @Test
    void getFontFamily() {
        assertEquals("JabRefMaterialDesign", jabRefIkonHandler.getFontFamily());
    }
}
