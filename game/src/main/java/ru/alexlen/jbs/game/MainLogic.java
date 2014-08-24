package ru.alexlen.jbs.game;

public class MainLogic {
    public enum Stage {DISPOSAL, BATTLE, COMPLETE}
    public final static int MAX_SHIP_SIZE = 4;
    public final static int[] AVAIL_SHIPS = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    public final static int[] AVAIL_SHIPS_AGR = {0, 4, 3, 2, 1};
    public final static int TOTAL_SHIPS = AVAIL_SHIPS.length;
    public final static int VARIANT_SHIPS = 4;

    public static AvailableShips getAvailableShips() {
        int[] availShips = new int[VARIANT_SHIPS + 1];
        for (int i = 1; i <= VARIANT_SHIPS; i++) {
            availShips[i] = VARIANT_SHIPS + 1 - i;
        }

        return new AvailableShips(availShips);
    }
}
