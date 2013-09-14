package free.almazko.battle_ship.game;

import org.junit.Test;

import static junit.framework.Assert.*;

public class OpponentTest {
    @Test
    public void makeShips() {
        Opponent opponent;

        final byte attempts = 50;
        byte success = 0;
        for (byte i = 0; i < attempts; i++) {
            opponent = new Opponent();
            ShipsArea area = opponent.makeShips();

            if (area.ships.size() == 10) {
                success++;
            }

        }

        assertEquals(attempts, success);
    }

    @Test
    public void makeShip() {
        Opponent opponent = new Opponent();

        Ship ship = opponent.makeShip(0, 0, 1, true);

        assertEquals(1, ship.size());
        assertEquals(new Cell(0,0), ship.getCells().getFirst());
    }




}
