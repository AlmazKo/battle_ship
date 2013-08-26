package free.almazko.battle_ship;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.Deque;
import java.util.LinkedList;

public class DrawView extends View implements OnTouchListener {
    private static final String TAG = "DrawView";

    private enum State {DRAFT, COMPLETE_SHIP, COMPLETE_ALL}

    private enum Direction {VERTICAL, HORIZONTAL}

    private State state = State.DRAFT;
    private Direction direction;

    DrawBattleMap drawBattleMap;
    BattleMap battleMap;
    MotionEvent event;
    Ship currentShip;

    public DrawView(Context context) {
        super(context);

//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnTouchListener(this);
    }

    private void initBattleMap(Canvas canvas) {
        drawBattleMap = new DrawBattleMap(canvas);
        battleMap = new BattleMap(drawBattleMap);
    }


    @Override
    public void onDraw(Canvas canvas) {
        if (battleMap == null) {
            initBattleMap(canvas);
        }

        drawBattleMap.drawGrid(canvas);
        if (event != null) {
            if (state == State.DRAFT) {
                battleMap.addDraftShip(currentShip);
            } else {
                battleMap.addShip(currentShip);

                if (battleMap.isComplete()) {
                    state = State.COMPLETE_ALL;
                    drawBattleMap.minimize(150);
                } else {
                    state = State.DRAFT;
                }


            }
        }
    }

    public boolean onTouch(View view, MotionEvent event) {

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            Cell cell = drawBattleMap.recognizeCell(event.getX(), event.getY());
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
            state = State.COMPLETE_SHIP;
        }

        this.event = event;
        invalidate();

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