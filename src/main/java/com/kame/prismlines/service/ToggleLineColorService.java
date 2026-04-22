package com.kame.prismlines.service;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.kame.prismlines.gutter.GutterIcon;
import com.kame.prismlines.color.PrismColor;


public class ToggleLineColorService {

    private final Editor editor;
    private final int line;
    private final PrismColor prismColor;
    private final ToggleMode mode;

    public ToggleLineColorService(Editor editor, int line) {
        this(editor, line, PrismColor.AQUA, ToggleMode.TOGGLE);
    }

    public ToggleLineColorService(Editor editor, int line, PrismColor prismColor) {
        this(editor, line, prismColor, ToggleMode.REPLACE);
    }

    public ToggleLineColorService(Editor editor, int line, PrismColor prismColor, ToggleMode mode) {
        this.editor = editor;
        this.line = line;
        this.prismColor = prismColor;
        this.mode = mode;
    }

    public void toggle() {
        Document document = editor.getDocument();
        MarkupModel markupModel = editor.getMarkupModel();
        int startOffset = document.getLineStartOffset(line);
        int endOffset = document.getLineEndOffset(line);

        RangeHighlighter existing = null;

        for (RangeHighlighter rh : markupModel.getAllHighlighters()) {

            PrismColor existingColor =
                    rh.getUserData(PrismKeys.PRISM_COLOR_KEY);

            if (existingColor == null) {
                continue;
            }

            int highlighterLine =
                    document.getLineNumber(rh.getStartOffset());

            if (highlighterLine == line) {
                existing = rh;
                break;
            }
        }

        // ----------------------
        // WHEEL MODE (toggle puro)
        // ----------------------
        if (mode == ToggleMode.TOGGLE) {
            if (existing != null) {
                markupModel.removeHighlighter(existing);
                return;
            }
            addHighlighter(markupModel, startOffset, endOffset);
            return;
        }

        // ----------------------
        // MENU MODE (replace)
        // ----------------------
        if (existing != null) {
            PrismColor existingColor = existing.getUserData(PrismKeys.PRISM_COLOR_KEY);
            if (existingColor == prismColor) {
                markupModel.removeHighlighter(existing);
                return;
            }
            markupModel.removeHighlighter(existing);
        }
        addHighlighter(markupModel, startOffset, endOffset);
    }

    /**
     *
     * @param markupModel
     * @param startOffset
     * @param endOffset
     */
    private void addHighlighter(MarkupModel markupModel, int startOffset, int endOffset) {

        TextAttributes attributes = new TextAttributes();
        attributes.setBackgroundColor(prismColor.getBackgroundColor());

        RangeHighlighter highlighter = markupModel.addRangeHighlighter(
                startOffset,
                endOffset,
                HighlighterLayer.SELECTION - 1,
                attributes,
                HighlighterTargetArea.EXACT_RANGE
        );

        highlighter.setGutterIconRenderer(
                new GutterIcon(editor, line, prismColor)
        );
        highlighter.putUserData(PrismKeys.PRISM_COLOR_KEY, prismColor);
    }
}