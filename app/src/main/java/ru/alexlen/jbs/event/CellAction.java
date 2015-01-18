package ru.alexlen.jbs.event;

import org.jetbrains.annotations.Nullable;
import ru.alexlen.jbs.game.Cell;

/**
 * @author Almazko
 */
public interface CellAction {

    @Nullable
    public Cell getCell();
    public boolean isCellRecognized();
    public boolean isUp();
    public boolean isDown();
    public boolean isMove();
}
