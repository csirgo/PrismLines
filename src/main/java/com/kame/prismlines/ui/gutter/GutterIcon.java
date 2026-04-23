package com.kame.prismlines.ui.gutter;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.actionSystem.AnAction;
import com.kame.prismlines.enums.PrismColor;
import com.kame.prismlines.ui.icon.PrismIconFactory;
import com.kame.prismlines.service.ToggleLineColorService;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * Gutter icon renderer used by PrismLines to display line color markers.
 *
 * <p>Each icon represents a colored line in the editor and provides
 * interactions such as toggling the color or opening the context menu.</p>
 */
public class GutterIcon extends GutterIconRenderer {

    private final Editor editor;
    private final int line;
    private final PrismColor prismColor;
    private final Icon icon;

    /**
     * Creates a gutter icon for a specific line and color.
     *
     * @param editor target editor
     * @param line line number associated with this icon
     * @param prismColor color represented by the icon
     */
    public GutterIcon(Editor editor, int line, PrismColor prismColor) {
        this.editor = editor;
        this.line = line;
        this.prismColor = prismColor;
        this.icon = PrismIconFactory.createFilledIcon(
                prismColor.getIconColor()
        );
    }

    /**
     * Returns the icon displayed in the gutter.
     */
    @Override
    public @NotNull Icon getIcon() {
        return icon;
    }

    /**
     * Handles click interactions on the gutter icon.
     *
     * <p>Only left-click triggers a color toggle.</p>
     */
    @Override
    public AnAction getClickAction() {
        return new AnAction() {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e) {

                if (!(e.getInputEvent() instanceof MouseEvent me)) {
                    return;
                }

                // solo click izquierdo
                if (!SwingUtilities.isLeftMouseButton(me)) {
                    return;
                }

                new ToggleLineColorService(editor, line).toggle();
            }
        };
    }

    /**
     * Defines icon alignment in the gutter.
     */
    @Override
    public @NotNull Alignment getAlignment() {
        return Alignment.LEFT;
    }

    /**
     * Disables default navigation behavior on click.
     */
    @Override
    public boolean isNavigateAction() {
        return false;
    }

    /**
     * Provides the context menu (right-click menu) for this gutter icon.
     *
     * @return PrismLines action group
     */
    @Override
    public ActionGroup getPopupMenuActions() {
        return (ActionGroup) ActionManager.getInstance()
                .getAction("PrismLinesGroup");
    }

    /**
     * Equality based on line and color to avoid duplicate gutter icons.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof GutterIcon other)) return false;
        return line == other.line &&
                prismColor == other.prismColor;
    }

    /**
     * Hash based on line and color.
     */
    @Override
    public int hashCode() {
        return Objects.hash(line, prismColor);
    }
}