package org.jabref.gui.icon;

import javafx.scene.paint.Color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertThrows;

public class IconThemeTests {

    IconTheme iconTheme = new IconTheme();

    @Test
    void getDefaultGroupColorsTest() {
        final Color color = Color.web("#8a8a8a");

        assertEquals(color, iconTheme.getDefaultGroupColor());
    }

    @Test
    void findIconTest() {
        String iconReturn = "Optional[org.jabref.gui.icon.InternalMaterialDesignIcon@";
        String icon = iconTheme.findIcon("MOLECULE", Color.web("#8a8a8a")).toString().substring(0, 56);
        String iconNull = iconTheme.findIcon("", Color.web("#8a8a8a")).toString();
        String empty = "Optional.empty";

        assertEquals(iconReturn, icon);
        assertEquals(empty, iconNull);
        assertThrows(NullPointerException.class, () -> {
            iconTheme.findIcon("MOLECULE", null);
        });
        assertThrows(NullPointerException.class, () -> {
            iconTheme.findIcon(null, null);
        });
    }

    @Test
    void getIconUrlTest() {
        String erroUrl = "/external/red.png";
        String acceptUrl = "/external/JabRef-icon-16.png";

        String urlInvalid = iconTheme.getIconUrl("teste").toString();
        urlInvalid = urlInvalid.substring(urlInvalid.length() - 17, urlInvalid.length());

        String urlValid = iconTheme.getIconUrl("jabrefIcon16").toString();
        urlValid = urlValid.substring(urlValid.length() - 28, urlValid.length());

        assertEquals(erroUrl, urlInvalid);
        assertEquals(acceptUrl, urlValid);
    }
}
