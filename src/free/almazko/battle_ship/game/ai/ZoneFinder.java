package free.almazko.battle_ship.game.ai;

import free.almazko.battle_ship.game.Area;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/14/13
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ZoneFinder {
    public Area area;
    private List<Zone> zones = new ArrayList<>();

    public ZoneFinder(Area area) {
        this.area = area;
    }

    public List<Zone> getZones() {
        zones = new ArrayList<>();
        if (area.isEmpty()) {
            zones.add(new Zone(0, 0, Area.SIZE - 1, Area.SIZE - 1));
            return zones;
        }

        for (int i = 0; i < Area.SIZE; i++) {
            for (int j = 0; j < Area.SIZE; j++) {
                if (area.isEmpty(i, j)) {
                    recognize(i, j);
                }
            }
        }

        return zones;
    }



    private void recognize(int minPivotX, int minPivotY) {

        addZone(horizontalSpread(minPivotX, minPivotY));
        addZone(verticalSpread(minPivotX, minPivotY));
    }


    private void addZone(Zone newZone) {

        if (newZone == null) return;

        for (Zone zone : zones) {
            if (zone.contains(newZone)) {
                return;
            }
        }

        zones.add(newZone);
    }


    private Zone horizontalSpread(int minPivotX, int minPivotY) {
        int maxPivotX = minPivotX, maxPivotY = minPivotY;

        for (int x = minPivotX + 1; x < Area.SIZE; x++) {
            if (area.isEmpty(x, minPivotY)) {
                maxPivotX++;
            } else {
                break;
            }
        }

        for (int y = minPivotY + 1; y < Area.SIZE; y++) {
            for (int x = minPivotX; x <= maxPivotX; x++) {
                if (!area.isEmpty(x, y)) {
                    return new Zone(minPivotX, minPivotY, maxPivotX, maxPivotY);
                }
            }
            maxPivotY++;
        }

        return new Zone(minPivotX, minPivotY, maxPivotX, maxPivotY);
    }


    private Zone verticalSpread(int minPivotX, int minPivotY) {
        int maxPivotX = minPivotX, maxPivotY = minPivotY;

        for (int y = minPivotY + 1; y < Area.SIZE; y++) {
            if (area.isEmpty(minPivotX, y)) {
                maxPivotY++;
            } else {
                break;
            }
        }

        for (int x = minPivotX + 1; x < Area.SIZE; x++) {
            for (int y = minPivotY; y <= maxPivotY; y++) {
                if (!area.isEmpty(x, y)) {
                    return new Zone(minPivotX, minPivotY, maxPivotX, maxPivotY);
                }
            }
            maxPivotX++;
        }

        return new Zone(minPivotX, minPivotY, maxPivotX, maxPivotY);
    }
}
