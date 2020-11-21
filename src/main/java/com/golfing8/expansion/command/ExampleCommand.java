package com.golfing8.expansion.command;

import com.golfing8.expansion.feature.ExampleFeature;
import com.golfing8.kore.FactionsKore;
import com.golfing8.kore.command.FactionsKoreCommand;
import com.golfing8.kore.command.exception.ImproperArgumentException;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ExampleCommand extends FactionsKoreCommand<ExampleFeature> {
    public ExampleCommand(Plugin main, String name, ExampleFeature feature) {
        super(main, name, feature);

        //These addCommand methods are for the sendHelpMessage method only and have no implementation other than that.
        //Use addAdminCommand for commands that are only for admins.
        addCommand("[some-argument]", "Description for argument");

        //Use this method and the command will be run async. Useful in some situations.
        setAsync();
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws ImproperArgumentException {
        sender.sendMessage(FactionsKore.c("&aHello!"));

        if(args.length == 0 || !hasAdminPermission(sender, true))return;

        //These parse commands throw an expansion so you wont need to return for bad arguments, it'll handle it for you.
        int integer = parseInt(args[0]);

        sender.sendMessage(FactionsKore.c("&cYou input: " + integer + "!"));
    }
}
