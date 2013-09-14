package free.almazko.battle_ship.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class InitCanvas {

    protected int reviewPlaceX, reviewPlaceY;
    protected int reviewCellSpacing;
    protected int fontSize, reviewCellSize;
    protected int shipCounter;

    protected Paint textPaint = new Paint();
    protected Paint availShipsPreview = new Paint();
    protected Paint shipsCounter = new Paint();
    protected Paint noAvailShipsPreview = new Paint();
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

        posY += (reviewCellSize + (reviewCellSpacing * 2)) * (shipSize - 1);

        Rect rect;
        posX += reviewCellSize * 3;
        int axis = posX;


        for (int i = 0; i < numberAvailShips; i++) {

            rect = new Rect(posX, posY, posX + shipCounter, posY + reviewCellSize);
            canvas.drawRect(rect, shipsCounter);

            posX -= shipCounter + reviewCellSpacing;
        }

        Paint shipsPreviewPainter;
        if (numberAvailShips < 1) {
            shipsPreviewPainter = noAvailShipsPreview;
        } else {
            shipsPreviewPainter = availShipsPreview;
        }
        posX = axis + reviewCellSpacing * 3;

        for (int i = 0; i < shipSize; i++) {
            rect = new Rect(posX, posY, posX + reviewCellSize, posY + reviewCellSize);
            canvas.drawRect(rect, shipsPreviewPainter);

            posX += reviewCellSize + reviewCellSpacing;
        }
    }

    public void update(Canvas canvas) {
        this.canvas = canvas;
        grid.changeCanvas(canvas);

        canvas.drawText("Расстановка кораблей", 150, 50, Styles.get("text_title"));
    }

    protected void initVars() {

        int minCanvasSize = Math.min(canvas.getHeight(), canvas.getWidth());

        if (minCanvasSize < 400) {
            reviewCellSpacing = 1;
            fontSize = 12;
            reviewCellSize = 14;
            shipCounter = 3;
        } else if (minCanvasSize < 700) {
            reviewCellSpacing = 2;
            fontSize = 14;
            reviewCellSize = 14;
            shipCounter = 4;
        } else {
            reviewCellSpacing = 3;
            fontSize = 16;
            reviewCellSize = 14;
            shipCounter = 4;
        }

        shipsCounter.setColor(0xFFFF0000);
        shipsCounter.setAntiAlias(true);
        shipsCounter.setStyle(Paint.Style.FILL);
        shipsCounter.setShadowLayer(reviewCellSpacing, 0, 0, 0xFFFF0000);

        availShipsPreview.setColor(0xFF00DD73);
        availShipsPreview.setAntiAlias(true);
        availShipsPreview.setStyle(Paint.Style.FILL);
        availShipsPreview.setShadowLayer(reviewCellSpacing, 0, 0, 0xFF00FF00);

        noAvailShipsPreview.setColor(0xFF666666);
        noAvailShipsPreview.setAntiAlias(true);
        noAvailShipsPreview.setStyle(Paint.Style.FILL);
        noAvailShipsPreview.setShadowLayer(reviewCellSpacing, 0, 0, 0xFFAAAAAA);
    }


    private boolean isVertical() {
        return canvas.getWidth() < canvas.getHeight();
    }

}
