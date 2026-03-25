package com.kame.prismlines;

import com.intellij.openapi.editor.event.*;
import com.intellij.openapi.editor.*;

public class EditorFactoryListener implements com.intellij.openapi.editor.event.EditorFactoryListener {

    @Override
    public void editorCreated(EditorFactoryEvent event) {

        Editor editor = event.getEditor();

        editor.addEditorMouseListener(new WheelMouseListener());
    }
}