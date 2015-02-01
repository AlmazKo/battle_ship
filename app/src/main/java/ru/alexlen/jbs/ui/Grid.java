package ru.alexlen.jbs.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import org.jetbrains.annotations.Nullable;
import ru.alexlen.jbs.game.Cell;


//make child of View
public class Grid {

    public class Properties {
        public float cellSpacing;
        public int   cellSize;
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

    public class Drawer {
        private final Canvas canvas;

        private Drawer(Canvas canvas) {
            this.canvas = canvas;
        }


        public void drawCell(final Cell cell, Paint paint) {
            properties.update(paint);
            RectF rect = makeRect(cell.x, cell.y);
            canvas.drawRect(rect, paint);
        }

        public void drawCell(int x, int y, Paint paint) {
            properties.update(paint);
            RectF rect = makeRect(x, y);
            canvas.drawRect(rect, paint);
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

    }

    private int size;
    private int numCells;
    private int top = 0, left = 0;

    public Properties properties = new Properties();

    public Grid(int numCells, int size) {
        this.numCells = numCells;
        this.size = size;
    }

    public Drawer getDrawer(Canvas canvas) {
        return new Drawer(canvas);
    }

    public void setPosition(int left, int top) {
        this.top = top;
        this.left = left;
    }

    public Rect getRect() {
        return new Rect(left, top, left + size, top + size);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCellSpacing(int cellSpacing) {
        properties.update(cellSpacing);
    }

    public int size() {
        return size;
    }


    private Cell lastCell;

    @Nullable
    public Cell recognizeCell(float x, float y) {

        int cellX = (int) Math.floor((x - left) / properties.cellSize);
        int cellY = (int) Math.floor((y - top) / properties.cellSize);

        if (cellX >= numCells || cellY >= numCells || cellX < 0 || cellY < 0) {
            return null;
        }

        //optimization
        if (lastCell != null && lastCell.equals(cellX, cellY)) return lastCell;

        lastCell = new Cell(cellX, cellY);

        return lastCell;
    }


    private RectF makeRect(int x, int y) {

        float offset = Math.round((size - 2 * properties.cellOffset) / numCells);
        float posX = left + properties.cellSpacing - 1 + x * offset;
        float posY = top + properties.cellSpacing - 1 + y * offset;

        return new RectF(
                posX, posY,
                posX + offset - properties.cellSpacing + 1, posY + offset - properties.cellSpacing + 1);
    }
}
