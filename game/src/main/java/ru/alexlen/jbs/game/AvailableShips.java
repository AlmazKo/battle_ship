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


    public int count(int i) {
        return availShips[i];
    }

    public boolean remove(final int i) {
        if (availShips[i] > 0) {
            availShips[i]--;
            return true;
        } else {
            return false;
        }
    }

    public boolean ended(final int i) {
        return availShips[i] < 1;
    }

    public boolean allEnded() {
        for (int i = 1; i <= maxShips; i++) {
            if (availShips[i] > 0) return false;
        }

        return true;
    }
}
