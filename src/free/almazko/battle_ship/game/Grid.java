package free.almazko.battle_ship.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Grid {

    private Paint lastPaint = new Paint();

    public class Properties {
        public float cellSpacing;
        public int cellSize;
        public float cellOffset;

        void update(Paint paint) {
            cellSpacing = paint.getStrokeWidth();
            cellSize = size / numCells;
            cellOffset = (float) Math.floor(this.cellSpacing / 2);
        }

        void update(int cellSpacing) {
            this.cellSpacing = cellSpacing;
            cellSize = size / numCells;
            cellOffset = (float) Math.floor(this.cellSpacing / 2);
        }
    }

    private int size;
    private int numCells;
    private int top = 0, left = 0;

    private Canvas canvas;
    public Properties properties = new Properties();

    public Grid(Canvas canvas, int numCells, int size) {
        this.numCells = numCells;
        this.size = size;
        this.canvas = canvas;
    }

    public Grid(Canvas canvas, int numCells) {
        this.numCells = numCells;
        this.canvas = canvas;
        size = Math.min(canvas.getHeight(), canvas.getWidth());

    }

    public void setPosition(int left, int top) {
        this.top = top;
        this.left = left;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void setCellSpacing(int cellSpacing){
        properties.update(cellSpacing);
    }

    public void changeCanvas(Canvas canvas) {
        this.canvas = canvas;

    }

    public int size() {
        return size;
    }


    public void draw(Paint paint) {
        properties.update(paint);

        for (byte i = 0; i < numCells; i++) {
            for (byte j = 0; j < numCells; j++) {
                drawCell(i, j, Styles.get("cell_blank"));
            }
        }
    }

    public void drawByLine(Paint paint) {
        properties.update(paint);

        int width = size;
        int height = size;

        float y = top;
        float x = left;

        float leftInit = x;
        float leftEnd = x + width;

        float topInit = y;
        float topEnd = y + height;
        float offset = (width - 2 * properties.cellOffset) / numCells;

        x += properties.cellOffset;
        y += properties.cellOffset;
        for (byte i = 0; i <= numCells; i++) {

            canvas.drawLine(leftInit, y, leftEnd, y, paint);  //horizontal
            canvas.drawLine(x, topInit, x, topEnd, paint);  //vertical

            y += offset;
            x += offset;
        }
    }

    public Cell recognizeCell(float x, float y) {

        int cellX = (int) Math.floor((x - left) / properties.cellSize);
        int cellY = (int) Math.floor((y - top) / properties.cellSize);

        if (cellX >= numCells || cellY >= numCells || cellX < 0 || cellY < 0) {
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

        float offset = Math.round((size - 2 * properties.cellOffset) / numCells);
        float posX = left + properties.cellSpacing - 1 + x * offset;
        float posY = top + properties.cellSpacing - 1 + y * offset;

        return new RectF(
                posX, posY,
                posX + offset - properties.cellSpacing + 1, posY + offset - properties.cellSpacing + 1);
    }


}
