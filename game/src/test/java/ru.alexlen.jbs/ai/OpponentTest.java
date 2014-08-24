package ru.alexlen.jbs.game.ai;

import junit.framework.Assert;
import org.junit.Test;
import ru.alexlen.jbs.game.Cell;
import ru.alexlen.jbs.game.Ship;
import ru.alexlen.jbs.game.ShipsArea;

import static junit.framework.Assert.assertEquals;

public class OpponentTest {
    @Test
    public void makeShips() {
        Opponent opponent;

        final byte attempts = 20;
        byte success = 0;
        ShipsArea area;

        for (byte i = 0; i < attempts; i++) {
            opponent = new Opponent();
            area = opponent.makeShips();

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
        Assert.assertEquals(new Cell(0, 0), ship.getCells().getFirst());
    }




}
