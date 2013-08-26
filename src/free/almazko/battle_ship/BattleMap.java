package free.almazko.battle_ship;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;


public class BattleMap {

    public class Area {
        int[][] area = new int[Area.SIZE][Area.SIZE];
        public static final byte SIZE = 10;

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


    List<Ship> ships = new ArrayList<Ship>();
    DrawBattleMap draw;

    private Area area = new Area();
    private static final int EMPTY = 0b0;
    private static final int SHIP = 0b01;
    private static final int SHIPS_AREA = 0b10;
    private static final int MAX_SHIP_SIZE = 5;

    int[] avail_ships = new int[MAX_SHIP_SIZE + 1];

    public BattleMap(DrawBattleMap draw) {
        this.draw = draw;

        avail_ships[1] = 5;
        avail_ships[2] = 4;
        avail_ships[3] = 3;
        avail_ships[4] = 2;
        avail_ships[5] = 1;
    }

    public boolean addShip(Ship ship) {
        if (!possibleAddShip(ship)) {
            drawShips();
            return false;
        }

        avail_ships[ship.size()]--;
        ships.add(ship);
        drawShips();

//
//

        return true;
    }

    public boolean possibleAddShip(Ship ship) {

        if (avail_ships[ship.size()] == 0) {
            return false;
        }

        return freeSpaceForShip(ship);
    }

    public boolean freeSpaceForShip(Ship ship) {
        if (ship.size() > 5) {
            return false;
        }
        for (Cell cell : ship.getCells()) {
            if (!area.isEmpty(cell.x, cell.y)) {
                return false;
            }
        }

        return true;

    }

    public void addDraftShip(Ship ship) {
        drawShips();

        if (!freeSpaceForShip(ship)) {
            return;
        }

        Paint paint;
        if (possibleAddShip(ship)) {
            paint = DrawBattleMap.PAINT_DRAFT_SHIP;
        } else {
            paint = DrawBattleMap.PAINT_WRONG_SHIP;
        }

        for (Cell c : ship.getCells()) {
            draw.drawCell(c, paint);
        }
    }

    private void drawShips() {
        draw.text(5, avail_ships[5]);
        draw.text(4, avail_ships[4]);
        draw.text(3, avail_ships[3]);
        draw.text(2, avail_ships[2]);
        draw.text(1, avail_ships[1]);

        for (Ship ship : ships) {
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
        }

        for (int x = 0; x < Area.SIZE; x++) {
            for (int y = 0; y < Area.SIZE; y++) {
                switch (area.get(x, y)) {
                    case SHIP:
                        draw.drawCell(x, y, DrawBattleMap.PAINT_SHIP);
                        break;
                    case SHIPS_AREA:
                        draw.drawCell(x, y, DrawBattleMap.PAINT_SHIPS_AREA);
                        break;
                }
            }
        }
    }
}
