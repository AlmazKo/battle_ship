package free.almazko.battle_ship;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/5/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends Activity {
    GameView gameView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);

        int gridOffset = getResources().getInteger(R.integer.grid_offset);
        gameView.setGridOffset(gridOffset);
        setContentView(gameView);
        gameView.requestFocus();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}