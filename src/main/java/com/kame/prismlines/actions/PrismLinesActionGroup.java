package com.kame.prismlines.actions;

import com.kame.prismlines.color.PrismColor;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.kame.prismlines.listener.MouseListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;


public class PrismLinesActionGroup extends ActionGroup {

    // Constructor
    public PrismLinesActionGroup() {
        super("PrismLines", true); // segundo parámetro: isPopup
    }

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {

        MouseListener mouseListener = MouseListener.getInstance();

        return Arrays.stream(PrismColor.values())
                .map(color -> new ColorLineAction(color, mouseListener))
                .toArray(AnAction[]::new);
    }
}
