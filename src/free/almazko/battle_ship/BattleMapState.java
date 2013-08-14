package free.almazko.battle_ship;

import android.graphics.Canvas;

public class BattleMapState {
    public int width;
    public int height;
    public int cellSizeX;
    public int cellSizeY;

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

    public void update(Canvas canvas, byte numberCells) {
        if (isPositionChanged(canvas)) {
            width = canvas.getWidth();
            height = canvas.getHeight();
            if (width>= height) {
                cellSizeY = cellSizeX = height / numberCells;
            } else {
                cellSizeY = cellSizeX = width / numberCells;
            }

        }

        this.canvas = canvas;
    }
}
