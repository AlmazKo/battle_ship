package ru.alexlen.jbs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import org.jetbrains.annotations.NotNull;
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

    final static String TAG = "ViewBattle";


    Area knownArea;
    Area protagonistArea;
    Grid opponentsGrid;
    Grid playersGrid;

    RenderTask eDrawCommittedShips = new RenderTask() {
        @Override
        public void draw(@NotNull final Canvas canvas) {


            canvas.drawColor(Color.BLACK);
            canvas.drawText("Бой", 50, 50, Styles.get("text_title"));


            opponentsGrid = new Grid(canvas, Area.SIZE);
            playersGrid = new Grid(canvas, Area.SIZE, 300);
            playersGrid.setPosition(10, opponentsGrid.size() + 10);


            drawShips(protagonistArea, playersGrid);
            drawShips(knownArea, opponentsGrid);

        }


        private void drawShips(Area area, Grid grid) {


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
                    //grid.drawCell(x, y, Styles.get("mini_ships_area"));
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
    };


    public ViewBattle(GameView view) {
        super(view);
    }


    public void drawCommittedShips(Area knownArea, Area protagonistArea) {
        this.protagonistArea = protagonistArea;
        this.knownArea = knownArea;
        view.addRenderTask(eDrawCommittedShips);
    }


    Cell cell;
    Cell lastCell;
    int  lastAction;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (cellActionListener == null) return super.onTouch(v, event);
        Log.v(TAG, "onTouch: " + event);
        cell = opponentsGrid.recognizeCell(event.getX(), event.getY());

        Log.d(TAG, MotionEvent.actionToString(event.getAction()) + ": " + cell);
        cellActionListener.onCellAction(new GameEvent(event, cell));

        return true;
    }
}
