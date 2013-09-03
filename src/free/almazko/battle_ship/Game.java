package free.almazko.battle_ship;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/31/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {


    public enum Player {PROTAGONIST, OPPONENT}

    public enum StrikeResult {MISS, ALREADY, HIT, KILL;

        public byte toByte(Cell cell) {

            byte result = Area.FIRED;

            if (this.equals(HIT) || this.equals(KILL)) {
                result &=  Area.SHIP;
            }

            return result;
        }
    }

    public Player currentPlayer;

    private Area playersArea;
    private Area opponentsArea;

    public Game(Area playersArea, Area opponentsArea) {
        this.playersArea = playersArea;
        this.opponentsArea = opponentsArea;
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
