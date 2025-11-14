package com.assignments.eventRegistrationSystemWithDesignPatterns.data;

import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * singleton pattern: ensures only one instance of EventData exists.
 * it provides global access to event data throughout the application.
 */
public class EventData {
	
	// member variables
	private static EventData instance;
	private final List<Event> events;
	
	private EventData() {
		events = new ArrayList<>();
		initializeEvents();
	}
	
	/**
	 * singleton pattern: thread-safe instance retrieval using double-checked locking
	 */
	public static EventData getInstance() {
		if (instance == null) {
			synchronized (EventData.class) {
				if (instance == null) {
					instance = new EventData();
				}
			}
		}
		return instance;
	}
	
	// getters
	public List<Event> getAllEvents() {
		// return copy to prevent external modification
		return new ArrayList<>(events);
	}
	
	// utility
	public Event getEventById(String id) {
		for (Event event : events) {
			if (event.getId().equals(id)) {
				return event;
			}
		}
		return null;
	}
	
	private void initializeEvents() {
		events.add(new Event(
			"1",
			"Tech Talk: Modern Web Patterns",
			"Oct 8, 2025 -- 18:00",
			"Room H 110 SGW",
			"Short talk about modern web architecture patterns and best practices for team projects.",
			15
		));
		
		events.add(new Event(
			"2",
			"Hands-on Workshop: REST APIs",
			"Oct 15, 2025 -- 14:00",
			"Room H 962 SGW",
			"Practical workshop where attendees build and consume REST APIs using Express and Java Servlets.",
			25
		));
		
		events.add(new Event(
			"3",
			"24-Hour Hackathon",
			"Nov 2, 2025 -- 09:00",
			"Mezanine Hall SGW",
			"Team-based hackathon. Bring your laptop and ideas. Limited spots available.",
			10
		));
	}
}
