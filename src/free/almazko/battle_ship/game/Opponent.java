package free.almazko.battle_ship.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/2/13
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Opponent {

    Cell move = new Cell(0, 0);
    public ShipsArea shipsArea = new ShipsArea();
    public ShipsArea enemyShipsArea = new ShipsArea();
    Random random = new Random();
    Game.StrikeResult result;
    private ZoneFinder zoneFinder;

    public Opponent() {
        zoneFinder = new ZoneFinder(shipsArea.getArea());
    }

    public Cell makeMove() {

        move.x = random.nextInt(Area.SIZE);
        move.y = random.nextInt(Area.SIZE);

        return move;
    }

    public void setResult(Game.StrikeResult result) {
        result = result;
    }


    public ShipsArea makeShips() {

        boolean isVertical;
        ShipsArea area = new ShipsArea();
        Area freeSpace = area.getArea();
        int maxShipSize = 4;
        isVertical = random.nextBoolean();

        int x, y;
        if (isVertical) {
            x = random.nextInt(Area.SIZE);
            y = random.nextInt(Area.SIZE - maxShipSize);
        } else {
            x = random.nextInt(Area.SIZE - maxShipSize);
            y = random.nextInt(Area.SIZE);
        }

        Ship ship;
        ship = makeShip(x, y, 4, isVertical);

        area.add(ship);


        int freeCells = 0;
        for (int i = 0; i < Area.SIZE; i++) {
            for (int j = 0; j < Area.SIZE; j++) {
                if (area.getArea().isEmpty(i, j)) {
                    freeCells++;
                }
            }
        }


        return area;
    }

    public List<Zone> getZones() {

        ArrayList<Zone> zones = new ArrayList<>();

        Area area = shipsArea.getArea();

        if (area.isEmpty()) {
            zones.add(new Zone(0, 0, 9, 9));
            return zones;
        }

        int minPivotX = -1, minPivotY = -1;

        for (int i = 0; i < Area.SIZE; i++) {
            for (int j = 0; j < Area.SIZE; j++) {
                if (area.isEmpty(i, j)) {
                    minPivotX = i;
                    minPivotY = j;
                    break;
                }
            }
            if (minPivotX >= 0) break;
        }

        if (minPivotX <= 0) return zones;

        int maxPivotX = minPivotX, maxPivotY = minPivotY;

        for (int i = minPivotX + 1; i < Area.SIZE; i++) {
            if (area.isEmpty(i, maxPivotY)) {
                maxPivotX++;
            } else {
                break;
            }

        }

        if (maxPivotX <= 0) return zones;

        Zone zone = null;


        return zones;
    }

    private Ship makeShip(int x, int y, int size, boolean isVertical) {
        Collection<Cell> cells = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (isVertical) {
                y++;
            } else {
                x++;
            }

            cells.add(new Cell(x, y));
        }

        Ship.Direction direction;

        if (isVertical) {
            direction = Ship.Direction.VERTICAL;
        } else {
            direction = Ship.Direction.HORIZONTAL;
        }

        return new Ship(cells, direction);
    }
}
