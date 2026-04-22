package com.kame.prismlines.listener;

import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.event.*;
import com.kame.prismlines.service.ToggleLineColorService;
import org.jetbrains.annotations.NotNull;

import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.WeakHashMap;

public class MouseListener implements EditorMouseListener {

    private static final MouseListener INSTANCE = new MouseListener();
    private final Map<Editor, Integer> lastRightClickLine = new WeakHashMap<>();

    public static MouseListener getInstance() {
        return INSTANCE;
    }

    private MouseListener() {}

    @Override
    public void mousePressed(@NotNull EditorMouseEvent e) {
        Editor editor = e.getEditor();
        MouseEvent me = e.getMouseEvent();

        // Gutter only
        if (e.getArea() != EditorMouseEventArea.LINE_MARKERS_AREA
                && e.getArea() != EditorMouseEventArea.LINE_NUMBERS_AREA) {
            return;
        }

        int line = e.getLogicalPosition().line;

        // Right click save line
        if (SwingUtilities.isRightMouseButton(me)) {
            lastRightClickLine.put(editor, line);
        }

        // Mouse wheel
        if (SwingUtilities.isMiddleMouseButton(me)) {
            new ToggleLineColorService(editor, line).toggle();
        }
    }

    public Integer getLastRightClickLine(Editor editor) {
        return lastRightClickLine.get(editor);
    }
}