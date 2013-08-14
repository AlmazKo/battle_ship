package free.almazko.battle_ship;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View implements OnTouchListener {
    private static final String TAG = "DrawView";

    private enum State {NONE, STARTED}

    private enum Direction {VERTICAL, HORIZONTAL}


    private State state = State.NONE;
    private Direction direction;

    BattleMap battleMap = new BattleMap();
    MotionEvent event;
    List<Cell> cells = new ArrayList<Cell>();

    public DrawView(Context context) {
        super(context);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnTouchListener(this);
    }

    @Override
    public void onDraw(Canvas canvas) {
        battleMap.draw(canvas);
        if (event != null) {
            for (Cell c : cells) {
                battleMap.drawDraftCell(c);
            }
        }
    }

    public boolean onTouch(View view, MotionEvent event) {

        Cell cell;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                cell = battleMap.recognizeCell(event.getX(), event.getY());
                if (cell == null) {
                    return false;
                }
                cells.clear();
                this.event = event;

                cells.add(cell);
                break;

            case MotionEvent.ACTION_MOVE:

                cell = battleMap.recognizeCell(event.getX(), event.getY());
                if (cell == null) {
                    return false;
                }
                int size = cells.size();

                if (cell.equals(cells.get(size - 1))) {
                    return false;
                }


                if (size == 1) {
                    direction = recognizeDirection(cell);
                    if (direction == null) {
                        return false;
                    }

                    this.event = event;
                    cells.add(cell);

                } else if (isValidDirectionNewCell(cell)) {

                    if (direction == Direction.VERTICAL) {
                        if (Math.abs((cells.get(0).y - cells.get(size - 1).y)) < Math.abs((cells.get(0).y - cell.y))) {
                            cells.add(cell);
                        } else {
                            cells.remove(size - 1);
                        }
                    }

                    if (direction == Direction.HORIZONTAL) {
                        if (Math.abs((cells.get(0).x - cells.get(size - 1).x)) < Math.abs((cells.get(0).x - cell.x))) {
                            cells.add(cell);
                        } else {
                            cells.remove(size - 1);
                        }
                    }
                    this.event = event;


                } else {

                    Cell c = recognizeMissCell(cell);
                    if (c.equals(cells.get(size - 1))) {
                        return false;
                    }
                    this.event = event;
                    if (direction == Direction.VERTICAL) {
                        if (Math.abs((cells.get(0).y - cells.get(size - 1).y)) < Math.abs((cells.get(0).y - c.y))) {
                            cells.add(c);
                        } else {
                            cells.remove(size - 1);
                        }
                    }

                    if (direction == Direction.HORIZONTAL) {
                        if (Math.abs((cells.get(0).x - cells.get(size - 1).x)) < Math.abs((cells.get(0).x - c.x))) {
                            cells.add(c);
                        } else {
                            cells.remove(size - 1);
                        }
                    }


                }


                break;
            case MotionEvent.ACTION_UP:
                direction = null;
                return false;

            default:
                return false;
        }


        invalidate();
        return true;
    }

    private boolean hasCell(Cell cell) {
        for (Cell c : cells) {
            if (c.x == cell.x && c.y == cell.y) return true;
        }

        return false;
    }


    private boolean isValidDirectionNewCell(Cell cell) {
        int size = cells.size();

        if (size < 2) return true;

        return (cells.get(size - 1).x == cell.x && direction == Direction.VERTICAL) ||
                (cells.get(size - 1).y == cell.y && direction == Direction.HORIZONTAL);
    }

    private Direction recognizeDirection(Cell cell) {

        if (cells.get(0).x == cell.x) {
            return Direction.VERTICAL;
        } else if (cells.get(0).y == cell.y) {
            return Direction.HORIZONTAL;
        } else {
            return null;
        }
    }


    private Cell recognizeMissCell(Cell cell) {
        Cell result;

        if (direction == Direction.VERTICAL) {
            result = new Cell(cells.get(cells.size() - 1).x, cell.y);
        } else {
            result = new Cell(cell.x, cells.get(cells.size() - 1).y);
        }

        return result;
    }
}