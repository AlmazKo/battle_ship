package ru.alexlen.jbs.game;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ShipTest {


    @Test
    public void strikeDoubleDeckShip() {

        Ship ship = new Ship(new Cell(1,1), new Cell(2,1));

        assertEquals(2, ship.getLife());
        assertTrue(!ship.strike((new Cell(0,0))));

        assertTrue(ship.strike((new Cell(1,1))));
        assertEquals(1, ship.getLife());
        assertTrue(ship.strike((new Cell(2,1))));

        assertEquals(0, ship.getLife());

    }

    @Test
    public void strikeSingleDeckShip() {

        Ship ship = new Ship(new Cell(5,5));

        assertEquals(1, ship.getLife());

        assertTrue(ship.strike((new Cell(5,5))));
        assertEquals(0, ship.getLife());
    }

}