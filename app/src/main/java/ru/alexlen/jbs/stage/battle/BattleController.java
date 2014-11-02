//package ru.alexlen.jbs.stage.battle;
//
//import android.graphics.Canvas;
//import android.view.MotionEvent;
//import android.view.View;
//import ru.alexlen.jbs.GameView;
//import ru.alexlen.jbs.stage.AbstractController;
//import ru.alexlen.jbs.game.ai.Opponent;
//
///**
// * Created with IntelliJ IDEA.
// * User: almaz
// * Date: 8/31/13
// * Time: 1:33 PM
// * To change this template use File | Settings | File Templates.
// */
//public class BattleController extends AbstractController {
//
//    private GameView parentView;
//    private BattleCanvas gameCanvas;
//    private Game game;
//
//    private Area knownArea = new Area();
//    Opponent opponent = new Opponent();
//
//    public BattleController(Canvas canvas, GameView parentView, Area playersArea) {
//
//        this.parentView = parentView;
//        this.gameCanvas = new BattleCanvas(canvas);
//        game = new Game(playersArea, opponent.makeShips().getArea());
//    }
//
//    public void onDraw(Canvas canvas) {
//
//        this.gameCanvas.update(canvas, game.getProtagonistArea(), knownArea);
//
//        if (!game.isProtagonist()) {
//            Game.StrikeResult result = game.strike(opponent.makeMove());
//
//            if (result == Game.StrikeResult.HIT) {
//                parentView.getVibrator().vibrate(200);
//            }
//
//            if (result == Game.StrikeResult.KILL) {
//                parentView.getVibrator().vibrate(400);
//            }
//
//            parentView.reDraw();
//        }
//
//    }
//
//    public boolean onTouch(View v, MotionEvent event) {
//
//        if (!game.isProtagonist()) {
//            return false;
//        }
//
//        int action = event.getAction();
//
//        if (action == MotionEvent.ACTION_DOWN) {
//            Cell cell = gameCanvas.recognizeOpponentCell(event.getX(), event.getY());
//            if (cell == null) {
//                return false;
//            }
//
//            Game.StrikeResult result = game.strike(cell);
//
//            if (result != Game.StrikeResult.ALREADY) {
//
//                byte areaFlags = Area.FIRED;
//
//                if (result.equals(Game.StrikeResult.HIT) || result.equals(Game.StrikeResult.KILL)) {
//                    areaFlags += Area.SHIP;
//                }
//
//                knownArea.set(cell.x, cell.y, areaFlags);
//            }
//        }
//
//        parentView.reDraw();
//        return true;
//    }
//}
