package free.almazko.battle_ship.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class InitCanvas  {

    protected int reviewPlaceX, reviewPlaceY;
    protected int reviewCellSpacing;
    protected int fontSize, reviewCellSize;

    protected Paint textPaint = new Paint();
    protected Paint previewPaint = new Paint();
    protected Grid grid;
    protected Canvas canvas;

    public InitCanvas(Grid grid, Canvas canvas) {
        this.grid = grid;
        this.canvas = canvas;

        initVars();
    }

    public void drawText(int shipSize, int numberAvailShips) {

        if (isVertical()) {
            reviewPlaceX = 10;
            reviewPlaceY = fontSize;
        } else {
            reviewPlaceX = fontSize;
            reviewPlaceY = 10;
        }

        int posX = reviewPlaceX, posY = reviewPlaceY;

        posY += (reviewCellSize + (reviewCellSpacing * 2)) * shipSize;


        canvas.drawText(shipSize + " x", posX, posY, textPaint);

        posY -= reviewCellSize - reviewCellSpacing;
        posX += reviewCellSize * 2;

        Rect rect;
        for (int i = 0; i < numberAvailShips; i++) {
            rect = new Rect(posX, posY, posX + reviewCellSize, posY + reviewCellSize);
            canvas.drawRect(rect, previewPaint);

            posX += reviewCellSize + reviewCellSpacing;
        }
    }

    public void update(Canvas canvas) {
        this.canvas = canvas;
        grid.changeCanvas(canvas);

        canvas.drawText("Расстановка кораблей", 150, 50, Styles.get("text_title"));
    }

    protected void initVars() {
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setAntiAlias(true);

        reviewCellSpacing = 2;
        fontSize = 12;
        reviewCellSize = 12;

        textPaint.setTextSize(fontSize);

        previewPaint.setColor(0xFF00DD73);
        previewPaint.setAntiAlias(true);
        previewPaint.setStyle(Paint.Style.FILL);
        previewPaint.setShadowLayer(reviewCellSpacing, 0, 0, 0xFF00FF00);

    }


    private boolean isVertical() {
        return canvas.getWidth() < canvas.getHeight();
    }

}
