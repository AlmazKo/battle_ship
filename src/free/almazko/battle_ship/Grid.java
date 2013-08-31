package free.almazko.battle_ship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Grid {

    public class Properties {
        public int cellSpacing;
        public int cellSize;
        public float cellOffset;
    }

    private int size;
    private int numCells;
    private int top = 0, left = 0;

    private Canvas canvas;
    public Properties properties;

    public Grid(Canvas canvas, int numCells, int size) {
        this.numCells = numCells;
        this.size = size;
        this.canvas = canvas;
        initVars(canvas);
    }

    public Grid(Canvas canvas, int numCells) {
        this.numCells = numCells;
        this.canvas = canvas;
        size = Math.min(canvas.getHeight(), canvas.getWidth());
        initVars(canvas);
    }

    public void setPosition(int top, int left) {
        this.top = top;
        this.left = left;
    }

    private void initVars(Canvas canvas) {
        properties = new Properties();
        properties.cellSize = size / numCells;
        properties.cellSpacing = 3;
        properties.cellOffset = (float) Math.floor(properties.cellSpacing / 2);
    }

    public void changeCanvas(Canvas canvas) {
        this.canvas = canvas;
        initVars(canvas);
    }

    public int size() {
        return size;
    }


    public void draw(Paint paint) {
        int width = size;
        int height = size;

        float y = left + properties.cellOffset;
        float x = left + properties.cellOffset;

        float offset = (width - 2 * properties.cellOffset) / numCells;
        for (byte i = 0; i <= numCells; i++) {
            canvas.drawLine(properties.cellOffset, y, width, y, paint);  //horizontal
            canvas.drawLine(x, top, x, height, paint);                     //vertical
            y += offset;
            x += offset;
        }
    }

    public Cell recognizeCell(float x, float y) {
        int cellX = (int) Math.floor(x / properties.cellSize);
        int cellY = (int) Math.floor(y / properties.cellSize);

        if (cellX >= numCells || cellY >= numCells) {
            return null;
        }

        return new Cell(cellX, cellY);
    }

    public void drawCell(final Cell cell, Paint paint) {
        RectF rect = makeRect(cell.x, cell.y);
        canvas.drawRect(rect, paint);
    }

    public void drawCell(int x, int y, Paint paint) {
        RectF rect = makeRect(x, y);
        canvas.drawRect(rect, paint);
    }

    public RectF makeRect(int x, int y) {
        float offset = (properties.cellSize * numCells - 2 * properties.cellOffset) / numCells;
        float posX = properties.cellSpacing - 1 + x * offset;
        float posY = properties.cellSpacing - 1 + y * offset;

        return new RectF(
                posX, posY,
                posX + offset - properties.cellSpacing + 1, posY + offset - properties.cellSpacing + 1);
    }


}
