//package ru.alexlen.jbs.stage.disposal;
//
//import static ru.alexlen.jbs.game.DisposalLogic.*;
//
//public class Disposal {
//    public ShipsArea ships;
//    public Ship draftShip;
//
//    public int[] availShips = AVAIL_SHIPS_AGR;
//
//    public Disposal(ShipsArea ships) {
//        this.ships = ships;
//    }
//
//    public boolean addShip(final Ship ship) {
//        if (!possibleAddShip(ship)) {
//            return false;
//        }
//
//        availShips[ship.size()]--;
//        ships.add(ship);
//        return true;
//    }
//
//    public void addDraftShip(Ship ship) {
//
//        if (!ships.freeSpaceForShip(ship)) {
//            return;
//        }
//
//        if (possibleAddShip(ship)) {
//            ship.setImpossible(false);
//        } else {
//            ship.setImpossible(true);
//        }
//
//        draftShip = ship;
//    }
//
//    public boolean possibleAddShip(final Ship ship) {
//
//        return availShips[ship.size()] != 0 && ships.freeSpaceForShip(ship);
//    }
//
//    public void commitDraftShip() {
//        if (draftShip != null) {
//            addShip(draftShip);
//            draftShip = null;
//        }
//    }
//
//    public boolean isComplete() {
//        for (int numberAvailShips : availShips) {
//            if (numberAvailShips > 0) return false;
//        }
//
//        return true;
//    }
//
//}
