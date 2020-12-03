package org.davidsadowsky.zammywinegrabber.data;

import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.movement.position.Position;

public enum Location {
    FALADOR("Falador", Area.rectangular(new Position(2943, 3370, 0), new Position(2947,3368,0)), Area.rectangular(new Position(2944, 3518, 0), new Position(2941,3517,0)));
    private final Area bankArea, templeArea;
    private final String name;

    Location(String name, final Area bankArea, final Area templeArea) {
        this.bankArea = bankArea;
        this.templeArea = templeArea;
        this.name = name;
    }

    public Area getBankArea() {
        return bankArea;
    }
    public Area getTempleArea() {
        return templeArea;
    }
    public String getName() { return name; }
}