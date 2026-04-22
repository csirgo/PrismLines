package com.kame.prismlines.gutter;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.actionSystem.AnAction;
import com.kame.prismlines.color.PrismColor;
import com.kame.prismlines.icon.PrismIconFactory;
import com.kame.prismlines.service.ToggleLineColorService;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;


public class GutterIcon extends GutterIconRenderer {

    private final Editor editor;
    private final int line;
    private final PrismColor prismColor;
    private final Icon icon;

    public GutterIcon(Editor editor, int line, PrismColor prismColor) {
        this.editor = editor;
        this.line = line;
        this.prismColor = prismColor;
        this.icon = PrismIconFactory.createColorIcon(
                prismColor.getIconColor()
        );
    }

    @Override
    public @NotNull Icon getIcon() {
        return icon;
    }

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

    @Override
    public @NotNull Alignment getAlignment() {
        return Alignment.LEFT;
    }

    @Override
    public boolean isNavigateAction() {
        return false;
    }

    @Override
    public ActionGroup getPopupMenuActions() {
        return (ActionGroup) ActionManager.getInstance()
                .getAction("PrismLinesGroup");
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GutterIcon other)) return false;
        return line == other.line &&
                prismColor == other.prismColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, prismColor);
    }
}