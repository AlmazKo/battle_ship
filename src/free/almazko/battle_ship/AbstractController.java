package free.almazko.battle_ship;

import android.graphics.Canvas;
import android.view.View;

public abstract class AbstractController implements View.OnTouchListener {
    abstract public void onDraw(Canvas canvas);
}
