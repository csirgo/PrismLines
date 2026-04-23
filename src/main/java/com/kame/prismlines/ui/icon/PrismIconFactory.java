package com.kame.prismlines.ui.icon;

import javax.swing.Icon;
import java.awt.Color;

/**
 * Factory class responsible for creating PrismIcons with predefined styles.
 *
 * <p>Provides a simple API to generate filled or outline icons without
 * exposing the internal {@link PrismIcon} construction logic.</p>
 */
public class PrismIconFactory {

    private PrismIconFactory() {}

    /**
     * Creates a filled Prism icon.
     *
     * @param color icon color
     * @return filled icon instance
     */
    public static Icon createFilledIcon(Color color) {
        return new PrismIcon(color, IconStyle.FILLED);
    }

    /**
     * Creates an outline Prism icon.
     *
     * @param color icon color
     * @return outline icon instance
     */
    public static Icon createOutlineIcon(Color color) {
        return new PrismIcon(color, IconStyle.OUTLINE);
    }
}
