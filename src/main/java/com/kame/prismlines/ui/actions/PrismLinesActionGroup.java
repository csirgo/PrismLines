package com.kame.prismlines.ui.actions;

import com.intellij.openapi.actionSystem.Separator;
import com.kame.prismlines.enums.PrismColor;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.kame.prismlines.listener.MouseListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dynamic action group that provides PrismLines context menu entries.
 *
 * <p>It builds the menu based on available {@link PrismColor} values and
 * appends utility actions such as clearing a line color.</p>
 */
public class PrismLinesActionGroup extends ActionGroup {

    /**
     * Creates the PrismLines action group displayed in the editor context menu.
     */
    public PrismLinesActionGroup() {
        super("PrismLines", true); // popup group
    }

    /**
     * Generates the list of available actions dynamically.
     *
     * <p>Includes one action per {@link PrismColor} plus a separator
     * and a "Clear" action.</p>
     *
     * @param e action event context (may be null)
     * @return array of child actions for the popup menu
     */
    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {

        MouseListener mouseListener = MouseListener.getInstance();

        List<AnAction> actions = new ArrayList<>(Arrays.stream(PrismColor.values())
                .map(color -> new ColorLineAction(color, mouseListener))
                .toList());

        actions.add(Separator.getInstance());
        actions.add(new ClearColorAction());

        return actions.toArray(new AnAction[0]);
    }
}
