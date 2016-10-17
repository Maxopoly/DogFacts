package com.github.maxopoly.dogfacts.commands.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.github.maxopoly.dogfacts.DogFactsPlugin;

import vg.civcraft.mc.civmodcore.command.PlayerCommand;

public class ReloadDogFacts extends PlayerCommand {

	public ReloadDogFacts(String name) {
		super(name);
		setIdentifier("dfreload");
		setDescription("Reloads the config for this plugin");
		setUsage("/dfreload");
		setArguments(0, 0);
	}

	@Override
	public boolean execute(CommandSender arg0, String[] arg1) {
		arg0.sendMessage(ChatColor.GREEN + "Reloading DogFacts");
		DogFactsPlugin.getInstance().reload();
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}

}
