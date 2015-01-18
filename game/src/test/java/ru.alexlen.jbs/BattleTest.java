package ru.alexlen.jbs;

import org.junit.Test;
import ru.alexlen.jbs.game.Area;
import ru.alexlen.jbs.game.Cell;
import ru.alexlen.jbs.game.Ship;
import ru.alexlen.jbs.game.ai.OpponentDisposal;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/14/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class BattleTest {
    @Test
    public void makeShip() {

        Area playerArea = new Area();

//        playerArea.set(;)



        Area opponentArea = new Area();

//        Game battle = new Game(playerArea, opponentArea);

        OpponentDisposal opponent = new OpponentDisposal();

        Ship ship = opponent.makeShip(0, 0, 1, true);

        assertEquals(1, ship.size());
        assertEquals(new Cell(0,0), ship.getCells().getFirst());
    }

}
