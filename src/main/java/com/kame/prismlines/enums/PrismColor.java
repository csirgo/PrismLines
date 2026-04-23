package com.kame.prismlines.enums;

import com.intellij.ui.JBColor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.Color;

/**
 * Class PrismColor
 * Contains all the colors and their respective names used to color lines and icons
 */
@Getter
@AllArgsConstructor
public enum PrismColor {

    RED(
            "Red",
            new JBColor(
                    new Color(255, 160, 170, 80),
                    new Color(220, 120, 130, 80)
            ),
            new JBColor(new Color(255, 160, 170), new Color(255, 160, 170))
    ),

    ORANGE(
            "Orange",
            new JBColor(
                    new Color(255, 185, 95, 80),
                    new Color(220, 150, 70, 80)
            ),
            new JBColor(new Color(255, 185, 95), new Color(255, 185, 95))
    ),

    YELLOW(
            "Yellow",
            new JBColor(
                    new Color(255, 225, 80, 80),
                    new Color(220, 190, 50, 80)
            ),
            new JBColor(new Color(255, 225, 80), new Color(255, 225, 80))
    ),

    GREEN(
            "Green",
            new JBColor(
                    new Color(180, 240, 80, 80),
                    new Color(140, 200, 50, 80)
            ),
            new JBColor(new Color(180, 240, 80), new Color(180, 240, 80))
    ),

    AQUA(
            "Aqua",
            new JBColor(
                    new Color(82, 235, 197, 80),
                    new Color(55, 190, 160, 80)
            ),
            new JBColor(
                    new Color(82, 235, 197),
                    new Color(82, 235, 197)
            )
    ),

    INDIGO(
            "Indigo",
            new JBColor(
                    new Color(165, 190, 255, 80),
                    new Color(125, 145, 220, 80)
            ),
            new JBColor(
                    new Color(165, 190, 255),
                    new Color(165, 190, 255)
            )
    ),

    VIOLET(
            "Violet",
            new JBColor(
                    new Color(220, 175, 255, 80),
                    new Color(180, 130, 220, 80)
            ),
            new JBColor(
                    new Color(220, 175, 255),
                    new Color(220, 175, 255)
            )
    );

    private final String displayName;
    private final JBColor backgroundColor;
    private final Color iconColor;
}