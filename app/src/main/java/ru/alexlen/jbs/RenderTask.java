package ru.alexlen.jbs;

import android.graphics.Canvas;
import org.jetbrains.annotations.NotNull;

/**
 * @author Almazko
 */
public interface RenderTask {
    public void draw(@NotNull final Canvas canvas);
    public int getZIndex();
    public int getId();
   // public boolean isInstant();
}
