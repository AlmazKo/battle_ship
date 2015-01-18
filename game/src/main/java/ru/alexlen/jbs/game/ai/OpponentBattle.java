package ru.alexlen.jbs.game.ai;

import ru.alexlen.jbs.game.BattleLogic;
import ru.alexlen.jbs.game.Cell;
import ru.alexlen.jbs.game.Player;
import ru.alexlen.jbs.game.ShipsArea;

import java.util.Random;

/**
 * @author Almazko
 */
public class OpponentBattle implements Player {
    Random random = new Random();
    private ShipsArea mShips;

    public OpponentBattle(ShipsArea ships) {
        mShips = ships;
    }

    @Override public Cell strike() {
        return new Cell(random.nextInt(10), random.nextInt(10));
    }

    @Override public void result(BattleLogic.StrikeResult result) {

    }
}
