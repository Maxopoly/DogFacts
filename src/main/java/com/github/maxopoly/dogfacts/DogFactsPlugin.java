package com.github.maxopoly.dogfacts;

import org.bukkit.Bukkit;

import com.github.maxopoly.dogfacts.commands.DogFactsCommandHandler;
import com.github.maxopoly.dogfacts.database.DogFactsDAO;
import com.github.maxopoly.dogfacts.facts.FactManager;
import com.github.maxopoly.dogfacts.facts.FactRunnable;

import vg.civcraft.mc.civmodcore.ACivMod;

public class DogFactsPlugin extends ACivMod {

    private static DogFactsPlugin instance;
    private FactManager manager;
    private DogFactsDAO dao;

    public void onEnable() {
		super.onEnable();
		instance = this;
		handle = new DogFactsCommandHandler();
		handle.registerCommands();
		reload();
    }

    public void onDisable() {

    }

    @Override
    protected String getPluginName() {
    	return "DogFacts";
    }

    public static DogFactsPlugin getInstance() {
    	return instance;
    }
    
    /**
     * Reloads the entire config
     */
    public void reload() {
    	DogFactsConfig config = new DogFactsConfig(this);
		config.parse();
		manager = config.getManager();
		dao = config.getDAO();
		if (FactRunnable.PID != -1) {
			Bukkit.getScheduler().cancelTask(FactRunnable.PID);
		}
		FactRunnable.PID = new FactRunnable().runTaskTimer(this, config.getIntervall(), config.getIntervall()).getTaskId();
    }
    
    public static FactManager getManager() {
    	return getInstance().manager;
    }
    
    public static DogFactsDAO getDAO() {
    	return getInstance().dao;
    }

}
