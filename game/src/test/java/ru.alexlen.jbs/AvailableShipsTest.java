package ru.alexlen.jbs;

import org.junit.Before;
import org.junit.Test;
import ru.alexlen.jbs.game.AvailableShips;

import static org.junit.Assert.*;

/**
 * @author Almazko
 */
public class AvailableShipsTest {
    AvailableShips ships;

    @Before
    public void setUp() throws Exception {
        int amountShips = 5;

        int[] availShips = new int[amountShips + 1];
        for (int i = 1; i <= amountShips; i++) {
            availShips[i] = amountShips + 1 - i;
        }

        ships = new AvailableShips(availShips);
    }

    @Test
    public void newState() throws Exception {
        assertEquals(ships.count(5), 1);
        assertEquals(ships.count(4), 2);
        assertEquals(ships.count(3), 3);
        assertEquals(ships.count(2), 4);
        assertEquals(ships.count(1), 5);
    }


    @Test
    public void endShip() throws Exception {

        assertTrue("Should success remove ships", ships.remove(5));
        assertEquals("Ships this size should equals 0", ships.count(5), 0);
        assertTrue("Ships this size has isEndedShips", ships.isEndedShips(5));
        assertFalse("Must not remove ships when it has isEndedShips", ships.remove(5));
    }

    @Test
    public void particularDecreaseShips() throws Exception {

        assertTrue("Successful ship remove", ships.remove(4));
        assertEquals("Ships quantity of the same size should be equal to 1", ships.count(4), 1);
        assertFalse("Ships this size has not isEndedShips", ships.isEndedShips(4));

        assertTrue("Should success ship remove ", ships.remove(4));

        assertEquals("After double decreasing, amount of available ships should equals 0", ships.count(4), 0);
        assertTrue("After double decreasing, available ships has isEndedShips", ships.isEndedShips(4));
        assertFalse("After double decreasing, must not remove this ships", ships.remove(4));
    }


    @Test
    public void removeAllShips() throws Exception {

        assertFalse(ships.isEndedAllShips());

        assertTrue(ships.remove(5));

        assertTrue(ships.remove(4));
        assertTrue(ships.remove(4));

        assertTrue(ships.remove(3));
        assertTrue(ships.remove(3));
        assertTrue(ships.remove(3));

        assertTrue(ships.remove(2));
        assertTrue(ships.remove(2));
        assertTrue(ships.remove(2));
        assertTrue(ships.remove(2));

        assertTrue(ships.remove(1));
        assertTrue(ships.remove(1));
        assertTrue(ships.remove(1));
        assertTrue(ships.remove(1));
        assertTrue(ships.remove(1));

        assertFalse(ships.remove(5));
        assertFalse(ships.remove(4));
        assertFalse(ships.remove(3));
        assertFalse(ships.remove(2));
        assertFalse(ships.remove(1));

        assertTrue(ships.isEndedAllShips());

    }
}
