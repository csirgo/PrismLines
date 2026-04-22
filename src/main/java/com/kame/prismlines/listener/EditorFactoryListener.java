package com.kame.prismlines.listener;

import com.intellij.openapi.editor.event.*;
import com.intellij.openapi.editor.*;

public class EditorFactoryListener implements com.intellij.openapi.editor.event.EditorFactoryListener {

    @Override
    public void editorCreated(EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        editor.addEditorMouseListener(MouseListener.getInstance());
    }

    @Override
    public void editorReleased(EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        editor.removeEditorMouseListener(MouseListener.getInstance());
    }
}