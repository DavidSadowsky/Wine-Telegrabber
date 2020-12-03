package org.davidsadowsky.zammywinegrabber.tasks;

import org.davidsadowsky.zammywinegrabber.Zammywinegrabber;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.PathingEntity;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Magic;
import org.rspeer.runetek.api.component.tab.Spell;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.Objects;
import java.util.function.BooleanSupplier;

import static org.rspeer.runetek.api.component.tab.Magic.isSpellSelected;

public class Telegrab extends Task {
    @Override
    public boolean validate() {
        return Zammywinegrabber.location != null && (Players.getLocal().getFloorLevel() == 1 || Zammywinegrabber.location.getTempleArea().contains(Players.getLocal())) && Inventory.getCount() < 28;
    }

    @Override
    public int execute() {
        PathingEntity monk = Npcs.getNearest("Monk of zamorak");
        if(monk.isHealthBarVisible()) {
            WorldHopper.randomHopInF2p();
            Time.sleepUntil(new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    return Game.getState() == Game.STATE_HOPPING_WORLD;
                }
            }, 2000);
            Time.sleepUntil(new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    return Game.getState() == Game.STATE_IN_GAME;
                }
            }, 2000);
        }
        while(Players.getLocal().getFloorLevel() != 1) {
            SceneObjects.getNearest("Ladder").interact("Climb");
            Time.sleepUntil(new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    return Players.getLocal().getPosition().getFloorLevel() == 1;
                }
            }, 5000);
        }
        Pickable zammyWine = Pickables.getNearest("Wine of zamorak");
        if(!isSpellSelected()) Magic.cast(Spell.Modern.TELEKINETIC_GRAB);
        if(zammyWine != null) {
            final int numWines = Inventory.getCount(Zammywinegrabber.WINEID);
            Magic.cast(Spell.Modern.TELEKINETIC_GRAB, zammyWine);
            Time.sleepUntil(new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    return Players.getLocal().isAnimating();
                }
            },2000);
            Time.sleepUntil(new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    return Inventory.getCount(Zammywinegrabber.WINEID) != numWines;
                }
            },2000);
        }
        return 0;
    }
}
