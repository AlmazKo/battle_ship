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

    public BattleCanvas(Canvas canvas, Grid playersGrid, Grid opponentsGrid) {
        this.playersGrid = playersGrid;
        this.opponentsGrid = opponentsGrid;
    }

    public void drawScore(final List<Ship> playersShips, final List<Ship> opponentsShip) {

    }


    public void update(Canvas canvas) {
        this.canvas = canvas;
        playersGrid.changeCanvas(canvas);
        opponentsGrid.changeCanvas(canvas);
    }


    public void drawGrids(){
        playersGrid.draw(Styles.get("mini_grid_line"));
        opponentsGrid.draw(Styles.get("grid_line"));
    }

}
