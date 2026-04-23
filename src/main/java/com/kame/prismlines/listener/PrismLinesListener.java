package com.kame.prismlines.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;

/**
 * Listener responsible for attaching and detaching the PrismLines mouse listener
 * to every editor instance created by the IDE.
 *
 * <p>This ensures that line interaction (coloring, toggling, gutter actions, etc.)
 * is only active while the editor exists and avoids memory leaks by properly
 * unregistering listeners when the editor is disposed.</p>
 */
public class PrismLinesListener implements EditorFactoryListener {

    /**
     * Called when a new editor instance is created by the IDE.
     * <p>Registers the PrismLines {@link MouseListener} to handle user interactions
     * such as right-click line selection and mouse-based toggling.</p>
     *
     * @param event event containing the newly created editor instance
     */
    @Override
    public void editorCreated(EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        editor.addEditorMouseListener(MouseListener.getInstance());
    }

    /**
     * Called when an editor instance is about to be disposed.
     * <p>Unregisters the PrismLines {@link MouseListener} to prevent memory leaks
     * and avoid handling events from a non-existent editor.</p>
     *
     * @param event event containing the editor being released
     */
    @Override
    public void editorReleased(EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        editor.removeEditorMouseListener(MouseListener.getInstance());
    }
}