package ru.alexlen.jbs.stage.battle;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/31/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {


    public enum Player {PROTAGONIST, OPPONENT}

    public enum StrikeResult {MISS, ALREADY, HIT, KILL}

    public Player currentPlayer;

    private Area playersArea;
    private Area opponentsArea;

    public Game(final Area playersArea, final Area opponentsArea) {
        this.playersArea = playersArea.cloneArea();
        this.opponentsArea = opponentsArea.cloneArea();
        choiceFirstPlayer();
    }

    public void choiceFirstPlayer() {
        currentPlayer = Player.PROTAGONIST;
    }

    public StrikeResult strike(Cell cell) {
        Area targetArea;

        if (isProtagonist()) {
            targetArea = opponentsArea;
        } else {
            targetArea = playersArea;
        }

        int target = targetArea.get(cell.x, cell.y);

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
