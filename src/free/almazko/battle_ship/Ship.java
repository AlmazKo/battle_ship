package free.almazko.battle_ship;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/19/13
 * Time: 10:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Ship {
    private Deque<Cell> cells = new LinkedList<Cell>();
    private Direction direction;

    public static enum Direction {VERTICAL, HORIZONTAL}

    public Ship(Deque<Cell> cells, Direction direction) {
        this.direction = direction;
        this.cells = cells;
    }

    public Ship(Collection<Cell> newCells, Direction direction) {
        this.direction = direction;
        cells.addAll(newCells);
    }

    public Ship(Cell cell) {
        cells.add(cell);
    }

    public Deque<Cell> getCells() {
        return cells;
    }

    public int size() {
        return cells.size();
    }

    public boolean isVertical() {
        return this.direction.equals(Direction.VERTICAL);
    }

    public boolean isHorizontal() {
        return this.direction.equals(Direction.HORIZONTAL);
    }

}
