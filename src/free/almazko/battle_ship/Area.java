package free.almazko.battle_ship;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/28/13
 * Time: 9:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Area {
    public static final byte SIZE = InitBattleMap.SIZE;
    int[][] area = new int[Area.SIZE][Area.SIZE];

    public void set(int x, int y, int type) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            area[x][y] = type;
        }
    }

    public int get(int x, int y) {

        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            return area[x][y];
        }

        return -1;
    }

    public boolean isEmpty(int x, int y) {
        return get(x, y) < 1;
    }
}