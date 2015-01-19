package ru.alexlen.jbs.game.ai;

import ru.alexlen.jbs.game.Area;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ZoneFinderTest {
    @Test
    public void emptyArea() {

        Area area = new Area();
        ZoneFinder finder = new ZoneFinder(area);
        List<Zone> zones;
        zones = finder.getZones();
        assertEquals("Empty area contains only one zone", zones.size(), 1);
        assertTrue(zones.contains(new Zone(0, 0, 9, 9)));
    }

    @Test
    public void oneShotInCornerArea() {

        Area area = new Area();
        ZoneFinder finder = new ZoneFinder(area);
        List<Zone> zones;

        area.set(0, 0, Area.SHIP);

        zones = finder.getZones();
        assertEquals("Area contains two zone", 2, zones.size());

        assertTrue(zones.contains(new Zone(1, 0, 9, 9)));
        assertTrue(zones.contains(new Zone(0, 1, 9, 9)));
    }
    @Test
    public void oneShotInFarCornerArea() {

        Area area = new Area();
        ZoneFinder finder = new ZoneFinder(area);
        List<Zone> zones;

        area.set(9, 9, Area.SHIP);

        zones = finder.getZones();
        assertEquals("Area contains two zone", 2, zones.size());

        assertTrue(zones.contains(new Zone(0, 0, 8, 9)));
        assertTrue(zones.contains(new Zone(0, 0, 9, 8)));
    }

    @Test
    public void twoShotsInEdgeArea() {
        Area area = new Area();
        ZoneFinder finder = new ZoneFinder(area);
        List<Zone> zones;

        area.set(5, 0, Area.SHIP);
        area.set(5, 1, Area.SHIP);

        zones = finder.getZones();
        assertEquals("Area contains three zone", 3, zones.size());

        assertTrue(zones.contains(new Zone(0, 0, 4, 9)));
        assertTrue(zones.contains(new Zone(6, 0, 9, 9)));
        assertTrue(zones.contains(new Zone(0, 2, 9, 9)));
    }

    @Test
    public void oneShotInCenterArea() {
        Area area = new Area();
        ZoneFinder finder = new ZoneFinder(area);
        List<Zone> zones;

        area.set(5, 5, Area.SHIP);

        zones = finder.getZones();
        assertEquals("Area contains four zone", 4, zones.size());

        assertTrue(zones.contains(new Zone(0, 0, 4, 9)));
        assertTrue(zones.contains(new Zone(0, 0, 9, 4)));

        assertTrue(zones.contains(new Zone(0, 6, 9, 9)));
        assertTrue(zones.contains(new Zone(6, 0, 9, 9)));
    }


    @Test
    public void hardArea() {
        Area area = new Area();
        ZoneFinder finder = new ZoneFinder(area);
        List<Zone> zones;

        /**
         * example area  with engaged cells
         *  0123456789
         *0 ..........
         *1 ..........
         *2 ..........
         *3 ..........
         *4 ..........
         *5 .x........
         *6 .x.xxxx...
         *7 .x........
         *8 ..........
         *9 .........x
         */
        /**
         *
         *  area  with pivot points
         *  0123456789
         *0 -.-....-..
         *1 ..........
         *2 ..........
         *3 ..........
         *4 ..........
         *5 .x........
         *6 .x.xxxx...
         *7 .x-.......
         *8 -.........
         *9 .........x
         */

        area.set(1, 4, Area.SHIP);
        area.set(1, 5, Area.SHIP);
        area.set(1, 6, Area.SHIP);

        area.set(3, 5, Area.SHIP);
        area.set(4, 5, Area.SHIP);
        area.set(5, 5, Area.SHIP);
        area.set(6, 5, Area.SHIP);

        area.set(9, 9, Area.SHIP);

        zones = finder.getZones();
        assertEquals("Area contains four zone", 10, zones.size());

        assertTrue(zones.contains(new Zone(0, 0, 0, 9)));
        assertTrue(zones.contains(new Zone(0, 0, 9, 3)));

        assertTrue(zones.contains(new Zone(2, 0, 2, 9)));
        assertTrue(zones.contains(new Zone(2, 0, 9, 4)));

        assertTrue(zones.contains(new Zone(7, 0, 8, 9)));
        assertTrue(zones.contains(new Zone(7, 0, 9, 8)));

        assertTrue(zones.contains(new Zone(0, 7, 9, 8)));
        assertTrue(zones.contains(new Zone(0, 7, 8, 9)));

        assertTrue(zones.contains(new Zone(2, 6, 9, 8)));
        assertTrue(zones.contains(new Zone(2, 6, 8, 9)));
    }
}
