package com.kame.prismlines.service;

import com.intellij.openapi.util.Key;
import com.kame.prismlines.enums.PrismColor;

/**
 * Stores custom keys used by PrismLines metadata.
 */
public class PrismKeys {

    /**
     * Key used to associate a {@link PrismColor} with a line highlighter.
     */
    public static final Key<PrismColor> PRISM_COLOR_KEY =
            Key.create("PRISM_LINE_COLOR");

    private PrismKeys () {}
}