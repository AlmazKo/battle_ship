package ru.alexlen.jbs.event;

import android.view.MotionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.alexlen.jbs.game.Cell;

/**
 * @author Almazko
 */
public class GameEvent implements CellAction {
    public final Cell cell;
    public final MotionEvent cause;

    public GameEvent(MotionEvent cause, @Nullable Cell cell) {
        this.cause = cause;
        this.cell = cell;
    }

    public boolean isCellRecognized() {
        return cell != null;
    }


    @Nullable
    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public boolean isUp() {
        return cause.getAction() == MotionEvent.ACTION_UP;
    }

    @Override
    public boolean isDown() {
        return cause.getAction() == MotionEvent.ACTION_DOWN;
    }

    @Override
    public boolean isMove() {
        return cause.getAction() == MotionEvent.ACTION_MOVE;
    }
}
