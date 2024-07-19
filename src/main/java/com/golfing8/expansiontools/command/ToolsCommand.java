package com.golfing8.expansiontools.command;

import com.golfing8.expansiontools.feature.ToolsFeature;
import com.golfing8.expansiontools.util.ChatUtil;
import com.golfing8.kore.FactionsKore;
import com.golfing8.kore.command.FactionsKoreCommand;
import com.golfing8.kore.command.exception.ImproperArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ToolsCommand extends FactionsKoreCommand<ToolsFeature> {
    public ToolsCommand(Plugin main, String name, ToolsFeature feature) {
        super(main, name, feature);

        //These addCommand methods are for the sendHelpMessage method only and have no implementation other than that.
        //Use addAdminCommand for commands that are only for admins.
        addCommand("tools", "Give staff permission to give tools");

        //Use this method and the command will be run async. Useful in some situations.
        setAsync();
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws ImproperArgumentException {

        if (args.length < 3) {
            sender.sendMessage(ChatUtil.color(getFeature().getInvalidArgs()));
            return;
        }

        if (!sender.hasPermission("factionskore.admin.give")) {
            sender.sendMessage(ChatUtil.color(getFeature().getNoPerm()));
            return;
        }

        if (args[0].equalsIgnoreCase("give")) {


            Player t = Bukkit.getPlayer(args[1]);
            if (!(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1])))) {
                sender.sendMessage(getFeature().getInvalidPlayer());
            } else {

                String type;

                try {
                    type = args[2];
                } catch (Exception e) {
                    sender.sendMessage(ChatUtil.color(
                            getFeature().getInvalidType()));
                    return;
                }

                if (!getFeature().getConfig().getConfigurationSection("feature.tools").getKeys(false).contains(type)) {
                    sender.sendMessage(ChatUtil.color(
                            getFeature().getInvalidType()));
                    return;
                }

                int radius = getFeature().getConfig().getInt("feature.tools." + type + ".radius");
                sender.sendMessage(ChatUtil.color(getFeature().giveTrench()
                        .replace("%player%", t.getName())
                        .replace("%radius%", radius + "x" + radius)));
                t.sendMessage(ChatUtil.color(getFeature().givenTrench()
                        .replace("%radius%", radius + "x" + radius)));
                t.getInventory().addItem(getFeature().buildTrenchTool(type));
            }
        }
    }
}
