package org.davidsadowsky.zammywinegrabber.tasks;

import org.davidsadowsky.firemaking.Autofiremaker;
import org.davidsadowsky.zammywinegrabber.Zammywinegrabber;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.Game;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Equipment;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.time.LocalTime;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public class Banking extends Task {

    @Override
    public boolean validate() {
        boolean hasSupplies = true;
        for (int ID : Zammywinegrabber.suppliesIDS) {
            if (Inventory.getCount(ID) == 0) {
                hasSupplies = false;
                break;
            }
        }
        return Zammywinegrabber.location != null && ((Inventory.contains(Zammywinegrabber.WINEID) || !hasSupplies) && Zammywinegrabber.location.getBankArea().contains(Players.getLocal()));
    }

    @Override
    public int execute() {
        if (Bank.isOpen()) {
            Bank.depositAllExcept(Zammywinegrabber.suppliesIDS);
            Time.sleepUntil(new BooleanSupplier() {
                @Override
                public boolean getAsBoolean() {
                    return !Inventory.contains(Zammywinegrabber.WINEID);
                }
            }, 5000);
            for (Predicate<Item> item : Zammywinegrabber.SUPPLIES_PREDICATES) {
                if (!Inventory.contains(item)) {
                    Log.info("Withdrawing required supplies.");
                    Bank.withdraw(item, 1000000);
                    Time.sleepUntil(new BooleanSupplier() {
                        @Override
                        public boolean getAsBoolean() {
                            return Inventory.contains(item);
                        }
                    }, 2000);
                    Time.sleep(200);
                }
            }

            if (!Inventory.contains(Zammywinegrabber.suppliesIDS)) {
                Log.severe("Insufficient supplies to continue. Exiting.");
                Game.logout();
                return -1;
            }
        } else {
            Bank.open();
        }
        return Random.nextInt(400, 600);
    }
}