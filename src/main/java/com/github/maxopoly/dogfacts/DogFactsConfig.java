package com.github.maxopoly.dogfacts;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.maxopoly.dogfacts.database.DogFactsDAO;
import com.github.maxopoly.dogfacts.facts.Fact;
import com.github.maxopoly.dogfacts.facts.FactManager;
import com.github.maxopoly.dogfacts.facts.picker.IFactPicker;
import com.github.maxopoly.dogfacts.facts.picker.OrderedFactPicker;
import com.github.maxopoly.dogfacts.facts.picker.RandomFactPicker;

import vg.civcraft.mc.civmodcore.dao.ManagedDatasource;
import vg.civcraft.mc.civmodcore.util.ConfigParsing;

public class DogFactsConfig {

	private long intervall;
	private FactManager manager;
	private ManagedDatasource db;
	private DogFactsDAO dao;

	private final DogFactsPlugin plugin;

	public DogFactsConfig(DogFactsPlugin plugin) {
		this.plugin = plugin;
	}

	public void parse() {
		plugin.saveDefaultConfig();
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();
		Collection<Fact> facts = parseFacts(config.getConfigurationSection("facts"));
		plugin.info("Parsed a total of " + facts.size() + " facts");
		this.intervall = ConfigParsing.parseTime(config.getString("intervall"));
		String mode = config.getString("mode");
		IFactPicker picker;
		switch (mode.toLowerCase()) {
		case "random":
			picker = new RandomFactPicker(facts);
			break;
		case "ordered":
			picker = new OrderedFactPicker(facts);
			break;
		default:
			plugin.warning("Could not resolve the mode " + mode + ". Defaulting to random");
			picker = new RandomFactPicker(facts);
		}
		this.manager = new FactManager(picker);
		this.db = (ManagedDatasource) config.get("database");
		this.dao = new DogFactsDAO(db, plugin.getLogger());
	}

	private Collection<Fact> parseFacts(ConfigurationSection config) {
		List<Fact> facts = new LinkedList<Fact>();
		for (String key : config.getKeys(false)) {
			if (!config.isString(key)) {
				plugin.warning("Found invalid config option at " + config.getCurrentPath() + "." + key
						+ ". Only strings are allowed here");
				continue;
			}
			facts.add(new Fact(key, config.getString(key)));
		}
		return facts;
	}

	/**
	 * @return How often facts are sent out in ticks
	 */
	public long getIntervall() {
		return intervall;
	}

	/**
	 * Gets the manager constructed by parsing the config
	 * 
	 * @return Complete setup manager depending on the config (assuming the
	 *         parse() method was run)
	 */
	public FactManager getManager() {
		return manager;
	}
	
	public DogFactsDAO getDAO() {
		return dao;
	}
}
