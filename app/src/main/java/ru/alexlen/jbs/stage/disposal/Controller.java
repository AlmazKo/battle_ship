//package ru.alexlen.jbs.stage.disposal;
//
//
//import android.util.Log;
//import ru.alexlen.jbs.game.*;
//import ru.alexlen.jbs.event.GameEvent;
//import ru.alexlen.jbs.stage.ControllerInterface;
//
//import java.util.Deque;
//import java.util.LinkedList;
//
//public class Controller implements ControllerInterface {
//
//    private enum Direction {VERTICAL, HORIZONTAL}
//
//    private Direction direction;
//    private Cell lastCell = new Cell(-1, -1);
//    private ShipsArea shipsArea = new ShipsArea();
//    private Ship draftShip;
//    private DisposalView gameView;
//
//    AvailableShips availShips;
//
//    public Controller(DisposalView gameView) {
//        availShips = DisposalLogic.getAvailableShips();
//        this.gameView = gameView;
//        Log.d("BATTLE", "GameController create");
//        gameView.setController(this);
//        Log.d("BATTLE", "GameActivity request focus");
//        gameView.requestFocus();
////        gameView.invalidate();
////        reDraw();
//
//    }
//
//
//
//    public void onGameTouch(GameEvent event) {
//
//        if (event.isUp()) {
//            commitShip();
//            return;
//        }
//
//        if (!event.isCellRecognized()) {
//            return;
//        }
//
//        if (event.isDown()) {
//            initShip(event.cell);
//        } else if (event.isMove()) {
//            modifyShip(event.cell);
//        }
//    }
//
//    private boolean possibleAddShip(final Ship ship) {
//
//        return !availShips.ended(ship.size()) && shipsArea.freeSpaceForShip(ship);
//    }
//
////    public void onDraw(android.graphics.Canvas2 canvas) {
////        this.canvas.update(canvas);
////        battleMap.draw(this.canvas);
////
////
////        if (battleMap.isComplete()) {
////
////            parentView.getVibrator().vibrate(800);
////            Area playersArea = battleMap.getArea();
////
////            BattleController battleController = new BattleController(canvas, parentView, playersArea);
////
////            parentView.setController(battleController);
////        }
////    }
//
//    private void initShip(Cell cell) {
//        lastCell = cell;
//        placeShip(new Ship(cell));
//    }
//
//    private void modifyShip(Cell newCell) {
//
//        if (lastCell.equals(newCell)) {
//            return;
//        }
//
//        lastCell = newCell;
//
//        if (direction == null) {
//            direction = recognizeDirection(lastCell);
//            if (direction == null) {
//                return;
//            }
//        }
//
//        Ship ship = recognizeShip(newCell);
//
//        if (ship.size() > DisposalLogic.MAX_SHIP_SIZE) {
//            return;
//        }
//
//        if (draftShip.equals(ship)) {
//            return;
//        }
//
//        if (possibleAddShip(ship)) {
//            ship.setImpossible(false);
//        } else {
//            ship.setImpossible(true);
//        }
//
//        placeShip(ship);
//    }
//
//    private void placeShip(Ship ship) {
//        if (!shipsArea.freeSpaceForShip(ship)) {
//            return;
//        }
//
//        draftShip = ship;
//        gameView.invalidate();
//    }
//
//    private void commitShip() {
//
//        if (draftShip == null) {
//            return;
//        }
//        if (!possibleAddShip(draftShip)) {
//            return;
//        }
//
//        direction = null;
//        shipsArea.add(draftShip);
//
//        draftShip = null;
//        if (availShips.allEnded()) {
//            // notify
//        }
//
//        gameView.invalidate();
//    }
//
//
//    public void reDraw() {
//        Log.d("BATTLE", "GameController reDraw");
//
//        gameView.drawCommittedShips(shipsArea);
//        gameView.drawDraftShip(draftShip);
//    }
//
//
//    private Ship recognizeShip(Cell lastCell) {
//        if (direction.equals(Direction.HORIZONTAL)) {
//            return horizontalShip(lastCell);
//        } else {
//            return verticalShip(lastCell);
//        }
//    }
//
//    private Ship horizontalShip(Cell cell) {
//        Deque<Cell> cells = new LinkedList<Cell>();
//        Cell firstCell = draftShip.getCells().getFirst();
//
//        if (firstCell.y != cell.y) {
//            cell.y = firstCell.y;
//        }
//
//        if (firstCell.x < cell.x) {
//            for (int x = firstCell.x; x <= cell.x; x++) {
//                cells.add(new Cell(x, cell.y));
//            }
//        } else {
//            for (int x = firstCell.x; x >= cell.x; x--) {
//                cells.add(new Cell(x, cell.y));
//            }
//        }
//
//        return new Ship(cells, Ship.Direction.HORIZONTAL);
//    }
//
//    private Ship verticalShip(Cell cell) {
//        Deque<Cell> cells = new LinkedList<Cell>();
//        Cell firstCell = draftShip.getCells().getFirst();
//
//        if (firstCell.x != cell.x) {
//            cell.x = firstCell.x;
//        }
//
//        if (firstCell.y < cell.y) {
//            for (int y = firstCell.y; y <= cell.y; y++) {
//                cells.add(new Cell(cell.x, y));
//            }
//        } else {
//            for (int y = firstCell.y; y >= cell.y; y--) {
//                cells.add(new Cell(cell.x, y));
//            }
//        }
//
//        return new Ship(cells, Ship.Direction.VERTICAL);
//    }
//
//    private Direction recognizeDirection(Cell cell) {
//
//        if (draftShip == null || draftShip.getCells().size() < 1 || draftShip.getCells().getFirst() == null) {
//            int x =1;
//            x++;
//        }
//        if (draftShip.getCells().getFirst().x == cell.x) {
//            return Direction.VERTICAL;
//        } else if (draftShip.getCells().getFirst().y == cell.y) {
//            return Direction.HORIZONTAL;
//        } else {
//            return null;
//        }
//    }
//}
