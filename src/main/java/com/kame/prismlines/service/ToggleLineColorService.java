package com.kame.prismlines.service;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.kame.prismlines.enums.ToggleMode;
import com.kame.prismlines.ui.gutter.GutterIcon;
import com.kame.prismlines.enums.PrismColor;


/**
 * Service responsible for managing line colors in the editor.
 *
 * <p>It supports two behaviors:
 * <ul>
 *     <li>{@link ToggleMode#TOGGLE}: adds or removes a color from a line.</li>
 *     <li>{@link ToggleMode#REPLACE}: replaces an existing color with a new one.</li>
 * </ul>
 */
public class ToggleLineColorService {

    private final Editor editor;
    private final int line;
    private final PrismColor prismColor;
    private final ToggleMode mode;

    /**
     * Creates a toggle action using the default Aqua color.
     *
     * @param editor target editor
     * @param line target line number
     */
    public ToggleLineColorService(Editor editor, int line) {
        this(editor, line, PrismColor.AQUA, ToggleMode.TOGGLE);
    }

    /**
     * Creates a replacement action using the provided color.
     *
     * @param editor target editor
     * @param line target line number
     * @param prismColor color to apply
     */
    public ToggleLineColorService(Editor editor, int line, PrismColor prismColor) {
        this(editor, line, prismColor, ToggleMode.REPLACE);
    }

    /**
     * Creates a color action with full configuration.
     *
     * @param editor target editor
     * @param line target line number
     * @param prismColor color to apply
     * @param mode toggle behavior
     */
    public ToggleLineColorService(Editor editor, int line, PrismColor prismColor, ToggleMode mode) {
        this.editor = editor;
        this.line = line;
        this.prismColor = prismColor;
        this.mode = mode;
    }


    /**
     * Adds, removes or replaces a line color depending on the selected mode.
     */
    public void toggle() {
        Document document = editor.getDocument();
        MarkupModel markupModel = editor.getMarkupModel();
        int startOffset = document.getLineStartOffset(line);
        int endOffset = document.getLineEndOffset(line);

        RangeHighlighter existing = null;

        for (RangeHighlighter rh : markupModel.getAllHighlighters()) {

            PrismColor existingColor =
                    rh.getUserData(PrismKeys.PRISM_COLOR_KEY);

            int highlighterLine =
                    document.getLineNumber(rh.getStartOffset());

            if (existingColor != null && highlighterLine == line) {
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
     * Removes any existing color from the target line.
     */
    public void clear() {
        Document document = editor.getDocument();
        MarkupModel markupModel = editor.getMarkupModel();

        for (RangeHighlighter rh : markupModel.getAllHighlighters()) {

            int rhLine = document.getLineNumber(rh.getStartOffset());

            if (rhLine == line) {
                markupModel.removeHighlighter(rh);
            }
        }
    }

    /**
     * Creates a new highlighter and adds the gutter icon.
     *
     * @param markupModel editor markup model
     * @param startOffset line start offset
     * @param endOffset line end offset
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