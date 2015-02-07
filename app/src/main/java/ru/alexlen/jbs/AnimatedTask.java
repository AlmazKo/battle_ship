package ru.alexlen.jbs;

/**
 * @author Almazko
 */
abstract class AnimatedTask implements RenderTask {

    private long  start;
    private int   time;
    private float value;

    public void setElapsedTime(long currentTimeMs) {

        if (start == 0) {
            start = currentTimeMs;
            value = 0;
        } else {
            int elapsed = (int) (currentTimeMs - start);
            value = (elapsed > time) ? 1 : elapsed / (float) time;
        }
    }

    public boolean isCompleted() {
        return value == 1;
    }

    public int getLifeTime() {
        return time;
    }

    // [0...1]
    protected float getValue() {
        return value;
    }

    public void setLifeTime(int timeMs) {
        this.time = timeMs;
    }
}
