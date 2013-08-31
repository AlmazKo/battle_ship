package free.almazko.battle_ship;

import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;

public class Styles {

    private static Map<String, Paint> styles = new HashMap<>();
    private static Paint defaultStyle;

    static {
        Paint paint;
        int blurSize = 5;

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFF022244);

        styles.put("ships_area", paint);

        paint = new Paint();
        paint.setColor(0xFF53A2F8);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(blurSize, 0, 0, 0xFF53A2F8);

        styles.put("draft_ship", paint);

        paint = new Paint();
        paint.setColor(0xFF00DD73);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(blurSize, 0, 0, 0xFF00FF00);

        styles.put("ship", paint);

        paint = new Paint();
        paint.setColor(0xFFFF0000);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(blurSize, 0, 0, 0xFFFF0000);

        styles.put("wrong_ship", paint);

        paint = new Paint();
        paint.setColor(0xFF001327);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);

        styles.put("grid_line", paint);

        defaultStyle = new Paint();
        paint = new Paint();
        paint.setColor(0xFFFFFFFF);
    }

    public static Paint get(String name) {
        Paint paint = styles.get(name);
        if (paint == null) {
            return defaultStyle;
        } else {
            return paint;
        }
    }

}
