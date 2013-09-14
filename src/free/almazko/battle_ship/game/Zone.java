package free.almazko.battle_ship.game;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/13/13
 * Time: 10:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Zone {
    public int maxX;
    public int maxY;
    public int minX;
    public int minY;

    public Zone(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Zone)) return false;

        Zone z = (Zone) other;
        return z.maxX == maxX && z.minX == minX && z.maxY == maxY && z.minY == minY;
    }

    public boolean contains(Zone other) {
        return other.maxX <= maxX && other.minX >= minX && other.maxY <= maxY && other.minY >= minY;
    }

    @Override
    public String toString() {
        return "Zone[" + minX + ";" + minY + " " + maxX + ";" + maxY + ']';
    }


    public int maxXSize() {
         return maxX - minX + 1;
    }

    public int maxYSize() {
         return maxY - minY + 1;
    }

    public int maxSize() {
        return Math.max(maxXSize(), maxYSize());
    }


}
