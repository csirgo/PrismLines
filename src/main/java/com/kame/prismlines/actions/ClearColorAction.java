package com.kame.prismlines.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.kame.prismlines.icon.PrismIconFactory;
import com.kame.prismlines.listener.MouseListener;
import com.kame.prismlines.service.ToggleLineColorService;
import org.jetbrains.annotations.NotNull;

public class ClearColorAction extends AnAction {

    public ClearColorAction() {
        Presentation p = getTemplatePresentation();
        p.setText("Clear");
        p.setIcon(PrismIconFactory.createOutlinePrismIcon());
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        Integer line = MouseListener.getInstance().getLastRightClickLine(editor);

        if (editor == null || line == null) return;

        new ToggleLineColorService(editor, line).clear();
    }

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
}