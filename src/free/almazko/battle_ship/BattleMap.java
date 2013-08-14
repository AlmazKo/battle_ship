package free.almazko.battle_ship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class BattleMap {

    //    List<Point> points = new ArrayList<Point>();
    private static final byte FIELD_SIZE = 6;
    private static final float ROW_STROKE = 19;
    private static final float CELL_OFFSET = (float) Math.floor(ROW_STROKE / 2);
    Paint linePaint = new Paint();
    Paint objPaint = new Paint();
    BattleMapState state = new BattleMapState();

    public BattleMap() {
        linePaint.setColor(0xFF001327);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(ROW_STROKE);

        objPaint.setColor(0xFF53A2F8);
        objPaint.setAntiAlias(true);
        objPaint.setStyle(Paint.Style.FILL);
        objPaint.setShadowLayer(ROW_STROKE, 0, 0, 0xFF53A2F8);
//        objPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
    }

    protected void draw(Canvas canvas) {
        state.update(canvas, FIELD_SIZE);

        int width = state.cellSizeY * FIELD_SIZE;
        int height = width;

        float y = CELL_OFFSET;
        float x = CELL_OFFSET;

        float offset = (width - 2 * CELL_OFFSET) / FIELD_SIZE;
        for (byte i = 0; i <= FIELD_SIZE; i++) {
            canvas.drawLine(CELL_OFFSET, y, width, y, linePaint);
            canvas.drawLine(x, 0, x, height, linePaint);
            y += offset;
            x += offset;
        }
    }

    // todo refactoring
    public Cell recognizeCell(float x, float y) {
        if (state.isEmpty()) {
            return new Cell(0, 0);
        }

        int cellX = (int) Math.floor(x / state.cellSizeX);
        int cellY = (int) Math.floor(y / state.cellSizeY);
        if (cellX >= FIELD_SIZE || cellY >= FIELD_SIZE) {
            return null;
        }

        Cell cell = new Cell(cellX, cellY);
        Log.i("DrawView", "recognize: " + cell);

        return cell;
    }

    public void drawDraftCell(Cell cell) {
        RectF rect = makeRect(cell);
        state.canvas.drawRect(rect, objPaint);
    }

    public RectF makeRect(Cell cell) {
        float offset = (state.cellSizeY * FIELD_SIZE - 2 * CELL_OFFSET) / FIELD_SIZE;
        float posX = ROW_STROKE - 1 + cell.x * offset;
        float posY = ROW_STROKE - 1 + cell.y * offset;

        return new RectF(
                posX, posY,
                posX + offset - ROW_STROKE + 1, posY + offset - ROW_STROKE + 1);
    }
}
