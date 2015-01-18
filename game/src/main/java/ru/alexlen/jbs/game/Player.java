package ru.alexlen.jbs.game;

/**
 * @author Almazko
 */
public interface Player {

    public Cell strike();
    public void result(BattleLogic.StrikeResult result);

}
