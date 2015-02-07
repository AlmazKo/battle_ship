package ru.alexlen.jbs;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Almazko
 */
public final class GraphStack {


    private final List<RenderTask> stack = new ArrayList<>();

    private boolean isChanged = true;

    public boolean isChanged() {
        return isChanged;
    }

    public RenderTask[] getCopy() {
        RenderTask[] tasks;

        synchronized (stack) {
            tasks = new RenderTask[stack.size()];
            stack.toArray(tasks);
            isChanged = false;
        }

        return tasks;
    }

    synchronized void remove(int id) {

        isChanged = true;
        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getId() == id) {
                stack.remove(i);
                return;
            }
        }

    }

    void add(@NotNull RenderTask task) {
        isChanged = true;
        int id = task.getId();

        for (int i = 0; i < stack.size(); i++) {
            if (stack.get(i).getId() == id) {
                stack.set(i, task);
                return;
            }
        }

        stack.add(task);
    }

    public void removeAll() {
        synchronized (stack) {
            stack.clear();
        }
    }
}
