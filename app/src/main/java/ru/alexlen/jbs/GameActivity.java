package ru.alexlen.jbs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import ru.alexlen.jbs.game.Area;
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
public class GameActivity extends Activity implements SurfaceHolder.Callback {

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
        view.getHolder().addCallback(this);
        setContentView(view);

        startDisposal();

    }

    void startDisposal() {
        controller = new ControllerDisposal(this, new DisposalLogic(), new ViewDisposal(view));
    }

//
    void startBattle(Area playerArea)
    {
        OpponentDisposal opponentDisposal = new OpponentDisposal();

        ShipsArea opponentShips = opponentDisposal.makeShips();
        OpponentBattle opponent = new OpponentBattle(opponentShips);

        BattleLogic logic = new BattleLogic(playerArea, opponentShips.getArea());

        ControllerBattle controller = new ControllerBattle(this, logic, playerArea, opponent, new ViewBattle(view));

        controller.start();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        controller.start();
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override public void surfaceDestroyed(SurfaceHolder holder) {

    }
}