package free.almazko.battle_ship;

import java.util.ArrayList;
import java.util.List;

public class BattleMap {
    List<Ship> ships = new ArrayList<Ship>();
    DrawBattleMap draw;

    public BattleMap(DrawBattleMap draw) {
        this.draw = draw;
    }

    public boolean addShip(Ship ship) {
        ships.add(ship);
        drawShips();
        return true;
    }

    public boolean possibleAddShip(Ship ship) {
        return true;
    }

    public void addDraftShip(Ship ship) {
        drawShips();
        for (Cell c : ship.getCells()) {
            draw.drawDraftCell(c);
        }
    }

    private void drawShips() {
        for (Ship ship : ships) {
            for (Cell cell : ship.getCells()) {
                draw.drawCell(cell);
            }
        }
    }
}
