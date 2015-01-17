package ru.alexlen.jbs;

import android.view.MotionEvent;
import android.view.View;
import ru.alexlen.jbs.event.CellActionListener;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Almazko
 */
public abstract class AbstractViewController implements View.OnTouchListener {

    protected CellActionListener cellActionListener;
    public final BlockingDeque<RenderTask> queue = new LinkedBlockingDeque<>();

    protected final GameView view;

    public AbstractViewController(GameView view) {
        this.view = view;
        view.setOnTouchListener(this);
    }

    public void setCellActionListener(CellActionListener listener) {
        cellActionListener = listener;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
