package ru.alexlen.jbs.event;

import android.view.MotionEvent;
import org.jetbrains.annotations.NotNull;
import ru.alexlen.jbs.game.Cell;

/**
 * @author Almazko
 */
public class GameEvent {
    public Cell cell;
    public MotionEvent originalEvent;

    public GameEvent(@NotNull MotionEvent event, Cell cell) {
        originalEvent = event;
        this.cell = cell;
    }

    public boolean isCellRecognized() {
        return cell != null;
    }


    public boolean isUp() {
        return originalEvent.getAction() == MotionEvent.ACTION_UP;
    }

    public boolean isDown() {
        return originalEvent.getAction() == MotionEvent.ACTION_DOWN;
    }

    public boolean isMove() {
        return originalEvent.getAction() == MotionEvent.ACTION_MOVE;
    }
}
