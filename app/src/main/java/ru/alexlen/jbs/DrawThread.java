package ru.alexlen.jbs;

import android.graphics.*;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.concurrent.BlockingDeque;

/**
 * @author Almazko
 */
public class DrawThread extends Thread {

    private static final String TAG = "JBS_DrawThread";

    private final static class FrameRater {

        final static int HISTORY = 20;

        final int measures[] = new int[HISTORY];
        final Paint paint;

        int     count;
        long    mLastTime;
        boolean collect;


        private FrameRater() {
            paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            paint.setTextSize(14);
        }


        void commitFrame(long start, long current) {


            if (!collect || mLastTime == 0) {
                mLastTime = start;
                collect = true;
                count = 0;
            } else {
                count++;
            }

            for (int i = 1; i < HISTORY; i++) {
                measures[i - 1] = measures[i];
            }

            measures[HISTORY - 1] = (int) (current - start); //ms
            if (measures[HISTORY - 1] == 0) measures[9] = 1;


            if ((start - mLastTime) > 1000) {

                float collector = 0;
                int i;
                for (i = HISTORY - 1; i >= 0; i--) {
                    if (measures[i] == 0) break;
                    collector += measures[i];
                }

                collect = false;
                Log.i(TAG, " avg rate: " + (collector / (HISTORY - i)) + "ms, tasks: " + count);

            }
        }
    }

    private final SurfaceHolder surfaceHolder;
    private final FrameRater rater = new FrameRater();

    private boolean isStop = false;
    private BlockingDeque<RenderTask> queue;

    public DrawThread(SurfaceHolder surfaceHolder, BlockingDeque<RenderTask> queue) {
        this.surfaceHolder = surfaceHolder;
        this.queue = queue;
    }

    @Override
    public void run() {
        RenderTask task;
        Canvas canvas;

        Log.d(TAG, "Start Daemon");

        while (!isStop) {
            try {
                task = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

            long start = SystemClock.elapsedRealtime();

            canvas = null;

            Rect rect = null;
            if (task instanceof AreaRenderTask) {
                rect = ((AreaRenderTask) task).getArea();
            }

            if (rect != null) {
                Log.d(TAG, "Got task: " + task + " " + rect.toShortString() + " still: " + queue.size());
            } else {
                Log.d(TAG, "Got task: " + task + " still: " + queue.size());
            }

            try {
                // получаем объект Canvas и выполняем отрисовку
                canvas = surfaceHolder.lockCanvas(rect);
                if (canvas == null) continue;

//                if (!(task instanceof ControlTask)) canvas.drawColor(0xFF000000);
//                if (!(task instanceof ControlTask)) canvas.drawColor(0xFF000000);

                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                synchronized (surfaceHolder) {
                    task.draw(canvas);
                    rater.commitFrame(start, SystemClock.elapsedRealtime());

                    Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
                  //  surfaceHolder.draw(new Canvas(bitmap));
                }
            } finally {
                if (canvas != null) {
                    // отрисовка выполнена. выводим результат на экран
                    surfaceHolder.unlockCanvasAndPost(canvas);

                } else {
                    Log.d(TAG, "Null finally canvas");
                }


            }

//            try {
//                //let other threads do they work
//                Thread.sleep(15);
//            } catch (InterruptedException ignored) {}


        }

    }

    @Override
    public void interrupt() {
        Log.d(TAG, "Stop");
        super.interrupt();
    }

    public void kill() {
        isStop = true;
    }
}
