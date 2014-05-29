package free.almazko.battle_ship;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import free.almazko.battle_ship.game.stage.disposal.Controller;
import free.almazko.battle_ship.game.stage.disposal.DisposalView;
import free.almazko.battle_ship.game.ui.Styles;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/5/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("BATTLE", "GameActivity create");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int x = R.integer.grid_offset;
        Styles.fill(9);
        Log.d("BATTLE", "GameActivity init view");
        DisposalView initView = new DisposalView(this);
        Log.d("BATTLE", "GameActivity set view");
        setContentView(initView);
        Log.d("BATTLE", "GameActivity create controller");
        Controller initController = new Controller(initView);

    }
}