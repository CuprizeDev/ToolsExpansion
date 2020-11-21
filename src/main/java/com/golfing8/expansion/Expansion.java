package com.golfing8.expansion;

import com.golfing8.expansion.command.ExampleCommand;
import com.golfing8.expansion.feature.ExampleFeature;
import com.golfing8.kore.FactionsKore;
import com.golfing8.kore.expansion.FactionsKoreExpansion;

//This is your main class of the expansion you make. Info about it is in "kore-properties.yml".
public class Expansion extends FactionsKoreExpansion {

    //This is run when the expansion is enabled. Use this to register your features.
    public void onEnable() {
        //Features MUST be registered before their command.
        registerFeature(new ExampleFeature(FactionsKore.get(), "example", "Example feature", "Some description", "Some other description"));

        registerCommand(new ExampleCommand(FactionsKore.get(), "example", (ExampleFeature) getFeature("example")));
    }

    //This is run when the expansion is disabled.
    // The main plugin handles features enabling/disabling so don't worry about doing that here.
    public void onDisable() {

    }
}
