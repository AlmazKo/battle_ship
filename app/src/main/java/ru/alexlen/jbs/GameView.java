package ru.alexlen.jbs;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import org.jetbrains.annotations.NotNull;
import ru.alexlen.jbs.ui.Styles;

import java.util.concurrent.atomic.AtomicInteger;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "GameView";
    Vibrator vibrator;
    private DrawThread drawThread;
    private final GraphStack stack = new GraphStack();

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context) {
        super(context);

        // setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setFocusable(true);
        setFocusableInTouchMode(true);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        //  getHolder().addCallback(this);
    }


    private static AtomicInteger ainc = new AtomicInteger(100000);

    public int getUniqueId() {
        return ainc.getAndIncrement();
    }

    void removeTask(int id) {

        stack.remove(id);

    }

    void addRenderTask(@NotNull RenderTask task) {
        stack.add(task);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(holder, stack);
        drawThread.start();

        ((GameActivity) getContext()).startDisposal();
//        drawThread = new DrawThread(getHolder());
//        drawThread.start();
//        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        int i = 10 + 10;
        i++;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        // завершаем работу потока
        drawThread.kill();
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // если не получилось, то будем пытаться еще и еще
            }
        }
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


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("GameView", "onDraw" + canvas);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mTextWidth = 10;
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - (int) mTextWidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int) mTextWidth, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    public void removeAllTasks() {
        stack.removeAll();
    }
}