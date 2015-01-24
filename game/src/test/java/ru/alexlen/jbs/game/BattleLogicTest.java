package ru.alexlen.jbs.game;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static ru.alexlen.jbs.game.BattleLogic.StrikeResult.*;

public class BattleLogicTest {

    @Test
    public void testHitStrike() {

        ShipsArea ships = new ShipsArea();
        Ship ship = new Ship(new Cell(5, 0), new Cell(5, 1));
        ships.add(ship);

        BattleLogic logic = new BattleLogic(new ShipsArea(), ships);

        //The player plays first
        assertEquals(logic.strike(new Cell(5, 0)), HIT);
    }

    @Test
    public void testAlreadyStrike() {

        ShipsArea ships = new ShipsArea();
        Ship ship = new Ship(new Cell(5, 0), new Cell(5, 1));
        ships.add(ship);

        BattleLogic logic = new BattleLogic(new ShipsArea(), ships);

        //The player plays first
        assertEquals(logic.strike(new Cell(5, 0)), HIT);
        assertEquals(logic.strike(new Cell(5, 0)), ALREADY);
        assertEquals(logic.strike(new Cell(5, 0)), ALREADY);
    }

    @Test
    public void testKillStrike() {

        ShipsArea ships = new ShipsArea();
        Ship ship = new Ship(new Cell(5, 0), new Cell(5, 1));
        Ship ship2 = new Ship(new Cell(1, 0), new Cell(1, 1));
        ships.add(ship);
        ships.add(ship2);

        BattleLogic logic = new BattleLogic(new ShipsArea(), ships);

        //The player plays first
        assertEquals(logic.strike(new Cell(5, 0)), HIT);
        assertEquals(logic.strike(new Cell(5, 1)), KILL);
    }


    @Test
    public void killSingleDeckShip() {

        ShipsArea ships = new ShipsArea();
        Ship ship = new Ship(new Cell(0, 0));
        Ship ship2 = new Ship(new Cell(5, 5));
        ships.add(ship);
        ships.add(ship2);

        BattleLogic logic = new BattleLogic(new ShipsArea(), ships);

        //The player plays first
        assertEquals(logic.strike(new Cell(0, 0)), KILL);
    }


    @Test
    public void winSingle() {

        ShipsArea ships = new ShipsArea();
        Ship ship = new Ship(new Cell(0, 0));
        ships.add(ship);

        BattleLogic logic = new BattleLogic(new ShipsArea(), ships);

        //The player plays first
        assertEquals(logic.strike(new Cell(0, 0)), WIN);
    }


    @Test
    public void miss() {

        ShipsArea ships = new ShipsArea();
        Ship ship = new Ship(new Cell(0, 0));
        ships.add(ship);

        BattleLogic logic = new BattleLogic(new ShipsArea(), ships);

        assertEquals(logic.strike(new Cell(9, 9)), MISS);
    }

    @Test
    public void changePlayerAfterMiss() {

        ShipsArea ships = new ShipsArea();
        Ship ship = new Ship(new Cell(0, 0));
        ships.add(ship);

        BattleLogic logic = new BattleLogic(new ShipsArea(), ships);

        assertEquals(logic.getCurrentPlayer(), BattleLogic.Player.PROTAGONIST);
        assertEquals(logic.strike(new Cell(9, 9)), MISS);
        assertEquals(logic.getCurrentPlayer(), BattleLogic.Player.OPPONENT);
    }
}