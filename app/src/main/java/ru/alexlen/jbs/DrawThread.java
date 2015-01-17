package ru.alexlen.jbs;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingDeque;

/**
 * @author Almazko
 */
public class DrawThread extends Thread {

    private static final String TAG = "JBS_DrawThread";

    private final SurfaceHolder surfaceHolder;

    private boolean isStop = false;
    private BlockingDeque<RenderTask> queue;

    public DrawThread(SurfaceHolder surfaceHolder, BlockingDeque<RenderTask> queue)
    {
        this.surfaceHolder = surfaceHolder;
        this.queue = queue;
    }

    @Override
    public void run()
    {
        RenderTask effect;
        Canvas canvas;

        Log.d(TAG, "Start Daemon");

        while (!isStop) {
            try {
                effect = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }


            Log.d(TAG, "Got task: " + effect);

            canvas = null;

            try {
                // получаем объект Canvas и выполняем отрисовку
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    effect.draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    // отрисовка выполнена. выводим результат на экран
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }


            final long now = System.currentTimeMillis();

        }

    }


    @Override
    public void interrupt()
    {
        Log.d(TAG, "Stop");
        super.interrupt();
    }

    public void kill()
    {
        isStop = true;
    }

    synchronized public void addRenderTask(@NotNull final RenderTask task)
    {
        queue.add(task);
    }
}
