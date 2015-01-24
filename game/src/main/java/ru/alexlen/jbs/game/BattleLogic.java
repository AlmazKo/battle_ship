package ru.alexlen.jbs.game;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

import static ru.alexlen.jbs.game.BattleLogic.StrikeResult.*;

public class BattleLogic {

    public enum Player {PROTAGONIST, OPPONENT}

    public enum StrikeResult {MISS, ALREADY, HIT, KILL, WIN}

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

        ShipsArea targetShips;
        Set<Cell> targetTurns;
        if (isProtagonist()) {
            targetShips = opponentsShips;
            targetTurns = playersTurns;
        } else {
            targetShips = playerShips;
            targetTurns = opponentTurns;
        }

        if (targetTurns.contains(cell)) return ALREADY;
        targetTurns.add(cell);

        StrikeResult result = MISS;


        int totalLife = 0;
        int life;
        for (Ship ship : targetShips.ships) {
            if (ship.strike(cell)) {
                life = ship.getLife();
                if (life > 0)
                    return HIT;
                if (life == 0) result = KILL;
            }

            totalLife += ship.getLife();
        }

        if (totalLife == 0) return WIN;
        if (result == MISS) nextPlayer();

        return result;

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