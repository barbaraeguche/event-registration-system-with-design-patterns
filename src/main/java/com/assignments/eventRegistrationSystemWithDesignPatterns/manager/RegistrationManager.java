package com.assignments.eventRegistrationSystemWithDesignPatterns.manager;

import com.assignments.eventRegistrationSystemWithDesignPatterns.adapter.EventDataAdapter;
import com.assignments.eventRegistrationSystemWithDesignPatterns.adapter.EventDataSource;
import com.assignments.eventRegistrationSystemWithDesignPatterns.factory.RegistrationFactory;
import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Event;
import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Registration;

import java.util.ArrayList;
import java.util.List;

/**
 * manages registration operations using design patterns:
 * - adapter pattern: accesses event data through EventDataSource interface
 * - factory pattern: creates registrations through RegistrationFactory
 */
public class RegistrationManager {
	
	// member variable
	private final EventDataSource eventDataSource;
	
	public RegistrationManager() {
		this.eventDataSource = new EventDataAdapter();
	}
	
	// helper
	public List<Registration> registerForEvent(List<Registration> registrations, String eventId, int participants) {
		if (registrations == null) {
			registrations = new ArrayList<>();
		}
		
		Event event = eventDataSource.findEventById(eventId);
		if (event == null) {
			return registrations;
		}
		
		// check if already registered
		boolean isRegistered = false;
		for (Registration reg : registrations) {
			if (reg.getEvent().getId().equals(eventId)) {
				reg.setNumOfParticipants(participants);
				isRegistered = true;
				break;
			}
		}
		
		// create new registration using factory
		if (!isRegistered) {
			try {
				Registration newRegistration = RegistrationFactory.createRegistration(event, participants);
				registrations.add(newRegistration);
			} catch (IllegalArgumentException e) {
				System.err.println("Failed to create registration: " + e.getMessage());
			}
		}
		
		return registrations;
	}
	
	public List<Registration> updateRegistration(List<Registration> registrations, int index, int participants) {
		if (isInValidIndex(registrations, index)) { return registrations; }
		// signal to show warning
		if (participants == 0) { return null; }
		
		if (RegistrationFactory.isInValidParticipantCount(participants)) {
			return registrations;
		}
		
		// update the number of participants
		registrations.get(index).setNumOfParticipants(participants);
		return registrations;
	}
	
	public List<Registration> cancelRegistration(List<Registration> registrations, int index) {
		if (isInValidIndex(registrations, index)) { return registrations; }
		
		registrations.remove(index);
		return registrations;
	}
	
	public int calculateTotalCost(List<Registration> registrations) {
		if (registrations == null) {
			return 0;
		}
		
		return registrations.stream()
			.mapToInt(Registration::getTotalCost)
			.sum();
	}
	
	// getters
	public List<Event> getAllEvents() {
		return eventDataSource.fetchAllEvents();
	}
	
	// utility
	public Event getEventById(String eventId) {
		return eventDataSource.findEventById(eventId);
	}
	public int getTotalEventCount() {
		return eventDataSource.getTotalEventCount();
	}
	public boolean eventExists(String eventId) {
		return eventDataSource.eventExists(eventId);
	}
	
	public int getRegistrationCount(List<Registration> registrations) {
		return registrations == null ? 0 : registrations.size();
	}
	
	private boolean isInValidIndex(List<Registration> registrations, int index) {
		return registrations == null || index < 0 || index >= registrations.size();
	}
}
