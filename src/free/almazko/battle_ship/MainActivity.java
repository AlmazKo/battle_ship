package free.almazko.battle_ship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    GameView gameView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        registerListeners();
    }


    private void registerListeners() {
        Button button = (Button) findViewById(R.id.btn_singleplayer);

        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                showSingleGame();
            }
        });
    }

    private  void showSingleGame() {
        Intent intent = new Intent();
        intent.setClass(this, GameActivity.class);
        this.startActivity(intent);
    }
}
