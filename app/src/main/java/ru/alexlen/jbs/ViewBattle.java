package ru.alexlen.jbs;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.alexlen.jbs.event.GameEvent;
import ru.alexlen.jbs.game.Area;
import ru.alexlen.jbs.game.Cell;
import ru.alexlen.jbs.ui.Grid;
import ru.alexlen.jbs.ui.Styles;

import static ru.alexlen.jbs.game.Area.*;

/**
 * @author Almazko
 */
public class ViewBattle extends AbstractView {

    private class ShipsDrawer implements RenderTask {

        final Area knownArea;
        final Area protagonistArea;


        private ShipsDrawer(Area knownArea, Area protagonistArea) {
            this.knownArea = knownArea;
            this.protagonistArea = protagonistArea;
        }

        @Override
        public void draw(@NotNull final Canvas canvas) {


            canvas.drawColor(0x99000000);
            canvas.drawText("Бой", 50, 50, Styles.get("text_title"));

            playersGrid.setPosition(10, opponentsGrid.size() + 10);


            drawShips(protagonistArea, playersGrid.getDrawer(canvas));
            drawShips(knownArea, opponentsGrid.getDrawer(canvas));

        }

        @Override public int getZIndex() {
            return 0;
        }

        @Override public int getId() {
            return 0;
        }


        private void drawShips(Area area, Grid.Drawer grid) {


            int target;
            int x;
            int y;

            for (x = 0; x < SIZE; x++) {
                for (y = 0; y < SIZE; y++) {
                    if (area.is(x, y, SHIPS_AREA)) {
                        grid.drawCell(x, y, Styles.get("ships_area"));
                    }
                }

            }

            for (x = 0; x < SIZE; x++) {
                for (y = 0; y < SIZE; y++) {
                    target = area.get(x, y);
                    if ((target & FIRED) > 0 && (target & SHIP) <= 0) {
                        grid.drawCell(x, y, Styles.get("fared_area"));
                    }
                }
            }

            for (x = 0; x < SIZE; x++) {
                for (y = 0; y < SIZE; y++) {
                    grid.drawCell(x, y, Styles.get("cell_blank"));
                }
            }


            for (x = 0; x < SIZE; x++) {
                for (y = 0; y < SIZE; y++) {
                    target = area.get(x, y);
                    if ((target & SHIP) > 0) {

                        if ((target & FIRED) > 0) {
                            grid.drawCell(x, y, Styles.get("wrong_ship"));
                        } else {
                            grid.drawCell(x, y, Styles.get("mini_ship"));
                        }
                    }
                }
            }

        }
    }

    private boolean isEnd;
    final static String TAG = "ViewBattle";
    Grid opponentsGrid;
    Grid playersGrid;
    Rect canvasArea;

    public ViewBattle(GameView view) {
        super(view);
        opponentsGrid = new Grid(Area.SIZE, 100);
        playersGrid = new Grid(Area.SIZE, 300);
    }

    public void drawCommittedShips(Area knownArea, Area protagonistArea) {

        detectSize();
        view.addRenderTask(new ShipsDrawer(knownArea.cloneArea(), protagonistArea.cloneArea()));
    }


    boolean needDetect = true;

    private void detectSize() {
        if (!needDetect) return;

        needDetect = false;

        view.addRenderTask(new RenderTask() {
            int id = view.getUniqueId();

            @Override public void draw(@NotNull Canvas canvas) {

                canvasArea = canvas.getClipBounds();

                int mainSize;
                //is vertical
                if (canvasArea.bottom > canvasArea.right) {

                    mainSize = canvasArea.right;

                    opponentsGrid.setSize(mainSize - 1);
                    playersGrid.setPosition(1, mainSize);
                    playersGrid.setSize(canvasArea.bottom - (int) (mainSize * 1.1));
                } else {
                    mainSize = canvasArea.bottom;

                    opponentsGrid.setSize(mainSize - 1);
                    playersGrid.setPosition(mainSize, 1);
                    playersGrid.setSize(canvasArea.right - mainSize);
                }

            }

            @Override public int getZIndex() {
                return 0;
            }

            @Override public int getId() {
                return id;
            }
        });

    }

    public void drawWin() {

        view.addRenderTask(new ControlTask() {
            int id = view.getUniqueId();
            @Override
            public void draw(@NotNull final Canvas canvas) {

                canvas.drawColor(0x99000000);
                canvas.drawText("Win battle!", 150, 450, Styles.get("text_win_title"));
            }

            @Override public int getZIndex() {
                return 10000;
            }

            @Override public int getId() {
                return id;
            }

            @Nullable @Override public Rect getArea() {
                return new Rect(canvasArea);
            }
        });

        isEnd = true;

        view.setOnTouchListener(null);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {

                view.removeAllTasks();
                ((GameActivity) view.getContext()).restart();
                return true;
            }
        });
    }

    Cell cell;

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (isEnd) return false;

        if (cellActionListener == null) return super.onTouch(v, event);
        Log.v(TAG, "onTouch: " + event);
        cell = opponentsGrid.recognizeCell(event.getX(), event.getY());

        Log.d(TAG, MotionEvent.actionToString(event.getAction()) + ": " + cell);
        cellActionListener.onCellAction(new GameEvent(event, cell));

        return true;
    }
}
