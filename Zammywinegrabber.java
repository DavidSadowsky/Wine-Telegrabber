package org.davidsadowsky.zammywinegrabber;

import org.davidsadowsky.zammywinegrabber.data.Location;
import org.davidsadowsky.zammywinegrabber.data.Time;
import org.davidsadowsky.zammywinegrabber.tasks.Banking;
import org.davidsadowsky.zammywinegrabber.tasks.Telegrab;
import org.davidsadowsky.zammywinegrabber.tasks.Traverse;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ScriptMeta(developer = "David Sadowsky", name = "Zammy Wine Grabber", desc = "Telegrabs wines from the second floor of the Zamorak Temple north of Falador")

public class Zammywinegrabber extends TaskScript {

    private static final Task[] TASKS = { new Banking(), new Traverse(), new Telegrab() };
    public static final int[] suppliesIDS = { 555, 563 };
    public static final int WINEID = 245;
    public static final List<Predicate<Item>> SUPPLIES_PREDICATES = new ArrayList<Predicate<Item>>(){{
        add(item -> item.getName().contains("Water rune"));
        add(item -> item.getName().contains("Law rune"));
    }};

    public static Time time;
    public static Location location;
    public static int wine = 31581;

    @Override
    public void onStart() {
        new ZammywinegrabberGUI().setVisible(true);
        submit(TASKS);
    }
}