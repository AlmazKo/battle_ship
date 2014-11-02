package ru.alexlen.jbs;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import org.jetbrains.annotations.NotNull;
import ru.alexlen.jbs.stage.AbstractController;
import ru.alexlen.jbs.ui.Styles;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "GameView";
    Vibrator           vibrator;
    AbstractController controller;
    private DrawThread drawThread;
    private BlockingDeque<RenderTask> queue = new LinkedBlockingDeque<>();


    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

    }

    public GameView(Context context)
    {
        super(context);

        // setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setFocusable(true);
        setFocusableInTouchMode(true);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        getHolder().addCallback(this);
    }

    synchronized public void addRenderTask(@NotNull final RenderTask task)
    {
        queue.push(task);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        drawThread = new DrawThread(holder, queue);
        drawThread.start();

//        drawThread = new DrawThread(getHolder());
//        drawThread.start();
//        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        int i = 10 + 10;
        i++;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
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


    public void setGridOffset(int gridCellOffset)
    {
        Styles.fill(gridCellOffset);
    }

    public Vibrator getVibrator()
    {
        return vibrator;
    }

    public void reDraw()
    {
        invalidate();
    }

    public void setController(AbstractController controller)
    {
        this.controller = controller;
        setOnTouchListener(controller);
        reDraw();
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int mTextWidth = 10;
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - (int) mTextWidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int) mTextWidth, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

}