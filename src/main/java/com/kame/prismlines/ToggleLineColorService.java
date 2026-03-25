package com.kame.prismlines;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.JBColor;

import java.awt.Color;

public class ToggleLineColorService {

    private final Editor editor;
    private final int line;
    private final JBColor color;

    public ToggleLineColorService(Editor editor, int line) {
        JBColor blue = new JBColor(
                new Color(180,220,255,80), // modo claro
                new Color(80,120,200,80)   // modo oscuro
        );

        this.editor = editor;
        this.line = line;
        this.color = blue;
    }

    public ToggleLineColorService(Editor editor, int line, JBColor color) {
        this.editor = editor;
        this.line = line;
        this.color = color;
    }

    public void toggle() {
        Document document = editor.getDocument();
        int startOffset = document.getLineStartOffset(line);
        int endOffset = document.getLineEndOffset(line);
        MarkupModel markupModel = editor.getMarkupModel();
        RangeHighlighter existing = null;

        for (RangeHighlighter h : markupModel.getAllHighlighters()) {
            if (h.getStartOffset() == startOffset && h.getEndOffset() == endOffset) {
                existing = h;
                break;
            }
        }

        if (existing != null) {
            markupModel.removeHighlighter(existing); // quitar color
            return;
        }

        // Añadir color
        TextAttributes attributes = new TextAttributes();
        attributes.setBackgroundColor(color);

        RangeHighlighter highlighter = markupModel.addRangeHighlighter(
                startOffset,
                endOffset,
                HighlighterLayer.SELECTION - 1,
                attributes,
                HighlighterTargetArea.EXACT_RANGE
        );

        highlighter.setGutterIconRenderer(
                new GutterIcon(editor, line, color)
        );
    }
}