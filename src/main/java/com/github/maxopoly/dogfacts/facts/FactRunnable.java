package com.github.maxopoly.dogfacts.facts;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FactRunnable extends BukkitRunnable {
	
	private FactManager factManager;
	public static int PID = -1;

	@Override
	public void run() {
		Fact fact = factManager.getFact();
		for(Player p : Bukkit.getOnlinePlayers()) {
			if (factManager.isIgnoringAllFacts(p.getUniqueId())) {
				//player doesnt want to see any facts
				continue;
			}
			if (fact.dismissedBy(p.getUniqueId())) {
				//player doesnt want to see this fact
				continue;
			}
			fact.show(p);
		}
		
	}
}
