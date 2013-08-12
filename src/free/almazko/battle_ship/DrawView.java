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
                this.event = event;
                cell = battleMap.recognizeCell(event.getX(), event.getY());
                cells.clear();
                cells.add(cell);
                break;

            case MotionEvent.ACTION_MOVE:

                cell = battleMap.recognizeCell(event.getX(), event.getY());

                int size = cells.size();

                if (cell.equals(cells.get(size - 1))) {
                    return false;
                }

                if (size == 1) {
                    direction = recognizeDirection(cell);
                    if (direction != null) {
                        this.event = event;
                        cells.add(cell);
                    } else {
                        return false;
                    }
                } else if (isValidDirectionNewCell(cell)) {
                    this.event = event;
                    cells.add(cell);
                } else {

                    Cell c = recognizeMissCell(cell);
                    if (c != null) {
                        this.event = event;
                        cells.add(c);
                    } else {
                        return false;
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

        if (size < 3) return true;

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

        if (hasCell(cell)) return null;

        return result;
    }
}