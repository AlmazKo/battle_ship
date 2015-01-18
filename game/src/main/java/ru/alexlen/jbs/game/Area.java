package ru.alexlen.jbs.game;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/28/13
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Area implements Cloneable {

    public static final int EMPTY      = 0b0000;
    public static final int SHIP       = 0b0001;
    public static final int SHIPS_AREA = 0b0010;
    public static final int FIRED      = 0b0100;

    public static final int SIZE       = 10;

    final int[][] area;

    public Area(int[][] area) {
        this.area = area;
    }

    public Area() {
        area = new int[Area.SIZE][Area.SIZE];
    }

    public void set(final int x, final int y, final int type) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            area[x][y] = type;
        }
    }

    public int get(final int x, final int y) {

        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return area[x][y];
        }

        return -1;
    }

    public boolean isEmpty(final int x, final int y) {
        return get(x, y) < 1;
    }

    public boolean isEmpty() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (area[i][j] != EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public Area cloneArea() {
        final int[][] newArea = new int[Area.SIZE][Area.SIZE];

        for (int i = 0; i < area.length; i++) {
            int[] row = new int[Area.SIZE];
            System.arraycopy(area[i], 0, row, 0, Area.SIZE);
            newArea[i] = row;
        }

        return new Area(newArea);
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder("  0123456789\n");

        for (int y = 0; y < SIZE; y++) {
            text.append(y);
            text.append(" ");

            for (int x = 0; x < SIZE; x++) {
                switch (area[x][y]) {
                    case EMPTY:
                        text.append(".");
                        break;
                    case SHIP:
                        text.append("█");
                        break;
                    default:
                        text.append("~");
                }
            }
            text.append("\n");
        }

        return text.toString();
    }
}