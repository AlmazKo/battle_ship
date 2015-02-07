package ru.alexlen.jbs.ui;

import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public class Styles {

    private static Map<String, Paint> styles = new HashMap<>();
    private static Paint defaultStyle;
    public static  int   gridCellOffset;

    static {
        defaultStyle = new Paint();
        defaultStyle.setColor(0xFFFFFFFF);
        defaultStyle.setStrokeWidth(1);
        defaultStyle.setStyle(Paint.Style.STROKE);
    }


    public static void fill(int gridCellOffset) {

        if (!styles.isEmpty()) {
            styles = new HashMap<>();
        }

        Styles.gridCellOffset = gridCellOffset;

        Paint paint;
        int blurSize = gridCellOffset;

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFF000000);
        styles.put("none", paint);


        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFF022244);
        styles.put("ships_area", paint);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFFFFFF66);
        styles.put("panel", paint);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x00000000);
        styles.put("tpanel", paint);


        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFFFFFF66);
        paint.setShadowLayer(blurSize, 0, 0, 0xFFFFFF66);
        styles.put("fared_area", paint);

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
        paint.setShadowLayer(blurSize, 0, 0, 0xFF00DD73);
        styles.put("ship", paint);

        paint = new Paint();
        paint.setColor(0xFFFF0000);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(blurSize, 0, 0, 0xFFFF0000);
        styles.put("wrong_ship", paint);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFFFFFF66);
        styles.put("mini_fared_area", paint);

        paint = new Paint();
        paint.setColor(0xFF53A2F8);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        styles.put("mini_draft_ship", paint);

        paint = new Paint();
        paint.setColor(0xFF00DD73);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        styles.put("mini_ship", paint);

        paint = new Paint();
        paint.setColor(0xFFFF0000);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        styles.put("mini_wrong_ship", paint);

        paint = new Paint();
        paint.setColor(0xFF022244);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        styles.put("mini_ships_area", paint);

        paint = new Paint();
        paint.setColor(0xFF003366);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(gridCellOffset);
        styles.put("grid_line", paint);

        paint = new Paint();
        paint.setColor(0xFF003366);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);
        styles.put("mini_grid_line", paint);

        paint = new Paint();
        paint.setColor(0xFF008cf0);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        styles.put("cell_blank", paint);

        Typeface tf;
        tf = Typeface.create("Helvetica", Typeface.ITALIC);
        paint = new Paint();
        paint.setColor(0xFF42aaff);
        paint.setAntiAlias(true);
        paint.setTextSize(gridCellOffset * 4);
        paint.setTypeface(tf);
        styles.put("text_title", paint);

        tf = Typeface.create("Helvetica", Typeface.BOLD);
        paint = new Paint();
        paint.setColor(0xFF00DD73);
        paint.setAntiAlias(true);
        paint.setTextSize(gridCellOffset * 8);
        paint.setShadowLayer(blurSize * 2, 0, 0, 0xFF00DD73);
        paint.setTypeface(tf);
        styles.put("text_win_title", paint);


        tf = Typeface.create("Helvetica", Typeface.NORMAL);
        paint = new Paint();
        paint.setColor(0xFF008cf0);
        paint.setAntiAlias(true);
        paint.setTextSize(gridCellOffset * 2);
        paint.setTypeface(tf);
        styles.put("text_main", paint);


        // disposal
        paint = new Paint();
        paint.setColor(0xFFFF0000);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(2, 0, 0, 0xFFFF0000);
        styles.put("ships_counter", paint);

        paint = new Paint();
        paint.setColor(0xFF00DD73);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(2, 0, 0, 0xFF00FF00);
        styles.put("avail_ships", paint);

        paint = new Paint();
        paint.setColor(0xFF666666);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(2, 0, 0, 0xFFAAAAAA);
        styles.put("not_avail_ships", paint);
    }

    public static Paint get(String name) {
        Paint paint = styles.get(name);
        if (paint == null) {
            return defaultStyle;
        } else {
            return paint;
        }
    }


    public static Paint mix(Paint... paints) {

        Paint mixed = new Paint();
        for (Paint paint : paints) {
            mixed.setColor(paint.getColor());
            mixed.setAntiAlias(paint.isAntiAlias());
            mixed.setStrokeWidth(paint.getStrokeWidth());
            mixed.setStyle(paint.getStyle());
            mixed.setTextSize(paint.getTextSize());
            mixed.setTypeface(paint.getTypeface());
        }

        return mixed;

    }

}
