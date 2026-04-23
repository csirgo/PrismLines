package com.kame.prismlines.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorMouseEvent;
import com.intellij.openapi.editor.event.EditorMouseEventArea;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.kame.prismlines.service.ToggleLineColorService;
import org.jetbrains.annotations.NotNull;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Handles mouse interactions inside the editor gutter for PrismLines.
 *
 * <p>Tracks the last right-clicked line and allows toggling line colors
 * using middle mouse button interactions.</p>
 */
public class MouseListener implements EditorMouseListener {

    private static final MouseListener INSTANCE = new MouseListener();
    private final Map<Editor, Integer> lastRightClickLine = new WeakHashMap<>();

    /**
     * Returns the singleton instance of this listener.
     */
    public static MouseListener getInstance() {
        return INSTANCE;
    }

    private MouseListener() {}

    /**
     * Handles mouse press events inside the editor gutter.
     *
     * <p>Stores the last right-clicked line and triggers color toggle
     * when the middle mouse button is used.</p>
     *
     * @param e mouse event from the editor
     */
    @Override
    public void mousePressed(@NotNull EditorMouseEvent e) {
        Editor editor = e.getEditor();
        MouseEvent me = e.getMouseEvent();

        // Ignore clicks outside the gutter
        if (e.getArea() != EditorMouseEventArea.LINE_MARKERS_AREA
                && e.getArea() != EditorMouseEventArea.LINE_NUMBERS_AREA) {
            return;
        }

        int line = e.getLogicalPosition().line;

        // Right click save line
        if (SwingUtilities.isRightMouseButton(me)) {
            lastRightClickLine.put(editor, line);
        }

        // Mouse wheel toggle line
        if (SwingUtilities.isMiddleMouseButton(me)) {
            new ToggleLineColorService(editor, line).toggle();
        }
    }

    /**
     * Returns the last line clicked with the right mouse button in the given editor.
     *
     * @param editor the editor instance
     * @return last right-clicked line or null if none
     */
    public Integer getLastRightClickLine(Editor editor) {
        return lastRightClickLine.get(editor);
    }
}