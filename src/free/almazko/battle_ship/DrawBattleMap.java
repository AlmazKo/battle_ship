package free.almazko.battle_ship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class DrawBattleMap {

    //    List<Point> points = new ArrayList<Point>();
    private static final byte FIELD_SIZE = 10;
    private static final float ROW_STROKE = 11;
    private static final float CELL_OFFSET = (float) Math.floor(ROW_STROKE / 2);
    Paint linePaint = new Paint();
    Paint textPaint = new Paint();
    Paint previewPaint = new Paint();

    DrawBattleMapState state = new DrawBattleMapState();

    public static final Paint PAINT_SHIP = new Paint();
    public static final Paint PAINT_DRAFT_SHIP = new Paint();
    public static final Paint PAINT_WRONG_SHIP = new Paint();
    public static final Paint PAINT_SHIPS_AREA = new Paint();

    int reviewPlaceX = 900;
    int reviewPlaceY = 50;

    static {
        PAINT_SHIPS_AREA.setStyle(Paint.Style.FILL);
        PAINT_SHIPS_AREA.setColor(0xFF022244);

        PAINT_DRAFT_SHIP.setColor(0xFF53A2F8);
        PAINT_DRAFT_SHIP.setAntiAlias(true);
        PAINT_DRAFT_SHIP.setStyle(Paint.Style.FILL);
        PAINT_DRAFT_SHIP.setShadowLayer(ROW_STROKE, 0, 0, 0xFF53A2F8);

        PAINT_SHIP.setColor(0xFF00DD73);
        PAINT_SHIP.setAntiAlias(true);
        PAINT_SHIP.setStyle(Paint.Style.FILL);
        PAINT_SHIP.setShadowLayer(ROW_STROKE, 0, 0, 0xFF00FF00);

        PAINT_WRONG_SHIP.setColor(0xFFFF0000);
        PAINT_WRONG_SHIP.setAntiAlias(true);
        PAINT_WRONG_SHIP.setStyle(Paint.Style.FILL);
        PAINT_WRONG_SHIP.setShadowLayer(ROW_STROKE, 0, 0, 0xFFFF0000);
    }

    public DrawBattleMap() {
        linePaint.setColor(0xFF001327);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(ROW_STROKE);

        textPaint.setColor(0xFFFFFFFF);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);

        previewPaint.setColor(0xFF00DD73);
        previewPaint.setAntiAlias(true);
        previewPaint.setStyle(Paint.Style.FILL);
        previewPaint.setShadowLayer(7, 0, 0, 0xFF00FF00);
        // draftShipPointer.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
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
//
//        text(1,5);
//        text(2,4);
//        text(3,3);
//        text(4,2);
//        text(5,1);
    }

    public void text(int size, int number) {

        int cellSize = 32;

        int posX, posY;

        if (state.isVertical()) {
            posX = cellSize;
            posY = 850 + size*50;
        } else {
            posX = reviewPlaceX + cellSize;
            posY = reviewPlaceY * size;
        }


        state.canvas.drawText(size + " x", posX - cellSize, posY, textPaint);
        posY -= cellSize - 5;
        posX += cellSize;

        Rect rect;
        for (int i = 0; i < number; i++) {
            rect = new Rect(posX, posY, posX + cellSize, posY + cellSize);
            state.canvas.drawRect(rect, previewPaint);

            posX += cellSize + 5;
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

    public void drawCell(Cell cell, Paint paint) {
        RectF rect = makeRect(cell);
        state.canvas.drawRect(rect, paint);
    }

    public void drawCell(int x, int y, Paint paint) {
        RectF rect = makeRect(x, y);
        state.canvas.drawRect(rect, paint);
    }

    public RectF makeRect(Cell cell) {
        float offset = (state.cellSizeY * FIELD_SIZE - 2 * CELL_OFFSET) / FIELD_SIZE;
        float posX = ROW_STROKE - 1 + cell.x * offset;
        float posY = ROW_STROKE - 1 + cell.y * offset;

        return new RectF(
                posX, posY,
                posX + offset - ROW_STROKE + 1, posY + offset - ROW_STROKE + 1);
    }

    public RectF makeRect(int x, int y) {
        float offset = (state.cellSizeY * FIELD_SIZE - 2 * CELL_OFFSET) / FIELD_SIZE;
        float posX = ROW_STROKE - 1 + x * offset;
        float posY = ROW_STROKE - 1 + y * offset;

        return new RectF(
                posX, posY,
                posX + offset - ROW_STROKE + 1, posY + offset - ROW_STROKE + 1);
    }
}
