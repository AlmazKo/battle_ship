package free.almazko.battle_ship;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/27/13
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class GridCanvas {

    public static final Paint PAINT_SHIP = new Paint();
    public static final Paint PAINT_DRAFT_SHIP = new Paint();
    public static final Paint PAINT_WRONG_SHIP = new Paint();
    public static final Paint PAINT_SHIPS_AREA = new Paint();
    public Grid grid;

    protected Canvas canvas;
    protected Paint linePaint = new Paint();

    public GridCanvas(Grid grid, Canvas canvas) {
        this.grid = grid;
        this.canvas = canvas;
        initVars();
    }



    protected void initVars() {

        int blurSize = grid.properties.cellSpacing;
        PAINT_SHIPS_AREA.setStyle(Paint.Style.FILL);

        PAINT_SHIPS_AREA.setColor(0xFF022244);

        PAINT_DRAFT_SHIP.setColor(0xFF53A2F8);
        PAINT_DRAFT_SHIP.setAntiAlias(true);
        PAINT_DRAFT_SHIP.setStyle(Paint.Style.FILL);
        PAINT_DRAFT_SHIP.setShadowLayer(blurSize, 0, 0, 0xFF53A2F8);

        PAINT_SHIP.setColor(0xFF00DD73);
        PAINT_SHIP.setAntiAlias(true);
        PAINT_SHIP.setStyle(Paint.Style.FILL);
        PAINT_SHIP.setShadowLayer(blurSize, 0, 0, 0xFF00FF00);

        PAINT_WRONG_SHIP.setColor(0xFFFF0000);
        PAINT_WRONG_SHIP.setAntiAlias(true);
        PAINT_WRONG_SHIP.setStyle(Paint.Style.FILL);
        PAINT_WRONG_SHIP.setShadowLayer(blurSize, 0, 0, 0xFFFF0000);


        linePaint.setColor(0xFF001327);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(grid.properties.cellSpacing);
    }
}
