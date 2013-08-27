package free.almazko.battle_ship;


import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.Deque;
import java.util.LinkedList;

public class InitController implements View.OnTouchListener {

    private enum Direction {VERTICAL, HORIZONTAL}

    private Direction direction;
    private Ship currentShip;
    private InitCanvas canvas;
    private InitBattleMap battleMap;
    private DrawView parentView;
    private Grid grid;

    public InitController(Canvas canvas, DrawView parentView) {
        grid = new Grid(canvas, InitBattleMap.SIZE);
        this.canvas = new InitCanvas(grid, canvas);
        this.parentView = parentView;
        battleMap = new InitBattleMap(this.canvas);
    }

    public void onDraw(Canvas canvas) {
        this.canvas.update(canvas);
        this.canvas.drawGrid();
    }


    public boolean onTouch(View view, MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            Cell cell = grid.recognizeCell(event.getX(), event.getY());
            if (cell == null) {
                return false;
            }

            Ship ship;
            if (action == MotionEvent.ACTION_DOWN) {
                ship = new Ship(cell);
            } else {
                Cell last = currentShip.getCells().getLast();
                if (cell.equals(last)) {
                    return false;
                }

                if (direction == null) {
                    direction = recognizeDirection(cell);
                    if (direction == null) {
                        return false;
                    }
                }
                ship = recognizeShip(cell);
            }

            if (!battleMap.freeSpaceForShip(ship)) {
                return false;
            }

            currentShip = ship;

        } else if (action == MotionEvent.ACTION_UP) {
            direction = null;
            battleMap.commitDraftShip();
        }

        parentView.reDraw(event);

        return true;
    }

    public Ship recognizeShip(Cell lastCell) {
        if (direction.equals(Direction.HORIZONTAL)) {
            return horizontalShip(lastCell);
        } else {
            return verticalShip(lastCell);
        }
    }

    private Ship horizontalShip(Cell cell) {
        Deque<Cell> cells = new LinkedList<Cell>();
        Cell firstCell = currentShip.getCells().getFirst();

        if (firstCell.y != cell.y) {
            cell.y = firstCell.y;
        }

        if (firstCell.x < cell.x) {
            for (int x = firstCell.x; x <= cell.x; x++) {
                cells.add(new Cell(x, cell.y));
            }
        } else {
            for (int x = firstCell.x; x >= cell.x; x--) {
                cells.add(new Cell(x, cell.y));
            }
        }

        return new Ship(cells, Ship.Direction.HORIZONTAL);
    }

    private Ship verticalShip(Cell cell) {
        Deque<Cell> cells = new LinkedList<Cell>();
        Cell firstCell = currentShip.getCells().getFirst();

        if (firstCell.x != cell.x) {
            cell.x = firstCell.x;
        }

        if (firstCell.y < cell.y) {
            for (int y = firstCell.y; y <= cell.y; y++) {
                cells.add(new Cell(cell.x, y));
            }
        } else {
            for (int y = firstCell.y; y >= cell.y; y--) {
                cells.add(new Cell(cell.x, y));
            }
        }

        return new Ship(cells, Ship.Direction.VERTICAL);
    }

    private Direction recognizeDirection(Cell cell) {

        if (currentShip.getCells().getFirst().x == cell.x) {
            return Direction.VERTICAL;
        } else if (currentShip.getCells().getFirst().y == cell.y) {
            return Direction.HORIZONTAL;
        } else {
            return null;
        }
    }
}