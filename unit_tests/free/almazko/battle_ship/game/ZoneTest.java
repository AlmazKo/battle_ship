package free.almazko.battle_ship.game;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/14/13
 * Time: 8:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class ZoneTest {
    @Test
    public void equals() {

        Zone zone = new Zone(0, 0, 1, 1);

        assertEquals(zone, new Zone(0, 0, 1, 1));
    }

    @Test
    public void contains() {

        Zone zone = new Zone(0, 0, 1, 1);
        Zone bigZone = new Zone(0, 0, 9, 9);

        assertTrue(bigZone.contains(zone));
        assertFalse(zone.contains(bigZone));
        assertTrue(zone.contains(new Zone(0, 0, 1, 1)));
    }

    @Test
    public void maxXSize() {
        Zone zone;
        zone = new Zone(0, 0, 0, 0);
        assertEquals(1, zone.xSize());

        zone = new Zone(0, 0, 2, 2);
        assertEquals(3, zone.xSize());
    }

    @Test
    public void maxYSize() {
        Zone zone;
        zone = new Zone(0, 0, 0, 0);
        assertEquals(1, zone.ySize());

        zone = new Zone(0, 0, 2, 4);
        assertEquals(5, zone.ySize());
    }

    @Test
    public void maxSize() {
        Zone zone;
        zone = new Zone(1, 1, 6, 9);
        assertEquals(9, zone.maxSize());

        zone = new Zone(0, 0, 9, 6);
        assertEquals(10, zone.maxSize());
    }
}
