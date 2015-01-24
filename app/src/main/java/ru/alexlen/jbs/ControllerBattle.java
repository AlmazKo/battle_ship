package ru.alexlen.jbs;

import android.util.Log;
import ru.alexlen.jbs.event.CellAction;
import ru.alexlen.jbs.event.CellActionListener;
import ru.alexlen.jbs.game.*;

import java.util.*;

import static ru.alexlen.jbs.game.Area.SHIP;

/**
 * @author Almazko
 */
public class ControllerBattle implements CellActionListener {

    private       GameActivity mActivity;
    private final BattleLogic  logic;
//    private       Area         mPayerArea;
    private       Player       mOpponent;
    private       ViewBattle   mView;

    //    private Area      knownArea = new Area();
    private ShipsArea knownShips = new ShipsArea();
    private ShipsArea playerShips = new ShipsArea();

    public ControllerBattle(GameActivity activity, BattleLogic logic, ShipsArea playerArea, Player opponent,
                            ViewBattle viewController) {
        mActivity = activity;
        this.logic = logic;
        playerShips = playerArea;
        mOpponent = opponent;
        mView = viewController;
        mView.setCellActionListener(this);
    }


    public void start() {
        mView.drawCommittedShips(knownShips.getArea(), playerShips.getArea());
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
        turn(knownShips, cell, result);
        mView.drawCommittedShips(knownShips.getArea(), playerShips.getArea());


        if (result != BattleLogic.StrikeResult.MISS) return;

        waitOpponent();
    }

    private void waitOpponent() {
        Cell cell = mOpponent.strike();
        BattleLogic.StrikeResult result = logic.strike(cell);

        Log.i("ControllerBattle", "BattleLogic opponent: " + result + " in " + cell);
        turn(playerShips, cell, result);
        mView.drawCommittedShips(knownShips.getArea(), playerShips.getArea());

        if (result != BattleLogic.StrikeResult.MISS) waitOpponent();
    }

    private void turn(ShipsArea ships, Cell cell, BattleLogic.StrikeResult result) {


        int areaFlags = ships.getArea().get(cell.x, cell.y);

        switch (result) {
            case MISS:
                areaFlags = areaFlags | Area.FIRED;
                ships.getArea().set(cell.x, cell.y, areaFlags);
                break;

            case ALREADY:
                return;

            case HIT:
                areaFlags = areaFlags | SHIP | Area.FIRED;
                ships.getArea().set(cell.x, cell.y, areaFlags);
                break;
            case KILL:
                areaFlags = areaFlags | SHIP | Area.FIRED;
                ships.getArea().set(cell.x, cell.y, areaFlags);

                ships.add(getShip(ships.getArea(), cell));

                break;
        }

    }

    private Ship getShip(Area area, Cell cell) {

        if (area.has(cell.x + 1, cell.y, SHIP) || area.has(cell.x - 1, cell.y, SHIP)) {
            return new Ship(horizontalSearch(area, cell), Ship.Direction.HORIZONTAL);

        } else if (area.has(cell.x, cell.y + 1, SHIP) || area.has(cell.x, cell.y - 1, SHIP)) {
            return new Ship(verticalSearch(area, cell), Ship.Direction.VERTICAL);

        } else {
            return new Ship(cell);
        }
    }

    public static Collection<Cell> horizontalSearch(Area area, Cell cell) {

        Collection<Cell> cells = new LinkedList<>();

        int left = cell.x;

        for (int i = left - 1; i >= 0 && area.has(i, cell.y, SHIP); i--) {
            left = i;
        }

        while (area.has(left, cell.y, SHIP)) {
            cells.add(new Cell(left, cell.y));
            left++;
        }

        return cells;
    }

    public static Collection<Cell> verticalSearch(Area area, Cell cell) {
        Collection<Cell> cells = new LinkedList<>();

        int top = cell.y;

        for (int i = top - 1; i >= 0 && area.has(cell.x, i, SHIP); i--) {
            top = i;
        }

        while (area.has(cell.x, top, SHIP)) {
            cells.add(new Cell(cell.x, top));
            top++;
        }

        return cells;
    }
}
