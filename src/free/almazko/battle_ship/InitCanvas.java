package free.almazko.battle_ship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created with IntelliJ IDEA.
 * User: almaz
 * Date: 8/27/13
 * Time: 9:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitCanvas extends GridCanvas {

    protected int reviewPlaceX, reviewPlaceY;
    protected int reviewCellSpacing;
    protected int fontSize, reviewCellSize;

    protected Paint textPaint = new Paint();
    protected Paint previewPaint = new Paint();

    public InitCanvas(Grid grid, Canvas canvas) {
        super(grid, canvas);
        initVars();
    }

    public void drawText(int size, int number) {

        if (isVertical()) {
            reviewPlaceX = 10;
            reviewPlaceY = grid.size() + fontSize;
        } else {
            reviewPlaceX = grid.size() + fontSize;
            reviewPlaceY = 10;
        }

        int posX = reviewPlaceX, posY = reviewPlaceY;

        posY += (reviewCellSize + (reviewCellSpacing * 2)) * size;


        canvas.drawText(size + " x", posX, posY, textPaint);

        posY -= reviewCellSize - reviewCellSpacing;
        posX += reviewCellSize * 2;

        Rect rect;
        for (int i = 0; i < number; i++) {
            rect = new Rect(posX, posY, posX + reviewCellSize, posY + reviewCellSize);
            canvas.drawRect(rect, previewPaint);

            posX += reviewCellSize + reviewCellSpacing;
        }
    }

    public void drawGrid() {
        grid.draw(linePaint);
    }

    public void update(Canvas canvas) {
        this.canvas = canvas;
        grid.changeCanvas(canvas);
    }

    protected void initVars() {
        super.initVars();
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setAntiAlias(true);

        reviewCellSpacing = 2;
        fontSize = 12;
        reviewCellSize = 12;

        textPaint.setTextSize(fontSize);

        previewPaint.setColor(0xFF00DD73);
        previewPaint.setAntiAlias(true);
        previewPaint.setStyle(Paint.Style.FILL);
        previewPaint.setShadowLayer(reviewCellSpacing, 0, 0, 0xFF00FF00);

    }


    private boolean isVertical() {
        return canvas.getWidth() < canvas.getHeight();
    }

}
