package ru.alexlen.jbs.game;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Value object
 */
public class Ship implements Cloneable {
    private final Deque<Cell> cells;
    private final Direction   direction;

    public static enum Direction {VERTICAL, HORIZONTAL}

    private boolean impossible = true;

    public Ship(@NotNull Deque<Cell> cells, @NotNull Direction direction) {
        this.direction = direction;
        this.cells = cells;
    }

    public Ship(@NotNull Collection<Cell> cells, @NotNull Direction direction) {
        this.direction = direction;
        this.cells = new LinkedList<>(cells);
    }

    public Ship(final Cell cell) {
        cells = new LinkedList<>();
        cells.add(cell);
        direction = Direction.VERTICAL;
    }

    public Deque<Cell> getCells() {
        return cells;
    }

    public int size() {
        return cells.size();
    }

    public boolean isVertical() {
        return direction.equals(Direction.VERTICAL);
    }

    public boolean isHorizontal() {
        return direction.equals(Direction.HORIZONTAL);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        return cells.equals(ship.cells);
    }

    @Override public String toString() {
        StringBuilder builder = new StringBuilder("Ship:");

        for (Cell cell : cells) {
            builder.append(cell.toString());
            builder.append(',');
        }

        return builder.toString();
    }

    //
//    public boolean equals(Ship ship) {
//        if (ship.size() != size()) return false;
//
//        Cell[] current = cells.toArray(new Cell[size()]);
//        Cell[] other = ship.getCells().toArray(new Cell[ship.size()]);
//
//        for (int i = 0; i < size(); i++) {
//            if (!current[i].equals(other[i])) {
//                return false;
//            }
//        }
//
//        return true;
////        return cells.equals(ship.getCells());
//    }


    public Ship copy() {

        Deque<Cell> newCells = new LinkedList<>();

        for (Cell cell : cells) {
            newCells.add(cell);
        }

        return new Ship(newCells, direction);
    }
}
