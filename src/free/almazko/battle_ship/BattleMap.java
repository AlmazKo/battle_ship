package free.almazko.battle_ship;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/31/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class BattleMap {

    public enum Player {PROTAGONIST, OPPONENT}

    public enum StrikeResult {MISS, ALREADY, HIT, KILL}

    public Player currentPlayer;

    private Area playersArea;
    private Area opponentsArea;
    private BattleCanvas canvas;

    public BattleMap(BattleCanvas canvas, Area playersArea, Area opponentsArea) {
        this.playersArea = playersArea;
        this.opponentsArea = opponentsArea;
        this.canvas = canvas;

        choiceFirstPlayer();
    }


    public void choiceFirstPlayer() {
        currentPlayer = Player.PROTAGONIST;
    }

    public void draw() {
        drawShips(playersArea, canvas.playersGrid);
        drawShips(opponentsArea, canvas.opponentsGrid);
    }

    private void drawShips(Area area, Grid grid) {
        for (int x = 0; x < Area.SIZE; x++) {
            for (int y = 0; y < Area.SIZE; y++) {
                int target = area.get(x, y);


                if ((target & Area.SHIP) > 0) {
                    if ((target & Area.FIRED) > 0) {
                        grid.drawCell(x, y, Styles.get("wrong_ship"));
                    } else {
                        grid.drawCell(x, y, Styles.get("ship"));
                    }
                } else {
                    if ((target & Area.FIRED) > 0) {
                        grid.drawCell(x, y, Styles.get("fared_area"));
                    } else {
                        grid.drawCell(x, y, Styles.get("ships_area"));
                    }
                }
            }
        }
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


    private void nextPlayer() {
        if (currentPlayer == Player.PROTAGONIST) {
            currentPlayer = Player.OPPONENT;
        } else {
            currentPlayer = Player.PROTAGONIST;
        }

    }


    public boolean isProtagonist() {
        return currentPlayer == Player.PROTAGONIST;
    }
}
