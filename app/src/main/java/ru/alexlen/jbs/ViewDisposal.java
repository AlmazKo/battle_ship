package ru.alexlen.jbs;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.alexlen.jbs.event.GameEvent;
import ru.alexlen.jbs.game.Area;
import ru.alexlen.jbs.game.Cell;
import ru.alexlen.jbs.game.Ship;
import ru.alexlen.jbs.ui.Grid;
import ru.alexlen.jbs.ui.Styles;

/**
 * @author Almazko
 */
public class ViewDisposal extends AbstractView {

    final static String TAG = "ViewDisposal";
    Grid grid;
    private Area lastArea;


    Rect canvasArea;

    public ViewDisposal(GameView view) {
        super(view);

        grid = new Grid(10, 300);
        grid.setPosition(0, 100);

    }

    class ShipsDrawer implements AreaRenderTask {

        final Area area;

        private ShipsDrawer(Area area) {
            this.area = area;
        }

        @Override
        public void draw(@NotNull final Canvas canvas) {

            canvas.drawText("Расстановка кораблей", 50, 50, Styles.get("text_title"));


            Grid.Drawer drawer = grid.getDrawer(canvas);
            //Log.i("MAP", area.toString());

            for (int x = 0; x < Area.SIZE; x++) {
                for (int y = 0; y < Area.SIZE; y++) {
                    switch (area.get(x, y)) {
                        case Area.SHIPS_AREA:
                            drawer.drawCell(x, y, Styles.get("ships_area"));
                            break;
                        default:
                    }
                }
            }


            for (int x = 0; x < Area.SIZE; x++) {
                for (int y = 0; y < Area.SIZE; y++) {
                    switch (area.get(x, y)) {
                        case Area.SHIP:
                            drawer.drawCell(x, y, Styles.get("ship"));
                            break;
                        default:
                            drawer.drawCell(x, y, Styles.get("cell_blank"));
                    }
                }
            }
        }

        @Nullable @Override public Rect getArea() {
            return grid.getRect();
        }
    }

    public void drawCommittedShips(@NotNull Area area) {

        detectSize();
        lastArea = area.cloneArea();
        view.addRenderTask(new ShipsDrawer(lastArea));
    }

    boolean needDetect = true;

    private void detectSize() {
        if (!needDetect) return;

        needDetect = false;

        view.addRenderTask(new RenderTask() {
            @Override public void draw(@NotNull Canvas canvas) {

                canvasArea = canvas.getClipBounds();
                canvasArea.offsetTo(3, 3);

                grid.setSize(Math.min(canvasArea.right, canvasArea.bottom) - 1);
            }
        });

    }


    class DraftShipDrawer implements RenderTask {

        final         Ship    draftShip;
        private final boolean possible;

        DraftShipDrawer(Ship draftShip, boolean possible) {
            this.draftShip = draftShip;
            this.possible = possible;
        }

        @Override
        public void draw(@NotNull final Canvas canvas) {
            // eDrawCommittedShips.draw(canvas);

            Grid.Drawer drawer = grid.getDrawer(canvas);

            Paint paint;
            if (possible) {
                paint = Styles.get("draft_ship");
            } else {
                paint = Styles.get("wrong_ship");
            }

            for (Cell cell : draftShip.getCells()) {
                drawer.drawCell(cell, paint);
            }
        }

    }

    public void drawDraftShip(@NotNull final Ship draftShip, final boolean possible) {

        view.addRenderTask(new RenderTaskSet(grid.getRect(),
                new ShipsDrawer(lastArea),
                new DraftShipDrawer(draftShip.copy(), possible)
        ));
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


    private static final class TouchInfoTask implements AreaRenderTask {

        private final Rect rect;
        private final int  x;
        private final int  y;

        TouchInfoTask(final MotionEvent event, Rect area) {

            x = (int) event.getX();
            y = (int) event.getY();
            rect = new Rect(area);
            rect.left = rect.right - 200;
            rect.top = rect.bottom - 20;
        }

        @Override public void draw(@NotNull Canvas canvas) {
            canvas.drawRect(getArea(), Styles.get("none"));
            canvas.drawText(x + ":" + y, rect.left, rect.bottom, Styles.get("text_main"));
        }

        @Nullable @Override public Rect getArea() {
            return rect;
        }
    }

    @Override
    public boolean onTouch(View v, final MotionEvent event) {

        view.addRenderTask(new TouchInfoTask(event, canvasArea));

        if (cellActionListener == null) return super.onTouch(v, event);
        if (grid == null) return true;

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
