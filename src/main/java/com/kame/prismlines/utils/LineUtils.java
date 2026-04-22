package com.kame.prismlines.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;

import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class LineUtils {

    public static int getLineFromEvent(AnActionEvent e, Editor editor) {

        Document document = editor.getDocument();
        InputEvent inputEvent = e.getInputEvent();

        if (inputEvent instanceof MouseEvent mouseEvent) {

            Point point = mouseEvent.getPoint();
            Component source = mouseEvent.getComponent();
            Component editorComponent = editor.getContentComponent();

            if (source != editorComponent) {
                point = SwingUtilities.convertPoint(source, point, editorComponent);
            }

            LogicalPosition pos = editor.xyToLogicalPosition(point);
            int line = pos.line;

            // Asegurarse de que la línea esté dentro del rango
            int lineCount = editor.getDocument().getLineCount();
            if (line < 0) line = 0;
            if (line >= lineCount) line = lineCount - 1;

            return line;
        }

        int offset = editor.getCaretModel().getOffset();
        return document.getLineNumber(offset);
    }
}
