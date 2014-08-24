package ru.alexlen.jbs.game;

public class Cell {

    public int x;
    public int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    public boolean equals(Cell cell) {
        return this.x == cell.x && this.y == cell.y;
    }

    public boolean equals(Object other) {
        return this == other || other instanceof Cell && equals((Cell) other);
    }
}
