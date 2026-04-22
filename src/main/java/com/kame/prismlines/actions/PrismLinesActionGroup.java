package com.kame.prismlines.actions;

import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Separator;
import com.kame.prismlines.color.PrismColor;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.kame.prismlines.listener.MouseListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PrismLinesActionGroup extends ActionGroup {

    // Constructor
    public PrismLinesActionGroup() {
        super("PrismLines", true); // segundo parámetro: isPopup
    }

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {

        MouseListener mouseListener = MouseListener.getInstance();

        List<AnAction> actions = new ArrayList<>();

        // Colores
        actions.addAll(
                Arrays.stream(PrismColor.values())
                        .map(color -> new ColorLineAction(color, mouseListener))
                        .toList()
        );

        // Separador
        actions.add(Separator.getInstance());

        // Clear
        actions.add(new ClearColorAction());

        return actions.toArray(new AnAction[0]);
    }
}
