package free.almazko.battle_ship;

import java.util.ArrayList;
import java.util.List;


public class InitBattleMap {


    public class Area {
        public static final byte SIZE = InitBattleMap.SIZE;
        int[][] area = new int[Area.SIZE][Area.SIZE];

        public void set(int x, int y, int type) {
            if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                area[x][y] = type;
            }
        }

        public int get(int x, int y) {

            if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
                return area[x][y];
            }

            return -1;
        }

        public boolean isEmpty(int x, int y) {
            return get(x, y) < 1;
        }
    }


    List<Ship> ships = new ArrayList<>();
    Ship draftShip;

    InitCanvas canvas;

    public static final int SIZE = 10;
    private Area area = new Area();
    private static final int EMPTY = 0b0;
    private static final int SHIP = 0b01;
    private static final int SHIPS_AREA = 0b10;
    private static final int MAX_SHIPS = 4;
    private static final int MAX_SHIP_SIZE = MAX_SHIPS;


    int[] avail_ships = new int[MAX_SHIP_SIZE + 1];

    public InitBattleMap(InitCanvas canvas) {
        this.canvas = canvas;

        for (int i = 1; i <= MAX_SHIPS; i++) {
            avail_ships[i] = MAX_SHIPS + 1 - i;
        }
    }

    public boolean addShip(final Ship ship) {
        if (!possibleAddShip(ship)) {
            return false;
        }

        avail_ships[ship.size()]--;
        ships.add(ship);

        for (Cell cell : ship.getCells()) {
            area.set(cell.x + 1, cell.y - 1, SHIPS_AREA);
            area.set(cell.x + 1, cell.y, SHIPS_AREA);
            area.set(cell.x + 1, cell.y + 1, SHIPS_AREA);
            area.set(cell.x, cell.y - 1, SHIPS_AREA);
            area.set(cell.x, cell.y + 1, SHIPS_AREA);
            area.set(cell.x - 1, cell.y - 1, SHIPS_AREA);
            area.set(cell.x - 1, cell.y, SHIPS_AREA);
            area.set(cell.x - 1, cell.y + 1, SHIPS_AREA);
        }

        for (Cell cell : ship.getCells()) {
            area.set(cell.x, cell.y, SHIP);
        }

        return true;
    }

    public boolean possibleAddShip(final Ship ship) {

        return avail_ships[ship.size()] != 0 && freeSpaceForShip(ship);
    }

    public boolean freeSpaceForShip(Ship ship) {
        if (ship.size() > MAX_SHIP_SIZE) {
            return false;
        }
        for (Cell cell : ship.getCells()) {
            if (!area.isEmpty(cell.x, cell.y)) {
                return false;
            }
        }

        return true;

    }

    public Area getArea() {
        return area;
    }

    public void commitDraftShip() {
        addShip(draftShip);
    }


    public void addDraftShip(Ship ship) {

        if (!freeSpaceForShip(ship)) {
            return;
        }

        if (possibleAddShip(ship)) {
            ship.setImpossible(false);
        } else {
            ship.setImpossible(true);
        }

        draftShip = ship;
    }

    public boolean isComplete() {
        for (int i = 1; i <= MAX_SHIPS; i++) {
            if (avail_ships[i] > 0) return false;
        }

        return true;
    }


    private void draw(InitCanvas canvas) {

        Grid grid = canvas.grid;

        for (int i = 1; i <= MAX_SHIPS; i++) {
            canvas.drawText(i, avail_ships[i]);
        }

        for (int x = 0; x < Area.SIZE; x++) {
            for (int y = 0; y < Area.SIZE; y++) {
                switch (area.get(x, y)) {
                    case SHIP:
                        grid.drawCell(x, y, DrawBattleMap.PAINT_SHIP);
                        break;
                    case SHIPS_AREA:
                        grid.drawCell(x, y, DrawBattleMap.PAINT_SHIPS_AREA);
                        break;
                }
            }
        }
    }
}
