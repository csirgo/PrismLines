package com.kame.prismlines.ui.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.kame.prismlines.ui.icon.PrismIconFactory;
import com.kame.prismlines.listener.MouseListener;
import com.kame.prismlines.service.ToggleLineColorService;
import com.kame.prismlines.enums.PrismColor;
import org.jetbrains.annotations.NotNull;

/**
 * Action that applies a specific color to the selected line.
 *
 * <p>The target line is determined from the last right-click interaction
 * registered in the editor gutter.</p>
 */
public class ColorLineAction extends AnAction {

    private final PrismColor prismColor;
    private final MouseListener mouseListener;

    /**
     * Creates a color action for the given Prism color.
     *
     * @param prismColor color associated with this action
     * @param mouseListener listener used to retrieve the selected line
     */
    public ColorLineAction(PrismColor prismColor, MouseListener mouseListener) {
        super(
                prismColor.getDisplayName(),
                "Color line " + prismColor.getDisplayName(),
                PrismIconFactory.createFilledIcon(prismColor.getIconColor())
        );
        this.prismColor = prismColor;
        this.mouseListener = mouseListener;
    }

    /**
     * Applies or replaces the color on the selected line.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) return;

        Integer line = mouseListener.getLastRightClickLine(editor);
        if (line == null) return;

        new ToggleLineColorService(editor, line, prismColor).toggle();
    }
}
