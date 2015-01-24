package ru.alexlen.jbs.game;

import java.util.Comparator;

public class Cell implements Comparator<Cell> {

    public final int x;
    public final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Cell[" + x + ", " + y + ']';
    }

    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

    @Override public int compare(Cell o1, Cell o2) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return 10000 * x + y;
    }
}
