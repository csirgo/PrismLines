package com.kame.prismlines.color;

import com.intellij.ui.JBColor;

import java.awt.Color;


public enum PrismColor {

    RED(
            "Red",
            new JBColor(
                    new Color(255, 160, 170, 80),
                    new Color(220, 120, 130, 80)
            ),
            new Color(255, 160, 170)
    ),

    ORANGE(
            "Orange",
            new JBColor(
                    new Color(255, 185, 95, 80),
                    new Color(220, 150, 70, 80)
            ),
            new Color(255, 185, 95)
    ),

    YELLOW(
            "Yellow",
            new JBColor(
                    new Color(255, 225, 80, 80),
                    new Color(220, 190, 50, 80)
            ),
            new Color(255, 225, 80)
    ),

    GREEN(
            "Green",
            new JBColor(
                    new Color(180, 240, 80, 80),
                    new Color(140, 200, 50, 80)
            ),
            new Color(180, 240, 80)
    ),

    AQUA(
            "Aqua",
            new JBColor(
                    new Color(90, 225, 190, 80),
                    new Color(60, 185, 155, 80)
            ),
            new Color(90, 225, 190)
    ),

    INDIGO(
            "Indigo",
            new JBColor(
                    new Color(170, 180, 255, 80),
                    new Color(130, 140, 220, 80)
            ),
            new Color(170, 180, 255)
    ),

    VIOLET(
            "Violet",
            new JBColor(
                    new Color(235, 170, 255, 80),
                    new Color(190, 130, 220, 80)
            ),
            new Color(235, 170, 255)
    );

    private final String displayName;
    private final JBColor backgroundColor;
    private final Color iconColor;

    PrismColor(String displayName, JBColor backgroundColor, Color iconColor) {
        this.displayName = displayName;
        this.backgroundColor = backgroundColor;
        this.iconColor = iconColor;
    }

    public JBColor getBackgroundColor() {
        return backgroundColor;
    }

    public Color getIconColor() {
        return iconColor;
    }

    public String getDisplayName() {
        return displayName;
    }

}