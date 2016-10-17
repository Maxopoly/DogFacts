package com.github.maxopoly.dogfacts.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.maxopoly.dogfacts.DogFactsPlugin;
import com.github.maxopoly.dogfacts.facts.Fact;

import vg.civcraft.mc.civmodcore.dao.ManagedDatasource;

public class DogFactsDAO {
	
	private ManagedDatasource db;
	private Logger logger;
	
	private final static String insertIgnore = "insert into ignoredFacts (id, uuid) select id, ? from facts where identifier = ?;";
	private final static String registerFact = "insert into facts (identifier) values(?);";
	private final static String getAllFacts = "select f.identifier, if.uuid from facts f inner join ignoredFacts if on f.id = if.id;";
	
	public DogFactsDAO(ManagedDatasource db, Logger logger) {
		this.db = db;
		this.logger = logger;
		registerMigrations();
		db.updateDatabase();
	}
	
	public void registerMigrations() {
		db.registerMigration(0, false, 
			"create table if not exists facts (id int not null autoincrement, identifier varchar(200) not null, primary key(id), unique(identifier));",
			"create table if not exists ignoredFacts (id int not null, uuid varchar(36) not null, unique(id, uuid), foreign key (id) references facts(id));"	
			"create table if not exists ignoringAllFacts "
		);
	}
	
	public void registerFactAsync(Fact fact) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				registerFact(fact);
			}
		}.runTaskAsynchronously(DogFactsPlugin.getInstance());
	}
	
	private void registerFact(Fact fact) {
		try(Connection connection = db.getConnection();
				PreparedStatement addFact = connection.prepareStatement(registerFact)) {
			addFact.setString(1, fact.getIdentifier());
			addFact.execute();			
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Unable to register fact " + fact.getIdentifier(), e);
		}
	}
	
	public void insertFactIgnoreAsync(UUID player, Fact fact) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				insertFactIgnore(player, fact);
			}
		}.runTaskAsynchronously(DogFactsPlugin.getInstance());
	}
	
	private void insertFactIgnore(UUID player, Fact fact) {
		try(Connection connection = db.getConnection();
				PreparedStatement addIgnore = connection.prepareStatement(insertIgnore)) {
			addIgnore.setString(1, player.toString());
			addIgnore.setString(2, fact.getIdentifier());
			addIgnore.execute();			
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Unable to insert ignoring for fact " + fact.getIdentifier() + " for player " + player , e);
		}
	}
	
	public void loadAllIgnores(Map <String, Fact> factsByIdentifier) {
		try(Connection connection = db.getConnection();
				PreparedStatement loadFacts = connection.prepareStatement(getAllFacts);
					ResultSet facts = loadFacts.executeQuery()) {
			while (facts.next()) {
				String identifier = facts.getString(1);
				UUID uuid = UUID.fromString(facts.getString(2));
				Fact f = factsByIdentifier.get(identifier);
				if (f != null) {
					f.addIgnore(uuid, false);
				}
			}	
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Unable to load all ignores", e);
		}
	}

}
