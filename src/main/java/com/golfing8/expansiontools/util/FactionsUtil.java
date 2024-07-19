package com.golfing8.expansiontools.util;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FactionsUtil {
    public static Boolean canBuild(Player p, Location location) {

        if (Board.getInstance().getFactionAt(new FLocation(location)).isWilderness()) {
            return true;
        }

        return FPlayers.getInstance().getByPlayer(p).getFaction() ==
                Board.getInstance().getFactionAt(new FLocation(location));
    }
}
