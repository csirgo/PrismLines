package com.kame.prismlines.listener;

import com.github.weisj.jsvg.L;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.EditorFactoryEvent;
import com.intellij.openapi.editor.event.EditorFactoryListener;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.kame.prismlines.enums.PrismColor;
import com.kame.prismlines.persistence.PrismLineState;
import com.kame.prismlines.service.PrismStateService;
import com.kame.prismlines.service.ToggleLineColorService;

/**
 * Listener responsible for attaching and detaching the PrismLines mouse listener
 * to every editor instance created by the IDE.
 *
 * <p>This ensures that line interaction (coloring, toggling, gutter actions, etc.)
 * is only active while the editor exists and avoids memory leaks by properly
 * unregistering listeners when the editor is disposed.</p>
 */
public class PrismLinesListener implements EditorFactoryListener {

    public static final Key<Boolean> LISTENER_ADDED = Key.create("PRISM_LISTENER");

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

        VirtualFile file = FileDocumentManager.getInstance()
                .getFile(editor.getDocument());
        if (file == null) return;

        Project project = editor.getProject();
        if (project == null) return;

        PrismStateService state =  PrismStateService.getInstance(project);
        assert state.getState() != null;

        for (PrismLineState item : state.getLines(file.getPath())) {

            if (!file.getPath().equals(item.filePath)) continue;

            int line = item.line;
            PrismColor color = PrismColor.valueOf(item.colorName);

            new ToggleLineColorService(editor, line, color).restore();
        }

        editor.addEditorMouseListener(MouseListener.getInstance());
        editor.putUserData(LISTENER_ADDED, true);
    }
}