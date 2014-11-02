package ru.alexlen.jbs;


import ru.alexlen.jbs.event.CellAction;
import ru.alexlen.jbs.event.CellActionListener;
import ru.alexlen.jbs.game.*;

import java.util.Deque;
import java.util.LinkedList;

public class ControllerDisposal implements CellActionListener {


    enum Direction {VERTICAL, HORIZONTAL}

    private Direction direction;
    private boolean   impossiblePlace = false;
    private Cell      lastCell        = new Cell(-1, -1);
    private ShipsArea shipsArea       = new ShipsArea();
    private Ship draftShip;


    private DisposalViewController viewController;

    AvailableShips availShips;

    public ControllerDisposal(DisposalLogic logic, DisposalViewController viewController)
    {
        availShips = logic.getAvailableShips();
        this.viewController = viewController;
        viewController.setCellActionListener(this);
        // viewController.drawCommittedShips(shipsArea.getArea());
    }


    public void start()
    {
        viewController.drawCommittedShips(shipsArea.getArea());
    }

    @Override
    public void onCellAction(final CellAction action)
    {

        if (action.isUp()) {
            commitShip();
            return;
        }

        if (action.isDown()) {
            initShip(action.getCell());
        } else if (action.isMove()) {
            modifyShip(action.getCell());
        }
    }

    // TODO move to DisposalLogic
    private boolean possibleAddShip(final Ship ship)
    {

        return !availShips.ended(ship.size()) && shipsArea.freeSpaceForShip(ship);
    }

//    public void onDraw(android.graphics.Canvas2 canvas) {
//        this.canvas.update(canvas);
//        battleMap.draw(this.canvas);
//
//
//        if (battleMap.isComplete()) {
//
//            parentView.getVibrator().vibrate(800);
//            Area playersArea = battleMap.getArea();
//
//            BattleController battleController = new BattleController(canvas, parentView, playersArea);
//
//            parentView.setController(battleController);
//        }
//    }

    private void initShip(final Cell targetCell)
    {
        lastCell = targetCell;
        placeShip(new Ship(targetCell));
    }

    private void modifyShip(final Cell targetCell)
    {
        if (lastCell.equals(targetCell)) {
            return;
        }

        lastCell = targetCell;

        if (direction == null) {
            direction = recognizeDirection(lastCell, draftShip.getCells().getFirst());
            if (direction == null) {
                return;
            }
        }

        Ship ship = recognizeShip(targetCell);

        if (draftShip.equals(ship)) {
            return;
        }

        if (ship.size() > DisposalLogic.MAX_SHIP_SIZE) {
            return;
        }

        placeShip(ship);
    }

    private void placeShip(final Ship ship)
    {
        if (!shipsArea.freeSpaceForShip(ship)) {
            return;
        }

        draftShip = ship;
        viewController.drawDraftShip(ship, possibleAddShip(ship));
    }

    private void commitShip()
    {

        if (draftShip == null) {
            return;
        }

        if (!possibleAddShip(draftShip)) {
            return;
        }

        direction = null;
        shipsArea.add(draftShip);

        draftShip = null;
//        if (availShips.allEnded()) {
//            // notify
//        }

        viewController.drawCommittedShips(shipsArea.getArea());
    }

    private Ship recognizeShip(final Cell targetCell)
    {
        if (direction.equals(Direction.HORIZONTAL)) {
            return makeHorizontalShip(targetCell);
        } else {
            return makeVerticalShip(targetCell);
        }
    }

    private Ship makeHorizontalShip(final Cell targetCell)
    {
        Deque<Cell> cells = new LinkedList<>();
        Cell firstCell = draftShip.getCells().getFirst();

        if (firstCell.x < targetCell.x) {
            for (int x = firstCell.x; x <= targetCell.x; x++) {
                cells.add(new Cell(x, firstCell.y));
            }
        } else {
            for (int x = firstCell.x; x >= targetCell.x; x--) {
                cells.add(new Cell(x, firstCell.y));
            }
        }

        return new Ship(cells, Ship.Direction.HORIZONTAL);
    }

    private Ship makeVerticalShip(final Cell targetCell)
    {
        Deque<Cell> cells = new LinkedList<>();
        final Cell firstCell = draftShip.getCells().getFirst();

        if (firstCell.y < targetCell.y) {
            for (int y = firstCell.y; y <= targetCell.y; y++) {
                cells.add(new Cell(firstCell.x, y));
            }
        } else {
            for (int y = firstCell.y; y >= targetCell.y; y--) {
                cells.add(new Cell(firstCell.x, y));
            }
        }

        return new Ship(cells, Ship.Direction.VERTICAL);
    }

    private Direction recognizeDirection(final Cell cell, final Cell headCell)
    {
        if (headCell.x == cell.x) {
            return Direction.VERTICAL;
        } else if (headCell.y == cell.y) {
            return Direction.HORIZONTAL;
        } else {
            return null;
        }
    }
}
