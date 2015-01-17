package ru.alexlen.jbs.game;

/**
 * @author Almazko
 * Container of available ships
 */
public class AvailableShips {

    private final int[] availShips;
    private final int maxShips;

    public AvailableShips(int[] availShips) {
        this.availShips = availShips;
        maxShips = availShips.length - 1;
    }


    public int count(int shipSize) {
        return availShips[shipSize];
    }

    //todo rename
    public boolean remove(int shipSize) {
        if (availShips[shipSize] > 0) {
            availShips[shipSize]--;
            return true;
        } else {
            return false;
        }
    }

    public boolean isEndedShips(final int shipSize) {
        return availShips[shipSize] < 1;
    }

    public boolean isEndedAllShips() {
        for (int i = 1; i <= maxShips; i++) {
            if (availShips[i] > 0) return false;
        }

        return true;
    }
}
