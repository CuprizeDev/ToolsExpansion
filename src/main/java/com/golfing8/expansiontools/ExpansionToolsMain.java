package com.golfing8.expansiontools;

import com.golfing8.expansiontools.command.ToolsCommand;
import com.golfing8.expansiontools.feature.ToolsFeature;
import com.golfing8.kore.FactionsKore;
import com.golfing8.kore.expansion.FactionsKoreExpansion;

//This is your main class of the expansion you make. Info about it is in "kore-properties.yml".
public class ExpansionToolsMain extends FactionsKoreExpansion {

    //This is run when the expansion is enabled. Use this to register your features.
    public void onEnable() {
        //Features MUST be registered before their command.
        registerFeature(new ToolsFeature(FactionsKore.get(), "tools", "Tools feature", "Use custom tools", "Give the tools"));
        registerCommand(new ToolsCommand(FactionsKore.get(), "tools", (ToolsFeature) getFeature("tools")));
    }

    //This is run when the expansion is disabled.
    // The main plugin handles features enabling/disabling so don't worry about doing that here.
    public void onDisable() {

    }
}
