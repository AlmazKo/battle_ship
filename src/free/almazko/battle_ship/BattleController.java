package free.almazko.battle_ship;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/31/13
 * Time: 1:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BattleController extends AbstractController {

    private DrawView parentView;
    private BattleCanvas canvas;
    private Grid playersGrid, opponentsGrid;
    BattleMap battleMap;
    Opponent opponent = new Opponent();

    public BattleController(Canvas canvas, DrawView parentView, Area playersArea, Area opponentsArea) {

        opponentsGrid = new Grid(canvas, Area.SIZE);

        playersGrid = new Grid(canvas, Area.SIZE, 300);
        playersGrid.setPosition(opponentsGrid.size() + 50, 50);


        this.canvas = new BattleCanvas(canvas, playersGrid, opponentsGrid);
        this.parentView = parentView;
        battleMap = new BattleMap(this.canvas, playersArea, opponentsArea);
    }

    public void onDraw(Canvas canvas) {

        this.canvas.update(canvas);
        this.canvas.drawGrids();
        this.battleMap.draw();

        if (!battleMap.isProtagonist()) {
            battleMap.strike(opponent.makeMove());
            parentView.reDraw();
        }

    }

    public boolean onTouch(View v, MotionEvent event) {

        if (!battleMap.isProtagonist()) {
            return false;
        }

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            Cell cell = opponentsGrid.recognizeCell(event.getX(), event.getY());
            BattleMap.StrikeResult result = battleMap.strike(cell);
        }


        parentView.reDraw();
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
