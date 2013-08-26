package free.almazko.battle_ship;

import android.graphics.Canvas;

public class DrawBattleMapState {
    public int width;
    public int height;
    public int cellSizeX;
    public int cellSizeY;

    private int size;
    Canvas canvas;

    public boolean isEmpty() {
        return this.canvas == null;
    }

    public boolean isPositionChanged(Canvas canvas) {
        if (isEmpty()) {
            return true;
        }

        if (this.canvas.getWidth() != canvas.getWidth()) {
            return true;
        }

        return false;
    }

    public boolean update(Canvas canvas, byte numberCells) {
        if (isPositionChanged(canvas)) {
            width = canvas.getWidth();
            height = canvas.getHeight();
            if (width >= height) {
                size = height;
                cellSizeY = cellSizeX = height / numberCells;
            } else {
                size = width;
                cellSizeY = cellSizeX = width / numberCells;
            }

        }

        this.canvas = canvas;

        return false;
    }

    public void update(int size, byte numberCells) {
        width = size;
        height = size;

        this.size = size;
        cellSizeY = cellSizeX = height / numberCells;
    }


    public int size() {
        return size;
    }

    public boolean isVertical() {
        if (canvas != null) {
            if (width < height) {
                return true;
            }
        }

        return false;
    }
}
