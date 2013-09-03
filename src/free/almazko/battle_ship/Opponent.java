package free.almazko.battle_ship;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/2/13
 * Time: 10:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Opponent {

    Cell move = new Cell(0, 0);
    Area area = new Area();
    Random random = new Random();
    Game.StrikeResult result;
    public Cell makeMove() {

        move.x = random.nextInt(Area.SIZE);
        move.y = random.nextInt(Area.SIZE);

        return move;
    }

    public void setResult(Game.StrikeResult result) {
         result = result;
    }
}
