package ru.alexlen.jbs;

import android.util.Log;
import ru.alexlen.jbs.event.CellAction;
import ru.alexlen.jbs.event.CellActionListener;
import ru.alexlen.jbs.game.Area;
import ru.alexlen.jbs.game.BattleLogic;
import ru.alexlen.jbs.game.Cell;
import ru.alexlen.jbs.game.Player;

/**
 * @author Almazko
 */
public class ControllerBattle implements CellActionListener {

    private       GameActivity mActivity;
    private final BattleLogic  logic;
    private       Area         mPayerArea;
    private       Player       mOpponent;
    private       ViewBattle   mView;

    private Area knownArea = new Area();

    public ControllerBattle(GameActivity activity, BattleLogic logic, Area playerArea, Player opponent,
                            ViewBattle viewController) {
        mActivity = activity;
        this.logic = logic;
        mPayerArea = playerArea;
        mOpponent = opponent;
        mView = viewController;
        mView.setCellActionListener(this);
    }


    public void start() {
        mView.drawCommittedShips(knownArea, mPayerArea);
    }

    @Override
    public void onCellAction(CellAction action) {
        if (!action.isDown()) {
            return;
        }

        Cell cell = action.getCell();
        if (cell == null) return;

        BattleLogic.StrikeResult result = logic.strike(action.getCell());
        Log.i("ControllerBattle", "BattleLogic: " + result + " in " + cell);
        turn(knownArea, cell, result);
        mView.drawCommittedShips(knownArea, mPayerArea);


        if (result != BattleLogic.StrikeResult.MISS) return;

        waitOpponent();
    }

    private void waitOpponent() {
        Cell cell = mOpponent.strike();
        BattleLogic.StrikeResult result = logic.strike(cell);

        Log.i("ControllerBattle", "BattleLogic opponent: " + result + " in " + cell);
        turn(mPayerArea, cell, result);
        mView.drawCommittedShips(knownArea, mPayerArea);

        if (result != BattleLogic.StrikeResult.MISS) waitOpponent();
    }

    private void turn(Area area, Cell cell, BattleLogic.StrikeResult result) {
        int areaFlags = area.get(cell.x, cell.y);

        switch (result) {
            case MISS:
                areaFlags = areaFlags | Area.FIRED;
                break;

            case ALREADY:
                return;

            case HIT:
                areaFlags = areaFlags | Area.SHIP | Area.FIRED;
                break;
            case KILL:
                areaFlags = areaFlags | Area.SHIP | Area.FIRED;
                break;
        }


        area.set(cell.x, cell.y, areaFlags);
    }

}
