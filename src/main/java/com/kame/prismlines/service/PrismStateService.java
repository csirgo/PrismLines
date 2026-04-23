package com.kame.prismlines.service;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.State;
import com.intellij.openapi.project.Project;
import com.kame.prismlines.enums.PrismColor;
import com.kame.prismlines.persistence.PrismLineState;
import com.kame.prismlines.persistence.PrismLinesState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Project-level persistent service responsible for storing PrismLines state.
 * <p>
 * This service uses IntelliJ's {@link PersistentStateComponent} mechanism to
 * persist colored line information across IDE restarts in the
 * {@code prism-lines.xml} file.
 * <p>
 * It acts as the single source of truth for all persisted line decorations.
 */
@State(
        name = "PrismLinesState",
        storages = @Storage("prism-lines.xml")
)
@Service(Service.Level.PROJECT)
public final class PrismStateService implements PersistentStateComponent<PrismLinesState> {

    private PrismLinesState state = new PrismLinesState();

    /**
     * Returns the current persistent state object.
     *
     * @return the stored PrismLines state
     */
    @Override
    public @Nullable PrismLinesState getState() {
        return state;
    }

    /**
     * Loads persisted state from disk into memory.
     *
     * @param state the deserialized state loaded from XML
     */
    @Override
    public void loadState(@NotNull PrismLinesState state) {
        this.state = state;
    }

    /**
     * Retrieves the service instance for the given project.
     *
     * @param project the current project
     * @return the PrismStateService instance
     */
    public static PrismStateService getInstance(Project project) {
        return project.getService(PrismStateService.class);
    }

    /**
     * Saves or updates a colored line entry in persistent storage.
     * <p>
     * If an entry already exists for the same file and line, it will be replaced.
     *
     * @param filePath the file path where the line belongs
     * @param line the line number
     * @param color the PrismColor applied to the line
     */
    public void saveLine(
            String filePath,
            int line,
            PrismColor color
    ) {
        state.lines.removeIf(l ->
                l.filePath.equals(filePath)
                        && l.line == line
        );

        state.lines.add(
                new PrismLineState(
                        filePath,
                        line,
                        color.name()
                )
        );
    }

    /**
     * Removes a persisted line entry from storage.
     *
     * @param filePath the file path where the line belongs
     * @param line the line number to remove
     */
    public void removeLine(String filePath, int line) {
        state.lines.removeIf(l ->
                l.filePath.equals(filePath)
                        && l.line == line
        );
    }

    /**
     * Retrieves all persisted colored lines for a specific file.
     *
     * @param filePath the file path to filter by
     * @return list of persisted line states for that file
     */
    public List<PrismLineState> getLines(String filePath) {
        return state.lines.stream()
                .filter(l -> l.filePath.equals(filePath))
                .toList();
    }
}