package ru.alexlen.jbs.game;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

import static ru.alexlen.jbs.game.BattleLogic.StrikeResult.*;

public class BattleLogic {

    public enum Player {PROTAGONIST, OPPONENT}

    public enum StrikeResult {MISS, ALREADY, HIT, KILL}

    Set<Cell> playersTurns  = new LinkedHashSet<>();
    Set<Cell> opponentTurns = new LinkedHashSet<>();


    public Player currentPlayer;

    private final ShipsArea playerShips;
    private final ShipsArea opponentsShips;

    public BattleLogic(@NotNull ShipsArea playersArea, @NotNull ShipsArea opponentsArea) {
        this.playerShips = playersArea.copy();
        this.opponentsShips = opponentsArea.copy();

        choiceFirstPlayer();
    }

    public void choiceFirstPlayer() {
        currentPlayer = Player.PROTAGONIST;
    }

    @NotNull
    public StrikeResult strike(Cell cell) {

        if (isProtagonist() && playersTurns.contains(cell)) return ALREADY;
        if (!isProtagonist() && opponentTurns.contains(cell)) return ALREADY;

        final ShipsArea targetShips = isProtagonist() ? opponentsShips : playerShips;


        for (Ship ship : targetShips.ships) {
            if (ship.strike(cell)) {
                return ship.getLife() > 0 ? HIT : KILL;
            }
        }

        nextPlayer();
        return MISS;

//
//        int target = targetArea.get(cell.x, cell.y);
//
//
//        targetArea.set(cell.x, cell.y, target + Area.FIRED);
//
//        if ((target & Area.SHIP) > 0) {
//            return HIT;
//        } else {
//            nextPlayer();
//            return StrikeResult.MISS;
//        }
    }

//
//    public Area getProtagonistArea() {
//
//        return playerShips;
//    }


    public boolean isProtagonist() {
        return currentPlayer == Player.PROTAGONIST;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void nextPlayer() {
        if (currentPlayer == Player.PROTAGONIST) {
            currentPlayer = Player.OPPONENT;
        } else {
            currentPlayer = Player.PROTAGONIST;
        }

    }

}