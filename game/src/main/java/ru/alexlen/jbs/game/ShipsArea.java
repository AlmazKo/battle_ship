package ru.alexlen.jbs.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static ru.alexlen.jbs.game.Area.SHIP;
import static ru.alexlen.jbs.game.Area.SHIPS_AREA;

public class ShipsArea implements Iterable<Ship> {
    final Area area;
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

//        if (!freeSpaceForShip(ship)) {
//            return false;
//        }

        ships.add(ship);

        for (Cell cell : ship.getCells()) {
            area.setFlag(cell.x + 1, cell.y - 1,SHIPS_AREA);
            area.setFlag(cell.x + 1, cell.y,SHIPS_AREA);
            area.setFlag(cell.x + 1, cell.y + 1,SHIPS_AREA);
            area.setFlag(cell.x, cell.y - 1,SHIPS_AREA);
            area.setFlag(cell.x, cell.y + 1,SHIPS_AREA);
            area.setFlag(cell.x - 1, cell.y - 1,SHIPS_AREA);
            area.setFlag(cell.x - 1, cell.y, SHIPS_AREA);
            area.setFlag(cell.x - 1, cell.y + 1, SHIPS_AREA);
        }

        for (Cell cell : ship.getCells()) {
            area.setFlag(cell.x, cell.y, SHIP);
            area.removeFlag(cell.x, cell.y, SHIPS_AREA);
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

    public ShipsArea copy() {
        ShipsArea result = new ShipsArea();
        for (Ship ship : ships) {
            result.add(ship.copy());
        }

        return result;
    }
}
