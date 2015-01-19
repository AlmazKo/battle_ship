package ru.alexlen.jbs.game;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Value object
 */
public class Ship {
    private final Deque<Cell> cells;

    private final Set<Cell> wrongCells;
    private       byte      life;
    private final Direction direction;

    public boolean strike(Cell cell) {
        if (wrongCells.contains(cell)) return false;

        for (Cell shipCell : cells) {
            if (shipCell.equals(cell)) {
                wrongCells.add(cell);
                life--;
                return true;
            }
        }

        return false;
    }

    public byte getLife() {
        return life;
    }

    public static enum Direction {VERTICAL, HORIZONTAL}

    private boolean impossible = true;

    public Ship(@NotNull Deque<Cell> cells, @NotNull Direction direction) {
        this.direction = direction;
        this.cells = cells;

        life = (byte) cells.size();
        wrongCells = new HashSet<>(life);
    }

    public Ship(@NotNull Collection<Cell> cells, @NotNull Direction direction) {
        this.direction = direction;
        this.cells = new LinkedList<>(cells);

        life = (byte) cells.size();
        wrongCells = new HashSet<>(life);
    }

    public Ship(Cell... aCells) {

        if (aCells.length == 0) {
            throw new IllegalArgumentException("No cells for creating ship!");
        }

        cells = new LinkedList<>();
        Collections.addAll(cells, aCells);

        if (cells.getFirst().x == cells.getLast().x) {
            direction = Direction.VERTICAL;
        } else {
            direction = Direction.HORIZONTAL;
        }

        life = (byte) cells.size();
        wrongCells = new HashSet<>(life);
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
