package com.github.maxopoly.dogfacts.facts.picker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.maxopoly.dogfacts.facts.Fact;

public class OrderedFactPicker implements IFactPicker {
	
	private final List<Fact> facts;
	private int index;
	
	public OrderedFactPicker(Collection<Fact> facts) {
		this.facts = new ArrayList<Fact>(facts);
		if (this.facts.size() == 0) {
			throw new IllegalArgumentException("You have to specify facts for this plugin to work");
		}
		index = 0;
	}

	@Override
	public Fact getFact() {
		Fact fact = facts.get(index++);
		if (index >= facts.size()) {
			index = 0;
		}
		return fact;
	}

	@Override
	public Collection<Fact> getAllFacts() {
		return facts;
	}
}
