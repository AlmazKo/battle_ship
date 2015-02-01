package ru.alexlen.jbs;

import android.graphics.Rect;
import org.jetbrains.annotations.Nullable;

/**
 * @author Almazko
 */
public interface AreaRenderTask extends RenderTask {

    @Nullable
    public Rect getArea();
}
