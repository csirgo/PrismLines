package com.kame.prismlines.ui.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.ui.JBColor;
import com.kame.prismlines.ui.icon.PrismIconFactory;
import com.kame.prismlines.listener.MouseListener;
import com.kame.prismlines.service.ToggleLineColorService;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;

/**
 * Action that removes any applied color from the selected line.
 *
 * <p>This action is available in the gutter context menu and is only enabled
 * when the selected line already contains a PrismLines highlighter.</p>
 */
public class ClearColorAction extends AnAction {

    /**
     * Creates the action and configures its presentation.
     */
    public ClearColorAction() {
        JBColor gray = new JBColor(new Color(180, 180, 180, 255), new Color(100, 100, 100, 255));

        Presentation p = getTemplatePresentation();
        p.setText("Clear");
        p.setIcon(PrismIconFactory.createOutlineIcon(gray));
    }

    /**
     * Removes the color from the last right-clicked line.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        Integer line = MouseListener.getInstance().getLastRightClickLine(editor);

        if (editor == null || line == null) return;

        new ToggleLineColorService(editor, line).clear();
    }

    /**
     * Enables the action only when the selected line already has a color.
     *
     * @param e action event
     */
    @Override
    public void update(@NotNull AnActionEvent e) {

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        Integer line = MouseListener.getInstance().getLastRightClickLine(editor);

        boolean enabled = false;

        if (editor != null && line != null) {
            enabled = hasColor(editor, line);
        }

        e.getPresentation().setEnabled(enabled);
    }

    /**
     * Checks whether the given line currently has an active color highlighter.
     *
     * @param editor target editor
     * @param line target line number
     * @return true if the line is colored
     */
    private boolean hasColor(Editor editor, int line) {
        Document document = editor.getDocument();
        MarkupModel markupModel = editor.getMarkupModel();

        int start = document.getLineStartOffset(line);
        int end = document.getLineEndOffset(line);

        for (RangeHighlighter rh : markupModel.getAllHighlighters()) {
            if (rh.getStartOffset() == start && rh.getEndOffset() == end) {
                return true;
            }
        }
        return false;
    }

    /**
     * Specifies that action updates should run on the UI thread.
     *
     * @return EDT update thread
     */
    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }
}