package free.almazko.battle_ship;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
    private static final String TAG = "DrawView";

    private enum State {DRAFT, COMPLETE_SHIP, COMPLETE_ALL}

    private enum Direction {VERTICAL, HORIZONTAL}

    private State state = State.DRAFT;
    private Direction direction;

    MotionEvent event;
    InitController initController;

    public DrawView(Context context) {
        super(context);

        // setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void reDraw(MotionEvent event) {
        this.event = event;
        invalidate();
    }


    @Override
    public void onDraw(Canvas canvas) {
        if (initController == null) {
            initController = new InitController(canvas, this);
            initController.onDraw(canvas);
            setOnTouchListener(initController);
        } else {
            initController.onDraw(canvas);
        }

    }

}