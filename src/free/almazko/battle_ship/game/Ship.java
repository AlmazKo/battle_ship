package free.almazko.battle_ship.game;

import java.util.*;

public class Ship {
    private Deque<Cell> cells = new LinkedList<>();
    private Direction direction;

    public static enum Direction {VERTICAL, HORIZONTAL}

    private boolean impossible = true;

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

    public boolean isImpossible() {
        return impossible;
    }

    public boolean hasDestroyed() {
        return false;
    }

    public void setImpossible(boolean impossible) {
        this.impossible = impossible;
    }

    public boolean equals(Ship ship) {
        if (ship.size() != size()) {
            return false;
        }

        Cell[] current = cells.toArray(new Cell[size()]);
        Cell[] other = ship.getCells().toArray(new Cell[ship.size()]);

        for (int i = 0; i < size(); i++) {
            if (!current[i].equals(other[i])) {
                return false;
            }
        }

        return true;
//        return cells.equals(ship.getCells());
    }
}
