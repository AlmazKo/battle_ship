package ru.alexlen.jbs;

import ru.alexlen.jbs.event.CellAction;
import ru.alexlen.jbs.event.CellActionListener;
import ru.alexlen.jbs.game.BattleLogic;

/**
 * @author Almazko
 */
public class ControllerBattle implements CellActionListener {

    private final BattleLogic          logic;
    private       BattleViewController view;

    public ControllerBattle(BattleLogic logic, BattleViewController viewController) {
        this.logic = logic;
        view = viewController;
        view.setCellActionListener(this);
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
//        view.
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
