package com.github.maxopoly.dogfacts.commands;
import com.github.maxopoly.dogfacts.commands.commands.ReloadDogFacts;

import vg.civcraft.mc.civmodcore.command.CommandHandler;

public class DogFactsCommandHandler extends CommandHandler {

    @Override
    public void registerCommands() {
    	addCommands(new ReloadDogFacts("ReloadDogFacts"));
    }
}
