package com.kame.prismlines;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class ColorLineMenuAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        Project project = e.getProject();

        if (editor == null || project == null) return;

        Document document = editor.getDocument();

        int line;

        InputEvent inputEvent = e.getInputEvent();

        if (inputEvent instanceof MouseEvent mouseEvent) {

            Point point = mouseEvent.getPoint();

            Component source = mouseEvent.getComponent();
            Component editorComponent = editor.getContentComponent();

            if (source != editorComponent) {
                point = SwingUtilities.convertPoint(source, point, editorComponent);
            }

            LogicalPosition pos = editor.xyToLogicalPosition(point);
            line = pos.line;

        } else {
            int offset = editor.getCaretModel().getOffset();
            line = document.getLineNumber(offset);
        }

            new ToggleLineColorService(editor, line).toggle();

    }
}
