package com.assignments.eventRegistrationSystemWithDesignPatterns.adapter;

import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Event;

import java.util.List;

/**
 * target interface for adapter pattern: defines what our application expects.
 * this is the interface our jsp/servlets will use to access event data.
 */
public interface EventDataSource {
	
	// getters
	List<Event> fetchAllEvents();
	
	// utility
	Event findEventById(String id);
	int getTotalEventCount();
	boolean eventExists(String id);
}
