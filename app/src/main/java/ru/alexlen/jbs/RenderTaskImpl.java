package ru.alexlen.jbs;

import android.os.SystemClock;
import org.jetbrains.annotations.NotNull;

/**
 * @author Almazko
 */
public abstract class RenderTaskImpl implements RenderTask, Comparable<RenderTaskImpl> {

    private final long created;

    protected RenderTaskImpl() {
        created = SystemClock.elapsedRealtime();
    }

    @Override public int compareTo(@NotNull RenderTaskImpl another) {
        return created < another.created ? -1 : (another.created == created ? 0 : 1);
    }
}
