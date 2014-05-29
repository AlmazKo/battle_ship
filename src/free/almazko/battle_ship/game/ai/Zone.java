package free.almazko.battle_ship.game.ai;

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


    public int xSize() {
         return maxX - minX + 1;
    }

    public int ySize() {
         return maxY - minY + 1;
    }

    public int maxSize() {
        return Math.max(xSize(), ySize());
    }

    public int minSize() {
        return Math.min(xSize(), ySize());
    }
}
