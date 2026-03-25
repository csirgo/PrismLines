package com.kame.prismlines;

import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.event.*;
import java.awt.event.MouseEvent;

public class WheelMouseListener implements EditorMouseListener {

    @Override
    public void mouseClicked(EditorMouseEvent event) {

        if (event.getArea() != EditorMouseEventArea.LINE_MARKERS_AREA) return;

        MouseEvent mouse = event.getMouseEvent();

        if (mouse.getButton() != java.awt.event.MouseEvent.BUTTON2) return;

        Editor editor = event.getEditor();

        LogicalPosition pos = editor.xyToLogicalPosition(mouse.getPoint());
        int line = pos.line;

        new ToggleLineColorService(editor, line).toggle();
    }
}