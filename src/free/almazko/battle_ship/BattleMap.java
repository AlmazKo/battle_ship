package free.almazko.battle_ship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class BattleMap {

    //    List<Point> points = new ArrayList<Point>();
    private static final byte FIELD_SIZE = 10;
    Paint linePaint = new Paint();
    Paint objPaint = new Paint();
    BattleMapState state = new BattleMapState();

    public BattleMap() {
        linePaint.setColor(0xFF0000FF);
        linePaint.setAntiAlias(true);

        objPaint.setColor(0xFFFFFFFF);
        objPaint.setAntiAlias(true);
    }

    protected void draw(Canvas canvas) {
        state.update(canvas, FIELD_SIZE);

        for (int y = state.cellSizeY; y <= state.height; y += state.cellSizeY) {
            canvas.drawLine(0, y, state.width, y, linePaint);
        }

        for (int x = state.cellSizeX; x <= state.width; x += state.cellSizeX) {
            canvas.drawLine(x, 0, x, state.height, linePaint);
        }
    }


    public Cell recognizeCell(float x, float y) {
        if (state.isEmpty()) {
            return new Cell(0, 0);
        }

        int cellX = (int) Math.floor(x / state.cellSizeX);
        int cellY = (int) Math.floor(y / state.cellSizeY);

        return new Cell(cellX, cellY);
    }

    public void drawDraftCell(Cell cell) {
        Rect rect = makeRect(cell);
        state.canvas.drawRect(rect, objPaint);
    }

    public Rect makeRect(Cell cell) {
        return new Rect(
                cell.x * state.cellSizeX, cell.y * state.cellSizeY,
                (cell.x + 1) * state.cellSizeX, (cell.y + 1)* state.cellSizeY);
    }
}
