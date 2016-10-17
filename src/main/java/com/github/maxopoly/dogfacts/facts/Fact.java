package com.github.maxopoly.dogfacts.facts;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.maxopoly.dogfacts.DogFactsPlugin;

public class Fact {

	private String identifier;
	private String text;
	private Set<UUID> ignores;

	public Fact(String identifier, String text) {
		this.identifier = identifier;
		this.text = text;
		this.ignores = new HashSet<UUID>();
	}

	public boolean dismissedBy(UUID uuid) {
		return true;
	}

	public void show(Player p) {

	}

	public String getText() {
		return text;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void addIgnore(UUID uuid, boolean saveToDb) {
		if (!ignores.contains(uuid)) {
			ignores.add(uuid);
			if (saveToDb) {
				DogFactsPlugin.getDAO().insertFactIgnoreAsync(uuid, this);
			}
		}
	}

}
