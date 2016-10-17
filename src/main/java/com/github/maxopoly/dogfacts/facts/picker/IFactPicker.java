package com.github.maxopoly.dogfacts.facts.picker;

import java.util.Collection;

import com.github.maxopoly.dogfacts.facts.Fact;

public interface IFactPicker {
	
	public Fact getFact();
	
	public Collection <Fact> getAllFacts();

}
