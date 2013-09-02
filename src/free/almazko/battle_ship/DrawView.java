package free.almazko.battle_ship;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.View;

public class DrawView extends View {
    private static final String TAG = "DrawView";
    Vibrator vibrator;
    AbstractController controller;

    public DrawView(Context context) {
        super(context);

        // setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setFocusable(true);
        setFocusableInTouchMode(true);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }


    public Vibrator getVibrator() {
        return vibrator;
    }
    public void reDraw() {
        invalidate();
    }

    public void setController(AbstractController controller) {
        this.controller = controller;
        setOnTouchListener(controller);
        reDraw();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (controller == null) {
            controller = new InitController(canvas, this);
            controller.onDraw(canvas);
            setOnTouchListener(controller);
        } else {
            controller.onDraw(canvas);
        }

    }

}