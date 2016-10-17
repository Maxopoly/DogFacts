package com.github.maxopoly.dogfacts.facts;

import java.util.Map;
import java.util.UUID;

import com.github.maxopoly.dogfacts.DogFactsPlugin;
import com.github.maxopoly.dogfacts.facts.picker.IFactPicker;

public class FactManager {

	private IFactPicker factPicker;
	private Map<String, Fact> factByIdentifier;

	public FactManager(IFactPicker factPicker) {
		this.factPicker = factPicker;
		for (Fact fact : factPicker.getAllFacts()) {
			factByIdentifier.put(fact.getIdentifier(), fact);
		}
	}

	public Fact getFact() {
		return factPicker.getFact();
	}

	/**
	 * Checks whether a player has chosen to ignore all facts
	 * 
	 * @param player
	 *            Player to check for
	 * @return True if the player has explicitly disabled showing any facts,
	 *         false if not
	 */
	public boolean isIgnoringAllFacts(UUID player) {
		return false;
	}

	/**
	 * Retrieves a fact by it's unique identifier
	 * 
	 * @param identifier
	 *            Identifier of the fact
	 * @return Fact with the given identifier or null if no such fact exists
	 */
	public Fact getFact(String identifier) {
		return factByIdentifier.get(identifier);
	}
	
	public void loadIgnores() {
		DogFactsPlugin.getDAO().loadAllIgnores(factByIdentifier);
	}

}
