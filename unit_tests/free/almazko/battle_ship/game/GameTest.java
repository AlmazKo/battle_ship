package free.almazko.battle_ship.game;

import free.almazko.battle_ship.game.ai.Opponent;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/14/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameTest{
    @Test
    public void makeShip() {

        Area playerArea = new Area();

//        playerArea.set(;)



        Area opponentArea = new Area();

//        Game battle = new Game(playerArea, opponentArea);

        Opponent opponent = new Opponent();

        Ship ship = opponent.makeShip(0, 0, 1, true);

        assertEquals(1, ship.size());
        assertEquals(new Cell(0,0), ship.getCells().getFirst());
    }

}
