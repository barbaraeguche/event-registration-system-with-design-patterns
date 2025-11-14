package com.assignments.eventRegistrationSystemWithDesignPatterns.factory;

import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Event;
import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Registration;

/**
 * factory pattern: creates Registration objects with validation.
 * it encapsulates the creation logic and ensures registrations are valid.
 */
public class RegistrationFactory {
	
	// member variables
	private static final int MIN_PARTICIPANTS = 1;
	private static final int MAX_PARTICIPANTS = 5;
	
	// helper
	public static Registration createRegistration(Event event, int participants) {
		if (event == null) {
			throw new IllegalArgumentException("Event cannot be null");
		}
		
		// validate participants to ensure validity
		if (isInValidParticipantCount(participants)) {
			throw new IllegalArgumentException(
				"Number of participants must be between " + MIN_PARTICIPANTS + " and " + MAX_PARTICIPANTS
			);
		}
		
		return new Registration(event, participants);
	}
	
	public static Registration createDefaultRegistration(Event event) {
		return createRegistration(event, 1);
	}
	
	// getters
	public static int getMaxParticipants() {
		return MAX_PARTICIPANTS;
	}
	public static int getMinParticipants() {
		return MIN_PARTICIPANTS;
	}
	
	// utility
	public static boolean isInValidParticipantCount(int participants) {
		return participants < MIN_PARTICIPANTS || participants > MAX_PARTICIPANTS;
	}
}
