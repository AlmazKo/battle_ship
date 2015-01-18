package ru.alexlen.jbs;

import ru.alexlen.jbs.event.CellAction;
import ru.alexlen.jbs.event.CellActionListener;
import ru.alexlen.jbs.game.Area;
import ru.alexlen.jbs.game.BattleLogic;
import ru.alexlen.jbs.game.Player;
import ru.alexlen.jbs.game.ShipsArea;

/**
 * @author Almazko
 */
public class ControllerBattle implements CellActionListener {

    private       GameActivity mActivity;
    private final BattleLogic  logic;
    private       Area         mPayerArea;
    private       Player       mOpponent;
    private       ViewBattle   mView;

    private ShipsArea opponentsArea = new ShipsArea();

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
        mView.drawCommittedShips(opponentsArea.getArea(), mPayerArea);
    }

    @Override
    public void onCellAction(CellAction action) {
        if (!action.isDown()) {
            return;
        }

//
//        final Cell cell = action.getCell();
//        BattleLogic.StrikeResult result = logic.strike(action.getCell());
//        knownArea.set(cell.x, cell.y, areaFlags);
//
//        switch (result) {
//            case MISS:
//                break;
//            case ALREADY:
//                break;
//            case HIT:
//                areaFlags += Area.SHIP;
//                break;
//            case KILL:
//                break;
//        }
//
//        mView.
//
//        if (result != BattleLogic.StrikeResult.ALREADY) {
//
//            byte areaFlags = Area.FIRED;
//
//            if (result.equals(BattleLogic.StrikeResult.HIT) || result.equals(BattleLogic.StrikeResult.KILL)) {
//                areaFlags += Area.SHIP;
//            }
//
//
//        }
//    }
}
}
