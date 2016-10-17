package com.github.maxopoly.dogfacts.facts.picker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.github.maxopoly.dogfacts.facts.Fact;

public class RandomFactPicker implements IFactPicker {
	
	private List <Fact> facts;
	private Random rng;

	public RandomFactPicker(Collection<Fact> facts) {
		this.facts = new ArrayList<Fact>(facts);
		if (this.facts.size() == 0) {
			throw new IllegalArgumentException("You have to specify facts for this plugin to work");
		}
		this.rng = new Random();
	}

	@Override
	public Fact getFact() {
		return facts.get(rng.nextInt(facts.size()));
	}
	
	@Override
	public Collection<Fact> getAllFacts() {
		return facts;
	}

}
