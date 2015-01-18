package ru.alexlen.jbs.game.ai;

import ru.alexlen.jbs.game.*;
//import ru.alexlen.jbs.stage.battle.Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/2/13
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpponentDisposal {

    Cell lastTarget = new Cell(0, 0);
    public  ShipsArea shipsArea      = new ShipsArea();

    //TODO get from Logic
    private int[]     availShips     = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    public  ShipsArea enemyShipsArea = new ShipsArea();
    Random random = new Random();
    //   Game.StrikeResult result;
    private ZoneFinder zoneFinder;

    public OpponentDisposal() {
        zoneFinder = new ZoneFinder(shipsArea.getArea());
    }

    public Cell makeMove() {
        lastTarget = new Cell(rand(Area.SIZE), rand(Area.SIZE));
        return lastTarget;
    }

//    public void setResult(Game.StrikeResult result) {
//        result = result;
//    }

    public ShipsArea makeShips() {

        Ship ship = null;
        for (int shipSize : availShips) {
            for (Zone zone : zoneFinder.getZones()) {

                if (zone.maxSize() >= shipSize) {
                    ship = positionShip(zone, shipSize);
                    break;
                }
            }
            shipsArea.add(ship);
        }

        return shipsArea;
    }

    private Ship positionShip(Zone zone, int shipSize) {

        boolean isVertical;
        if (zone.minSize() >= shipSize) {
            isVertical = random.nextBoolean();
        } else {
            isVertical = zone.ySize() >= shipSize;
        }

        int x, y;
        if (isVertical) {
            y = zone.minY + rand(zone.ySize() - shipSize);
            x = zone.minX + rand(zone.xSize() - 1);
        } else {
            y = zone.minY + rand(zone.ySize() - 1);
            x = zone.minX + rand(zone.xSize() - shipSize);
        }

        Ship ship;
        ship = makeShip(x, y, shipSize, isVertical);

        return ship;
    }

    private int rand(int value) {
        if (value < 1) {
            return 0;
        } else {
            return random.nextInt(value);
        }
    }

    public Ship makeShip(int x, int y, int size, boolean isVertical) {
        Collection<Cell> cells = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            cells.add(new Cell(x, y));

            if (isVertical) {
                y++;
            } else {
                x++;
            }
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
