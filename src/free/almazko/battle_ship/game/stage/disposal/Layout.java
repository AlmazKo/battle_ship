package free.almazko.battle_ship.game.stage.disposal;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import free.almazko.battle_ship.game.ui.Grid;
import free.almazko.battle_ship.game.ui.Styles;

/**
 * @author Almazko
 */
public class Layout {
    private Canvas canvas;
    protected final Grid grid;

    class Settings {

        public boolean isChanged = true;
        public int reviewPlaceX, reviewPlaceY;
        public int reviewCellSpacing;
        public int fontSize, reviewCellSize;
        public int shipCounter;

        public Paint textPaint = new Paint();
        public Paint availShipsPreview = new Paint();
        public Paint shipsCounter = new Paint();
        public Paint noAvailShipsPreview = new Paint();
        public Grid grid;
        protected Canvas canvas;

        Settings(Canvas canvas) {
            this.canvas = canvas;
            initStyles(canvas);
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


        public void initStyles(Canvas canvas) {

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

        protected void setCanvas(Canvas canvas) {
            if (this.canvas.getWidth() != canvas.getWidth()) {
                isChanged = true;
            }

            this.canvas = canvas;
        }

        protected void prepareGrid(Grid grid) {
            if (isChanged) {
                grid.setCellSpacing(Styles.gridCellOffset);
                grid.setPosition(0, 50);
            }
        }

        private boolean isVertical() {
            return canvas.getWidth() < canvas.getHeight();
        }

    }

    public Layout(Canvas canvas, Grid grid) {
        this.grid = grid;
        update(canvas);
    }

    public void update(Canvas canvas) {
        this.canvas = canvas;
        canvas.drawText("Расстановка кораблей", 150, 30, Styles.get("text_title"));
    }


}
