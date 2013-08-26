package free.almazko.battle_ship;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class InitController {
    public static final Paint PAINT_SHIP = new Paint();
    public static final Paint PAINT_DRAFT_SHIP = new Paint();
    public static final Paint PAINT_WRONG_SHIP = new Paint();
    public static final Paint PAINT_SHIPS_AREA = new Paint();

    int reviewPlaceX, reviewPlaceY;
    int reviewCellSpacing;
    int fontSize, reviewCellSize;

    private Canvas canvas;
    private Grid grid;

    public InitController(Canvas canvas) {
        grid = new Grid(canvas, BattleMap.SIZE);
        BattleMap battleMap = new BattleMap(grid, this);
    }


    private void initPaints() {
        PAINT_SHIPS_AREA.setStyle(Paint.Style.FILL);
        PAINT_SHIPS_AREA.setColor(0xFF022244);

        PAINT_DRAFT_SHIP.setColor(0xFF53A2F8);
        PAINT_DRAFT_SHIP.setAntiAlias(true);
        PAINT_DRAFT_SHIP.setStyle(Paint.Style.FILL);
        PAINT_DRAFT_SHIP.setShadowLayer(grid.p, 0, 0, 0xFF53A2F8);

        PAINT_SHIP.setColor(0xFF00DD73);
        PAINT_SHIP.setAntiAlias(true);
        PAINT_SHIP.setStyle(Paint.Style.FILL);
        PAINT_SHIP.setShadowLayer(ROW_STROKE, 0, 0, 0xFF00FF00);

        PAINT_WRONG_SHIP.setColor(0xFFFF0000);
        PAINT_WRONG_SHIP.setAntiAlias(true);
        PAINT_WRONG_SHIP.setStyle(Paint.Style.FILL);
        PAINT_WRONG_SHIP.setShadowLayer(ROW_STROKE, 0, 0, 0xFFFF0000);
    }


    public void text(int size, int number) {

        if (isVertical()) {
            reviewPlaceX = 10;
            reviewPlaceY = grid.size() + fontSize;
        } else {
            reviewPlaceX = grid.size() + fontSize;
            reviewPlaceY = 10;
        }

        int posX = reviewPlaceX, posY = reviewPlaceY;

        posY += (reviewCellSize + (reviewCellSpacing * 2)) * size;


        state.canvas.drawText(size + " x", posX, posY, textPaint);

        posY -= reviewCellSize - reviewCellSpacing;
        posX += reviewCellSize * 2;

        Rect rect;
        for (int i = 0; i < number; i++) {
            rect = new Rect(posX, posY, posX + reviewCellSize, posY + reviewCellSize);
            state.canvas.drawRect(rect, previewPaint);

            posX += reviewCellSize + reviewCellSpacing;
        }
    }


    private boolean isVertical() {
        return canvas.getWidth() < canvas.getHeight();
    }
}
