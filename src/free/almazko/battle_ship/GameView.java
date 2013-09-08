package free.almazko.battle_ship;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.View;
import free.almazko.battle_ship.game.AbstractController;
import free.almazko.battle_ship.game.InitController;
import free.almazko.battle_ship.game.Styles;

public class GameView extends View {
    private static final String TAG = "GameView";
    Vibrator vibrator;
    AbstractController controller;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context) {
        super(context);

        // setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setFocusable(true);
        setFocusableInTouchMode(true);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void setGridOffset(int gridCellOffset) {
        Styles.fill(gridCellOffset);
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


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mTextWidth =10;
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - (int)mTextWidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int)mTextWidth, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

}