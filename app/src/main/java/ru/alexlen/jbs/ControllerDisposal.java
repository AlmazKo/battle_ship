package ru.alexlen.jbs;


import android.util.Log;
import ru.alexlen.jbs.event.CellAction;
import ru.alexlen.jbs.event.CellActionListener;
import ru.alexlen.jbs.game.*;

import java.util.Deque;
import java.util.LinkedList;

public class ControllerDisposal implements CellActionListener {

    private final static String TAG = "ControllerDisposal";
    private GameActivity mActivity;

    enum Direction {VERTICAL, HORIZONTAL}

    private Direction direction;
    private Cell      currentCell;
    private ShipsArea shipsArea = new ShipsArea();
    private Ship mDraftShip;


    private ViewDisposal mView;

    final AvailableShips mAvailShips;

    public ControllerDisposal(GameActivity activity, DisposalLogic logic, ViewDisposal view) {
        mActivity = activity;
        mAvailShips = logic.getAvailableShips();
        mView = view;
        view.setCellActionListener(this);
    }


    public void start() {
        mView.drawCommittedShips(shipsArea.getArea());
    }

    @Override
    public void onCellAction(final CellAction action) {

        if (action.isUp()) {
            commitShip();
            return;
        }

        if (!action.isCellRecognized()) return;

        if (action.isDown()) {
            initShip(action.getCell());
        } else if (action.isMove() && mDraftShip != null) {
            modifyShip(action.getCell());
        }
    }

    // TODO move to DisposalLogic
    private boolean possibleAddShip(Ship ship) {
        return !mAvailShips.isEndedShips(ship.size()) && shipsArea.freeSpaceForShip(ship);
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

    private void initShip(Cell targetCell) {
        currentCell = targetCell;
        placeShip(new Ship(targetCell));
    }

    private void modifyShip(Cell targetCell) {
        if (currentCell.equals(targetCell)) {
            return;
        }

        currentCell = targetCell;

        if (direction == null) {
            direction = recognizeDirection(currentCell, mDraftShip.getCells().getFirst());
            if (direction == null) {
                return;
            }
        }

        Ship ship = recognizeShip(targetCell);
        if (ship.size() ==1) {
            direction = null;
        }

        if (mDraftShip.equals(ship)) {
            return;
        }

        if (ship.size() > DisposalLogic.MAX_SHIP_SIZE) {
            return;
        }

        placeShip(ship);
    }

    private void placeShip(final Ship ship) {
        if (!shipsArea.freeSpaceForShip(ship)) {
            return;
        }

        mDraftShip = ship;

        Log.d(TAG, "place: " + mDraftShip);
        mView.drawDraftShip(ship, possibleAddShip(ship));
    }

    private void commitShip() {
        if (mDraftShip == null) return;
        if (!possibleAddShip(mDraftShip)) {
            mView.drawCommittedShips(shipsArea.getArea());
            return;
        }

        direction = null;
        shipsArea.add(mDraftShip);
        Log.d(TAG, "commit: " + mDraftShip);

        mAvailShips.remove(mDraftShip.size());
        mDraftShip = null;
        mView.drawCommittedShips(shipsArea.getArea());

        if (mAvailShips.isEndedAllShips()) {
            mActivity.startBattle(shipsArea.getArea());
        }
    }

    private Ship recognizeShip(final Cell targetCell) {
        if (direction.equals(Direction.HORIZONTAL)) {
            return makeHorizontalShip(targetCell);
        } else {
            return makeVerticalShip(targetCell);
        }
    }

    private Ship makeHorizontalShip(Cell targetCell) {
        Deque<Cell> cells = new LinkedList<>();
        Cell firstCell = mDraftShip.getCells().getFirst();

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

    private Ship makeVerticalShip(Cell targetCell) {
        Deque<Cell> cells = new LinkedList<>();
        final Cell firstCell = mDraftShip.getCells().getFirst();

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

    private Direction recognizeDirection(Cell cell, Cell headCell) {
        if (headCell.x == cell.x) {
            return Direction.VERTICAL;
        } else if (headCell.y == cell.y) {
            return Direction.HORIZONTAL;
        } else {
            return null;
        }
    }
}
