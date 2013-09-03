package free.almazko.battle_ship;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class MyActivity extends Activity
{
    DrawView drawView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set full screen view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawView = new DrawView(this);

        int gridOffset = getResources().getInteger(R.integer.grid_offset);
        drawView.setGridOffset(gridOffset);
        setContentView(drawView);
        drawView.requestFocus();
    }
}
