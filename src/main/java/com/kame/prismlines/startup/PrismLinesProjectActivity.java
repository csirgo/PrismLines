package com.kame.prismlines.startup;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import com.kame.prismlines.listener.MouseListener;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.kame.prismlines.listener.PrismLinesListener.LISTENER_ADDED;

public class PrismLinesProjectActivity implements ProjectActivity {

    @Override
    public @Nullable Object execute(@NotNull Project project,
                                    @NotNull Continuation<? super Unit> continuation) {
        Editor[] editors = EditorFactory.getInstance().getAllEditors();
        for (Editor editor : editors) {
            if (project.equals((editor.getProject()))) {
                attachListeners(editor);
            }
        }
        return Unit.INSTANCE;
    }

    private void attachListeners(Editor editor) {
        if (Boolean.TRUE.equals(editor.getUserData(LISTENER_ADDED))) {
            return;
        }

        editor.addEditorMouseListener(MouseListener.getInstance());
        editor.putUserData(LISTENER_ADDED, true);
    }
}
