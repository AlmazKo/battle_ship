package ru.alexlen.jbs.event;

import org.jetbrains.annotations.NotNull;
import ru.alexlen.jbs.game.Cell;

/**
 * @author Almazko
 */
public interface CellAction {

    @NotNull
    public Cell getCell();
    public boolean isUp();
    public boolean isDown();
    public boolean isMove();
}
