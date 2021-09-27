package org.jabref.gui.icon;

import javafx.scene.paint.Color;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IconThemeTests {

    @Test
    void getDefaultGroupColors() {
        IconTheme iconTheme = new IconTheme();
        final Color color = Color.web("#8a8a8a");

        assertEquals(color, iconTheme.getDefaultGroupColor());
    }
}
