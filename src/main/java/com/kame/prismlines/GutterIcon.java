package com.kame.prismlines;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class GutterIcon extends GutterIconRenderer {

    private final Editor editor;
    private final int line;
    private final JBColor color;
    private final Icon icon;

    public GutterIcon(Editor editor, int line, JBColor color) {
        this.editor = editor;
        this.line = line;
        this.color = color;
        this.icon = createIcon(color.getDefaultColor());
    }

    @Override
    public @NotNull Icon getIcon() {
        return icon;
    }

    @Override
    public AnAction getClickAction() {
        return new AnAction() {
            @Override
            public void actionPerformed(@NotNull com.intellij.openapi.actionSystem.AnActionEvent e) {
                new ToggleLineColorService(editor, line, color).toggle();
            }
        };
    }

    @Override
    public Alignment getAlignment() {
        return Alignment.LEFT;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    private Icon createIcon(Color color) {

        int size = 8;

        BufferedImage img =
                new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = img.createGraphics();

        g.setColor(color);
        g.fillRect(0, 0, size, size);

        g.dispose();

        return new ImageIcon(img);
    }
}