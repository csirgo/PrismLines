package com.kame.prismlines.service;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.kame.prismlines.enums.PrismColor;
import com.kame.prismlines.ui.gutter.GutterIcon;

/**
 * Service responsible for managing visual line highlighters inside the editor.
 * <p>
 * This class encapsulates all logic related to:
 * <ul>
 *     <li>Creating colored line highlights</li>
 *     <li>Searching existing highlights</li>
 *     <li>Removing highlights</li>
 * </ul>
 * It does not handle persistence; it only operates on the editor UI layer.
 */
public class LineHighlighterService {

    private final Editor editor;
    private final Document document;
    private final MarkupModel markupModel;

    /**
     * Creates a new service bound to a specific editor instance.
     *
     * @param editor the editor where highlighters will be managed
     */
    public LineHighlighterService(Editor editor) {
        this.editor = editor;
        this.document = editor.getDocument();
        this.markupModel = editor.getMarkupModel();
    }

    /**
     * Finds an existing highlighter associated with the given logical line.
     *
     * @param line the line index to search for
     * @return the matching RangeHighlighter if found, otherwise null
     */
    public RangeHighlighter find(int line) {
        for (RangeHighlighter rh : markupModel.getAllHighlighters()) {
            PrismColor color = rh.getUserData(PrismKeys.PRISM_COLOR_KEY);
            int rhLine =  document.getLineNumber(rh.getStartOffset());

            if (color != null && rhLine == line) {
                return rh;
            }
        }
        return null;
    }

    /**
     * Adds a colored background highlighter and gutter icon to a specific line.
     *
     * @param line the line to highlight
     * @param prismColor the color configuration to apply
     */
    public void add(int line, PrismColor prismColor) {
        int start = document.getLineStartOffset(line);
        int end = document.getLineEndOffset(line);

        TextAttributes attributes = new TextAttributes();
        attributes.setBackgroundColor(
                prismColor.getBackgroundColor()
        );

        RangeHighlighter highlighter =
                markupModel.addRangeHighlighter(
                        start,
                        end,
                        HighlighterLayer.SELECTION - 1,
                        attributes,
                        HighlighterTargetArea.EXACT_RANGE
                );

        highlighter.setGutterIconRenderer(
                new GutterIcon(editor, line, prismColor)
        );

        highlighter.putUserData(
                PrismKeys.PRISM_COLOR_KEY,
                prismColor
        );
    }

    /**
     * Removes any existing highlighter associated with the given line.
     *
     * @param line the line whose highlight should be removed
     */
    public void remove(int line) {
        RangeHighlighter existing = find(line);

        if (existing != null) {
            markupModel.removeHighlighter(existing);
        }
    }
}
