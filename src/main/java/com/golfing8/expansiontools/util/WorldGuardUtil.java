package com.golfing8.expansiontools.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardUtil {
    public static boolean canBreak(Player p, Location location) {
        boolean result = true;
        RegionQuery query = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
        com.sk89q.worldedit.world.World world = BukkitAdapter.adapt(location.getWorld());
        if (!com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(WorldGuardPlugin.inst().wrapPlayer(p), world)) {
            result = query.testState(loc, WorldGuardPlugin.inst().wrapPlayer(p), Flags.BLOCK_BREAK);
        }
        return result;
    }
}
