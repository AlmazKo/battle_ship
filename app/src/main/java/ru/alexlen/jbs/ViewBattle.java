package ru.alexlen.jbs;

import android.graphics.Canvas;
import android.graphics.Color;
import org.jetbrains.annotations.NotNull;
import ru.alexlen.jbs.game.Area;
import ru.alexlen.jbs.ui.Grid;
import ru.alexlen.jbs.ui.Styles;

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


            drawProtagonistShips(protagonistArea, playersGrid);
            drawShips(knownArea, opponentsGrid);

        }


        private void drawShips(Area area, Grid grid) {

            for (int x = 0; x < Area.SIZE; x++) {
                for (int y = 0; y < Area.SIZE; y++) {
                    int target = area.get(x, y);

                    if ((target & Area.SHIP) > 0) {
                        if ((target & Area.FIRED) > 0) {
                            grid.drawCell(x, y, Styles.get("wrong_ship"));
                        } else {
                            grid.drawCell(x, y, Styles.get("ship"));
                        }
                    } else {
                        if ((target & Area.FIRED) > 0) {
                            grid.drawCell(x, y, Styles.get("fared_area"));
                        } else {
                            grid.drawCell(x, y, Styles.get("ships_area"));
                        }
                    }
                }
            }
        }

        private void drawProtagonistShips(Area area, Grid grid) {
            for (int x = 0; x < Area.SIZE; x++) {
                for (int y = 0; y < Area.SIZE; y++) {
                    int target = area.get(x, y);


                    if ((target & Area.SHIP) > 0) {
                        if ((target & Area.FIRED) > 0) {
                            grid.drawCell(x, y, Styles.get("mini_wrong_ship"));
                        } else {
                            grid.drawCell(x, y, Styles.get("mini_ship"));
                        }
                    } else {
                        if ((target & Area.FIRED) > 0) {
                            grid.drawCell(x, y, Styles.get("mini_fared_area"));
                        } else {
                            grid.drawCell(x, y, Styles.get("mini_ships_area"));
                        }
                    }
                }
            }
        }
    };

    public void drawCommittedShips(Area knownArea, Area protagonistArea) {
        this.protagonistArea = protagonistArea;
        this.knownArea = knownArea;
        view.addRenderTask(eDrawCommittedShips);
    }

    public ViewBattle(GameView view) {
        super(view);
    }
}
