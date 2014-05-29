package free.almazko.battle_ship.game.stage.disposal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import free.almazko.battle_ship.game.*;
import free.almazko.battle_ship.game.event.GameEvent;
import free.almazko.battle_ship.game.stage.ControllerInterface;
import free.almazko.battle_ship.game.ui.Grid;
import free.almazko.battle_ship.game.ui.Styles;

/**
 * DisposalView
 */
public class DisposalView extends View implements View.OnTouchListener {
    ControllerInterface controller;
    Grid battleGrid;
    Canvas canvas;

//    class Settings {
//
//        public boolean isChanged = true;
//        public int reviewPlaceX, reviewPlaceY;
//        public int reviewCellSpacing;
//        public int fontSize, reviewCellSize;
//        public int shipCounter;
//
//        public Paint textPaint = new Paint();
//        public Paint availShipsPreview = Styles.get("avail_ships");
//        public Paint shipsCounter = Styles.get("ships_counter");
//        public Paint noAvailShipsPreview = Styles.get("not_avail_ships");
//        public Grid grid;
//        protected Canvas canvas;
//
//        Settings(Canvas canvas) {
//            this.canvas = canvas;
//            initStyles(canvas);
//        }
//
////        public void drawText(int shipSize, int numberAvailShips) {
////
////            if (isVertical()) {
////                reviewPlaceX = 10;
////                reviewPlaceY = fontSize;
////            } else {
////                reviewPlaceX = fontSize;
////                reviewPlaceY = 10;
////            }
////
////            int posX = reviewPlaceX, posY = reviewPlaceY;
////
////            posY += (reviewCellSize + (reviewCellSpacing * 2)) * (shipSize - 1);
////
////            Rect rect;
////            posX += reviewCellSize * 3;
////            int axis = posX;
////
////
////            for (int i = 0; i < numberAvailShips; i++) {
////
////                rect = new Rect(posX, posY, posX + shipCounter, posY + reviewCellSize);
////                canvas.drawRect(rect, shipsCounter);
////
////                posX -= shipCounter + reviewCellSpacing;
////            }
////
////            Paint shipsPreviewPainter;
////            if (numberAvailShips < 1) {
////                shipsPreviewPainter = noAvailShipsPreview;
////            } else {
////                shipsPreviewPainter = availShipsPreview;
////            }
////            posX = axis + reviewCellSpacing * 3;
////
////            for (int i = 0; i < shipSize; i++) {
////                rect = new Rect(posX, posY, posX + reviewCellSize, posY + reviewCellSize);
////                canvas.drawRect(rect, shipsPreviewPainter);
////
////                posX += reviewCellSize + reviewCellSpacing;
////            }
////        }
////
////
////        public void initStyles(Canvas canvas) {
////
////            int minCanvasSize = Math.min(canvas.getHeight(), canvas.getWidth());
////
////            if (minCanvasSize < 400) {
////                reviewCellSpacing = 1;
////                fontSize = 12;
////                reviewCellSize = 14;
////                shipCounter = 3;
////            } else if (minCanvasSize < 700) {
////                reviewCellSpacing = 2;
////                fontSize = 14;
////                reviewCellSize = 14;
////                shipCounter = 4;
////            } else {
////                reviewCellSpacing = 3;
////                fontSize = 16;
////                reviewCellSize = 14;
////                shipCounter = 4;
////            }
////        }
//
//        protected void setCanvas(Canvas canvas) {
//            if (this.canvas.getWidth() != canvas.getWidth()) {
//                isChanged = true;
//            }
//
//            this.canvas = canvas;
//        }
//
//        protected void prepareGrid(Grid grid) {
//            if (isChanged) {
//                grid.setCellSpacing(Styles.gridCellOffset);
//                grid.setPosition(0, 50);
//            }
//        }
//
//        private boolean isVertical() {
//            return canvas.getWidth() < canvas.getHeight();
//        }
//
//    }

    public DisposalView(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public void setController(ControllerInterface controller) {
        this.controller = controller;
    }


    protected void prepareGrid(Grid grid) {
        grid.setCellSpacing(Styles.gridCellOffset);
        grid.setPosition(0, 50);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (battleGrid == null) {
            battleGrid = new Grid(canvas, Area.SIZE);
            prepareGrid(battleGrid);
            this.canvas = canvas;
        } else {
            this.canvas = canvas;
        }

        controller.reDraw();
    }

    public boolean onTouch(View v, MotionEvent event) {

        Log.i("BATTLE", "GameView new event");
        int action = event.getAction();

        Cell cell = null;
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            cell = battleGrid.recognizeCell(event.getX(), event.getY());
            Log.d("BATTLE", "GameView new event: recognize grid cell: " + cell);
        }

        GameEvent gameEvent = new GameEvent(event, cell);

        controller.onGameTouch(gameEvent);

        return true;
    }


    public void drawCommittedShips(ShipsArea shipsArea) {

        Area area = shipsArea.getArea();

        for (int x = 0; x < Area.SIZE; x++) {
            for (int y = 0; y < Area.SIZE; y++) {
                switch (area.get(x, y)) {
                    case Area.SHIP:
                        battleGrid.drawCell(x, y, Styles.get("ship"));
                        break;
                    case Area.SHIPS_AREA:
                        battleGrid.drawCell(x, y, Styles.get("ships_area"));
                        break;
                    default:
                        battleGrid.drawCell(x, y, Styles.get("cell_blank"));
                }
            }
        }
    }

    public void drawDraftShip(Ship draftShip) {

        if (draftShip == null) {
            return;
        }

        Paint paint;
        if (draftShip.isImpossible()) {
            paint = Styles.get("wrong_ship");
        } else {
            paint = Styles.get("draft_ship");
        }

        for (Cell cell : draftShip.getCells()) {
            battleGrid.drawCell(cell, paint);
        }
    }
}
