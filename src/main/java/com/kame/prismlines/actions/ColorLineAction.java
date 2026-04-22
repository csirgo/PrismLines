package com.kame.prismlines.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.kame.prismlines.icon.PrismIconFactory;
import com.kame.prismlines.listener.MouseListener;
import com.kame.prismlines.service.ToggleLineColorService;
import com.kame.prismlines.color.PrismColor;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ColorLineAction extends AnAction {

    private final PrismColor prismColor;
    private final MouseListener mouseListener;

    public ColorLineAction(PrismColor prismColor, MouseListener mouseListener) {
        super(
                prismColor.getDisplayName(),
                "Color line " + prismColor.getDisplayName(),
                PrismIconFactory.createColorIcon(prismColor.getIconColor())
        );
        this.prismColor = prismColor;
        this.mouseListener = mouseListener;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) return;

        Integer line = mouseListener.getLastRightClickLine(editor);
        if (line == null) return; // si no hay click registrado, no hacemos nada

        new ToggleLineColorService(editor, line, prismColor).toggle();
    }

    private static Icon createRoundedIcon(Color color) {
        int size = 12;

        BufferedImage image = new BufferedImage(
                size,
                size,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g.setColor(color);

        g.fillRoundRect(
                0,
                0,
                size,
                size,
                4,
                4
        );

        g.dispose();



        return new ImageIcon(image);
    }
}
