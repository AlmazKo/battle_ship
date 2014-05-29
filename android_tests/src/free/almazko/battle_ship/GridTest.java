package free.almazko.battle_ship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.test.AndroidTestCase;

import static org.mockito.Mockito.mock;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class free.almazko.battle_ship.GridTest \
 * free.almazko.battle_ship.tests/android.test.InstrumentationTestRunner
 */
public class GridTest extends AndroidTestCase {
    protected Canvas mockedCanvas;
    protected Paint mockedPaint;

    protected void setUp() {
        mockedCanvas = mock(Canvas.class);
        mockedPaint = mock(Paint.class);
    }

    public GridTest() {
        assertEquals(1, 1);
    }

}
