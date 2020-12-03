package org.davidsadowsky.zammywinegrabber.tasks;

import org.davidsadowsky.zammywinegrabber.Zammywinegrabber;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Magic;
import org.rspeer.runetek.api.component.tab.Spell;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class Traverse extends Task {

    @Override
    public boolean validate() {
        return Zammywinegrabber.location != null && (traverseToBank() || traverseToTemple());
    }

    @Override
    public int execute() {
        if (traverseToBank()) {
            if(Zammywinegrabber.location.getTempleArea().contains(Players.getLocal())) {
                Log.info(Zammywinegrabber.location.getTempleArea().contains(Players.getLocal()));
               Magic.cast(Spell.Modern.FALADOR_TELEPORT);
                Time.sleepUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() {
                        return Area.rectangular(new Position(2959, 3377, 0), new Position(2961, 3386, 0)).contains(Players.getLocal());
                    }
                }, 5000);
            }
            List<Position> tiles = Zammywinegrabber.location.getBankArea().getTiles();
            if (!Players.getLocal().isAnimating() && !Players.getLocal().isMoving()) Movement.walkTo(tiles.get(Random.nextInt(0, tiles.size() - 1)));
        } else {
            List<Position> tiles = Zammywinegrabber.location.getTempleArea().getTiles();
            if (!Players.getLocal().isAnimating() && !Players.getLocal().isMoving()) Movement.walkTo(tiles.get(Random.nextInt(0, tiles.size() - 1)));
        }
        return Random.nextInt(400, 600);
    }

    private boolean traverseToBank() {
        boolean hasSupplies = true;
        for(Predicate<Item> item : Zammywinegrabber.SUPPLIES_PREDICATES) {
            if(!Inventory.contains(item)) {
                hasSupplies = false;
                break;
            }
        }
        return (Inventory.isFull() || !hasSupplies) && !Zammywinegrabber.location.getBankArea().contains(Players.getLocal());
    }

    private boolean traverseToTemple() {
        boolean readyToTraverse = true;
        for(Predicate<Item> item : Zammywinegrabber.SUPPLIES_PREDICATES) {
            if(!Inventory.contains(item)) {
                readyToTraverse = false;
                break;
            }
        }
        if(Inventory.contains(Zammywinegrabber.WINEID) || Players.getLocal().getFloorLevel() == 1) readyToTraverse = false;
        return (Zammywinegrabber.location != null) && readyToTraverse && !Zammywinegrabber.location.getTempleArea().contains(Players.getLocal());
    }
}