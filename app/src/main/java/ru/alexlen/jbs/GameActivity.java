package ru.alexlen.jbs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import ru.alexlen.jbs.game.BattleLogic;
import ru.alexlen.jbs.game.DisposalLogic;
import ru.alexlen.jbs.game.ShipsArea;
import ru.alexlen.jbs.game.ai.OpponentBattle;
import ru.alexlen.jbs.game.ai.OpponentDisposal;
import ru.alexlen.jbs.ui.Styles;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 9/5/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends Activity {

    private GameView           view;
    private ControllerDisposal controller;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("BATTLE", "GameActivity create");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int x = R.integer.grid_offset;
        Styles.fill(9);

        view = new GameView(this);
        view.getHolder().addCallback(view);
        view.getHolder().setKeepScreenOn(true);
        setContentView(view);

        startDisposal();

    }

    void restart() {
        controller = new ControllerDisposal(this, new DisposalLogic(), new ViewDisposal(view));
        controller.start();
    }


    void startDisposal() {
        controller = new ControllerDisposal(this, new DisposalLogic(), new ViewDisposal(view));
        controller.start();
    }

//
    void startBattle(ShipsArea playerShips)
    {
        OpponentDisposal opponentDisposal = new OpponentDisposal();

        ShipsArea opponentShips = opponentDisposal.makeShips();
        OpponentBattle opponent = new OpponentBattle(opponentShips);

        BattleLogic logic = new BattleLogic(playerShips, opponentShips);


        Log.w("GameActivity", "Opponent map:\n" + opponentShips.getArea().toString());

        ControllerBattle controller = new ControllerBattle(this, logic, playerShips, opponent, new ViewBattle(view));

        controller.start();
    }


//
}