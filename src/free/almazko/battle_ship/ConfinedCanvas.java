package free.almazko.battle_ship;

import android.graphics.Paint;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/31/13
 * Time: 11:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ConfinedCanvas {

    public void drawLine(float startX, float startY, float stopX, float stopY,
                         Paint paint);
}
