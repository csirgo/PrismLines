package com.kame.prismlines.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Root persistent state container for PrismLines.
 * <p>
 * This class is serialized by IntelliJ's persistence system and stored in
 * {@code prism-lines.xml}. It contains all persisted colored line entries.
 */
public class PrismLinesState {
    /**
     * List of all persisted colored lines across all files.
     */
    public List<PrismLineState> lines = new ArrayList<>();
}
