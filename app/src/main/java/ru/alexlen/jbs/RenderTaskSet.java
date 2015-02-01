package ru.alexlen.jbs;

import android.graphics.Canvas;
import android.graphics.Rect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Almazko
 */
public final class RenderTaskSet implements AreaRenderTask{

    private final RenderTask[] tasks;
    private final Rect area;

    public RenderTaskSet(RenderTask ...tasks) {
        this.tasks = tasks;
        this.area = null;
    }

    public RenderTaskSet(Rect area, RenderTask ...tasks) {
        this.tasks = tasks;
        this.area = area;
    }

    @Nullable @Override public Rect getArea() {
        return area;
    }

    @Override public void draw(@NotNull Canvas canvas) {
        for (RenderTask task : tasks) {
            task.draw(canvas);
        }
    }
}
