package com.kame.prismlines.persistence;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Represents a single persisted colored line in a file.
 * <p>
 * Each entry stores the file path, line number, and the selected color name.
 */
@AllArgsConstructor
@NoArgsConstructor
public class PrismLineState {
    /**
     * Absolute path of the file containing the colored line.
     */
    public String filePath;

    /**
     * Zero-based line index in the file.
     */
    public int line;

    /**
     * Name of the {@code PrismColor} used for this line.
     */
    public String colorName;
}