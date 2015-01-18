package ru.alexlen.jbs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import org.jetbrains.annotations.NotNull;
import ru.alexlen.jbs.event.GameEvent;
import ru.alexlen.jbs.game.Area;
import ru.alexlen.jbs.game.Cell;
import ru.alexlen.jbs.game.Ship;
import ru.alexlen.jbs.ui.Grid;
import ru.alexlen.jbs.ui.Styles;

/**
 * @author Almazko
 */
public class ViewDisposal extends AbstractViewController {

    final static String TAG = "DisposalView";
    Area area;
    Grid grid;

    public ViewDisposal(GameView view) {
        super(view);
    }

    class Style {

        protected int reviewPlaceX, reviewPlaceY;
        protected int reviewCellSpacing;
        protected int fontSize, reviewCellSize;
        protected int shipCounter;

        protected Paint textPaint           = new Paint();
        protected Paint availShipsPreview   = new Paint();
        protected Paint shipsCounter        = new Paint();
        protected Paint noAvailShipsPreview = new Paint();
        protected Canvas canvas;

        public Style(Canvas canvas) {
            this.canvas = canvas;

            initStyles();
        }


        protected void initStyles() {

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

    }


    RenderTask eDrawCommittedShips = new RenderTask() {
        @Override
        public void draw(@NotNull final Canvas canvas) {

            canvas.drawColor(Color.BLACK);
            canvas.drawText("Расстановка кораблей", 150, 50, Styles.get("text_title"));
            grid = new Grid(canvas, 10);
            grid.setPosition(0,100);


            //Log.i("MAP", area.toString());

            for (int x = 0; x < Area.SIZE; x++) {
                for (int y = 0; y < Area.SIZE; y++) {
                    switch (area.get(x, y)) {
                        case Area.SHIPS_AREA:
                            grid.drawCell(x, y, Styles.get("ships_area"));
                            break;
                        default:
                    }
                }
            }


            for (int x = 0; x < Area.SIZE; x++) {
                for (int y = 0; y < Area.SIZE; y++) {
                    switch (area.get(x, y)) {
                        case Area.SHIP:
                            grid.drawCell(x, y, Styles.get("ship"));
                            break;
                        default:
                            grid.drawCell(x, y, Styles.get("cell_blank"));
                    }
                }
            }
        }

    };

    public void drawCommittedShips(@NotNull Area area) {
        this.area = area;
        view.addRenderTask(eDrawCommittedShips);
    }


    public void drawDraftShip(@NotNull final Ship draftShip, final boolean possible) {
        view.addRenderTask(new RenderTask() {
            @Override
            public void draw(@NotNull final Canvas canvas) {
                eDrawCommittedShips.draw(canvas);


                Paint paint;
                if (possible) {
                    paint = Styles.get("draft_ship");
                } else {
                    paint = Styles.get("wrong_ship");
                }

                Log.d("RenderTask", "draft: " + draftShip);
                for (Cell cell : draftShip.getCells()) {
                    grid.drawCell(cell, paint);
                }
            }
        });

    }

    private boolean isVertical(Canvas canvas) {
        return canvas.getWidth() < canvas.getHeight();
    }

//    private void drawText(int shipSize, int numberAvailShips) {
//
//        if (isVertical(canvas)) {
//            reviewPlaceX = 10;
//            reviewPlaceY = fontSize;
//        } else {
//            reviewPlaceX = fontSize;
//            reviewPlaceY = 10;
//        }
//
//        int posX = reviewPlaceX, posY = reviewPlaceY;
//
//        posY += (reviewCellSize + (reviewCellSpacing * 2)) * (shipSize - 1);
//
//        Rect rect;
//        posX += reviewCellSize * 3;
//        int axis = posX;
//
//
//        for (int i = 0; i < numberAvailShips; i++) {
//
//            rect = new Rect(posX, posY, posX + shipCounter, posY + reviewCellSize);
//            canvas.drawRect(rect, shipsCounter);
//
//            posX -= shipCounter + reviewCellSpacing;
//        }
//
//        Paint shipsPreviewPainter;
//        if (numberAvailShips < 1) {
//            shipsPreviewPainter = noAvailShipsPreview;
//        } else {
//            shipsPreviewPainter = availShipsPreview;
//        }
//        posX = axis + reviewCellSpacing * 3;
//
//        for (int i = 0; i < shipSize; i++) {
//            rect = new Rect(posX, posY, posX + reviewCellSize, posY + reviewCellSize);
//            canvas.drawRect(rect, shipsPreviewPainter);
//
//            posX += reviewCellSize + reviewCellSpacing;
//        }
//    }

    Cell cell;
    Cell lastCell;
    int  lastAction;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (cellActionListener == null) return super.onTouch(v, event);
        Log.v(TAG, "onTouch: " + event);
        cell = grid.recognizeCell(event.getX(), event.getY());


        //if (cell == null) return true;// TEMP

         if (cell == lastCell && event.getAction() == lastAction) return true;

        lastCell = cell;
        lastAction = event.getAction();

        Log.d(TAG, MotionEvent.actionToString(event.getAction()) + ": " + cell);
        cellActionListener.onCellAction(new GameEvent(event, cell));

        return true;
    }
}
