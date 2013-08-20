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
    Deque<Cell> cells = new LinkedList<Cell>();

    public Ship(Deque<Cell> cells) {
        this.cells = cells;
    }

    public Ship(Collection<Cell> newCells) {
        cells.addAll(newCells);
    }

    public Ship(Cell cell) {
        cells.add(cell);
    }

    public Deque<Cell> getCells() {
        return cells;
    }
}
