package ru.alexlen.jbs.game;

import org.jetbrains.annotations.NotNull;

public class BattleLogic {

    public enum Player {PROTAGONIST, OPPONENT}
    public enum StrikeResult {MISS, ALREADY, HIT, KILL}

    public Player currentPlayer;

    private final Area playersArea;
    private final Area opponentsArea;

    public BattleLogic(@NotNull Area playersArea, @NotNull Area opponentsArea) {
        this.playersArea = playersArea.cloneArea();
        this.opponentsArea = opponentsArea.cloneArea();

        choiceFirstPlayer();
    }

    public void choiceFirstPlayer() {
        currentPlayer = Player.PROTAGONIST;
    }

    @NotNull
    public StrikeResult strike(Cell cell) {

        final Area targetArea = isProtagonist() ? opponentsArea : playersArea;
        final int target = targetArea.get(cell.x, cell.y);

        if ((target & Area.FIRED) > 0) {
            return StrikeResult.ALREADY;
        }

        targetArea.set(cell.x, cell.y, target + Area.FIRED);

        if ((target & Area.SHIP) > 0) {
            return StrikeResult.HIT;
        } else {
            nextPlayer();
            return StrikeResult.MISS;
        }
    }


    public Area getProtagonistArea() {

        return playersArea;
    }


    public boolean isProtagonist() {
        return currentPlayer == Player.PROTAGONIST;
    }

    private void nextPlayer() {
        if (currentPlayer == Player.PROTAGONIST) {
            currentPlayer = Player.OPPONENT;
        } else {
            currentPlayer = Player.PROTAGONIST;
        }

    }

}