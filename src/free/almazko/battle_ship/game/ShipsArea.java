package free.almazko.battle_ship.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ShipsArea implements Iterable<Ship> {
    Area area;
    public Collection<Ship> ships = new ArrayList<>();

    public ShipsArea() {
        this.area = new Area();
    }

    public ShipsArea(Area area) {
        this.area = area;
    }

    public Area getArea() {
        return area;
    }

    @Override
    public Iterator<Ship> iterator() {
        return ships.iterator();
    }

    public boolean add(Ship ship) {

        if (!freeSpaceForShip(ship)) {
            return false;
        }

        ships.add(ship);

        for (Cell cell : ship.getCells()) {
            area.set(cell.x + 1, cell.y - 1, Area.SHIPS_AREA);
            area.set(cell.x + 1, cell.y, Area.SHIPS_AREA);
            area.set(cell.x + 1, cell.y + 1, Area.SHIPS_AREA);
            area.set(cell.x, cell.y - 1, Area.SHIPS_AREA);
            area.set(cell.x, cell.y + 1, Area.SHIPS_AREA);
            area.set(cell.x - 1, cell.y - 1, Area.SHIPS_AREA);
            area.set(cell.x - 1, cell.y, Area.SHIPS_AREA);
            area.set(cell.x - 1, cell.y + 1, Area.SHIPS_AREA);
        }

        for (Cell cell : ship.getCells()) {
            area.set(cell.x, cell.y, Area.SHIP);
        }

        return true;
    }
    public boolean freeSpaceForShip(Ship ship) {

        for (Cell cell : ship.getCells()) {
            if (!area.isEmpty(cell.x, cell.y)) {
                return false;
            }
        }

        return true;

    }
}
