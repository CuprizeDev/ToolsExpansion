package com.golfing8.expansion.feature;

import com.golfing8.kore.feature.Feature;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.UUID;

public class ExampleFeature extends Feature {
    public ExampleFeature(Plugin plugin, String name, String description, String basePermDesc, String adminPermDesc) {
        super(plugin, name, description, basePermDesc, adminPermDesc);

        //Use this method to enable dataconfig methods in your feature.
        setDataFolder();
    }

    private BukkitTask task;

    //This method is used to unregister your bukkit tasks and anything else you may need on disable.
    public void onDisable() {
        //This method returns false if the task provided is null or already cancelled.
        // It is useful to determine if the feature was on/off when this method was run as this method can be run while the feature is disabled.
        if(!cancelTask(task))return;
    }

    public void onEnable() {
        this.startTask();
    }

    private void startTask(){
        this.task = new BukkitRunnable(){
            @Override
            public void run(){
                Bukkit.getLogger().info("[FactionsKore] - Example feature is enabled!");
            }
        }.runTaskTimer(getPlugin(), 0, 20);
    }

    //Features have built in listener support.
    // They are unregistered when the feature is disabled automatically.
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.setJoinMessage("Player joined!");
    }
}
