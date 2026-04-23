package com.kame.prismlines.service;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.kame.prismlines.enums.ToggleMode;
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
        if (isLineNotValid()) {
            return;
        }

        Project project = editor.getProject();
        if (project == null) return;

        String filePath = getFilePath();
        if (filePath == null) return;

        LineHighlighterService highlighterService =
                new LineHighlighterService(editor);

        PrismStateService stateService =
                PrismStateService.getInstance(project);

        RangeHighlighter existing =
                highlighterService.find(line);


        // ----------------------
        // WHEEL MODE (toggle puro)
        // ----------------------
        if (mode == ToggleMode.TOGGLE) {
            if (existing != null) {
                highlighterService.remove(line);
                stateService.removeLine(filePath, line);
            } else {
                highlighterService.add(line, prismColor);
                stateService.saveLine(
                        filePath,
                        line,
                        prismColor
                );
            }
            return;
        }

        // ----------------------
        // MENU MODE (replace)
        // ----------------------
        if (existing != null) {
            PrismColor existingColor =
                    existing.getUserData(
                            PrismKeys.PRISM_COLOR_KEY
                    );

            if (existingColor == prismColor) {
                highlighterService.remove(line);
                stateService.removeLine(filePath, line);
                return;
            }

            highlighterService.remove(line);
        }

        highlighterService.add(line, prismColor);
        stateService.saveLine(
                filePath,
                line,
                prismColor
        );
    }

    /**
     * Removes any existing color from the target line.
     */
    public void clear() {
        if (isLineNotValid()) {
            return;
        }

        Project project = editor.getProject();
        if (project == null) return;

        String filePath = getFilePath();
        if (filePath == null) return;

        new LineHighlighterService(editor).remove(line);

        PrismStateService.getInstance(project)
                .removeLine(filePath, line);
    }

    /**
     * Restores a previously persisted colored line when an editor is reopened.
     * <p>
     * This method only recreates the visual highlighter and gutter icon.
     * It does not persist anything again.
     */
    public void restore() {
        if (isLineNotValid()) {
            return;
        }

        new LineHighlighterService(editor)
                .add(line, prismColor);
    }

    /**
     * Retrieves the absolute path of the file associated with the current editor.
     *
     * @return the file path, or {@code null} if the editor is not linked
     * to a physical file.
     */
    private String getFilePath() {
        VirtualFile file = FileDocumentManager.getInstance()
                .getFile(editor.getDocument());

        return file != null ? file.getPath() : null;
    }

    /**
     * Checks whether the target line exists in the current document.
     *
     * @return {@code true} if the line index is not valid, {@code false} otherwise.
     */
    private boolean isLineNotValid() {
        Document document = editor.getDocument();
        return line < 0 || line >= document.getLineCount();

    }
}