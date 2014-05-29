package free.almazko.battle_ship.game.event;

import android.view.MotionEvent;
import free.almazko.battle_ship.game.Cell;

/**
 * @author Almazko
 */
public class GameEvent {
    public Cell cell;
    public MotionEvent originalEvent;

    public GameEvent(MotionEvent event, Cell cell) {
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
