package free.almazko.battle_ship;

import android.graphics.Canvas;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/31/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BattleCanvas {
    public Grid playersGrid, opponentsGrid;
    private Canvas canvas;

    public BattleCanvas(Canvas canvas) {
        opponentsGrid = new Grid(canvas, Area.SIZE);
        playersGrid = new Grid(canvas, Area.SIZE, 300);
        playersGrid.setPosition(10, opponentsGrid.size() + 10);


        opponentsGrid.setCellSpacing(Styles.gridCellOffset);
        playersGrid.setCellSpacing(2);
    }

    public void drawScore(final List<Ship> playersShips, final List<Ship> opponentsShip) {

    }

    public void update(Canvas canvas, Area protagonistArea, Area knownArea) {
        this.canvas = canvas;
        playersGrid.changeCanvas(canvas);
        opponentsGrid.changeCanvas(canvas);

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

    public Cell recognizeOpponentCell(float x, float y) {
        return opponentsGrid.recognizeCell(x,y);
    }
}
