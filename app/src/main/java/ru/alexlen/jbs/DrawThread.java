package ru.alexlen.jbs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

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
    private final GraphStack stack;

    public DrawThread(SurfaceHolder surfaceHolder, GraphStack stack) {
        this.surfaceHolder = surfaceHolder;
        this.stack = stack;
    }

    final static int TURN = 50; //ms
    long TICK = 0; //ms

    @Override
    public void run() {
        Log.d(TAG, "Start Daemon");

        long start;
        long end;
        long remain;

        while (!isStop) {

            start = SystemClock.elapsedRealtime();
            TICK++;

            if (!stack.isChanged()) continue;

            RenderTask[] tasks = stack.getCopy();

            if (tasks.length != 0) {
                Log.d(TAG, "Tick: " + TICK + ", tasks: " + tasks.length);

                draw2(tasks);
            }

            end = SystemClock.elapsedRealtime();

            remain = TURN - (end - start);
            if (remain <= 0) continue;

            try {
                //let other threads do they work
                Thread.sleep(remain);
            } catch (InterruptedException ignored) {}
        }

    }

    private void draw2(RenderTask[] tasks) {
        Canvas canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas(null);
            if (canvas == null) return; //may be in end
            draw(canvas, tasks);

        } finally {
            if (canvas != null) {
                // отрисовка выполнена. выводим результат на экран
                surfaceHolder.unlockCanvasAndPost(canvas);

            } else {
                Log.d(TAG, "Null finally canvas");
            }


        }
    }

    private void draw(Canvas canvas, RenderTask[] tasks) {

        canvas.drawColor(0xFF000000);

        for (RenderTask renderTask : tasks) {
            renderTask.draw(canvas);
        }

        // rater.commitFrame();

    }

    private void process(RenderTask task, long start) {
        Canvas canvas = null;

        Rect rect = null;
        if (task instanceof AreaRenderTask) {
            rect = ((AreaRenderTask) task).getArea();
        }

//        if (rect != null) {
//            Log.d(TAG, "Got task: " + task + " " + rect.toShortString() + " still: " + queue.size());
//        } else {
//            Log.d(TAG, "Got task: " + task + " still: " + queue.size());
//        }

        try {
            // получаем объект Canvas и выполняем отрисовку
            canvas = surfaceHolder.lockCanvas(rect);
            if (canvas == null) return;

            canvas.drawColor(0xFF000000);
//                if (!(task instanceof ControlTask)) canvas.drawColor(0xFF000000);
//                if (!(task instanceof ControlTask)) canvas.drawColor(0xFF000000);

//                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            //canvas.drawColor(Color.TRANSPARENT);

//                synchronized (surfaceHolder) {


//                bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas2 = new Canvas(bitmap);
            task.draw(canvas);
//                task.draw(canvas2);

            // rater.commitFrame(start, SystemClock.elapsedRealtime());


            //scanvas.drawBitmap(bitmap, 0, 0, null);


            //  surfaceHolder.draw(new Canvas(bitmap));
//                }
        } finally {
            if (canvas != null) {
                // отрисовка выполнена. выводим результат на экран
                surfaceHolder.unlockCanvasAndPost(canvas);

            } else {
                Log.d(TAG, "Null finally canvas");
            }
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
