package com.assignments.eventRegistrationSystemWithDesignPatterns.decorator;

import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Event;

/**
 * decorator pattern: abstract base decorator for Event.
 * it allows dynamic enhancement of event objects without modifying the Event class.
 * its subclasses can add visual enhancements, price modifications, or other features
 */
public abstract class EventDecorator extends Event {
	
	// member variable
	protected Event event;
	
	public EventDecorator(Event event) {
		super(
			event.getId(), event.getName(),
			event.getDateTime(), event.getLocation(),
			event.getDescription(), event.getPricePerParticipant()
		);
		this.event = event;
	}
	
	// getters
	@Override
	public String getId() {
		return event.getId();
	}
	@Override
	public String getName() {
		return event.getName();
	}
	@Override
	public String getDateTime() {
		return event.getDateTime();
	}
	@Override
	public String getLocation() {
		return event.getLocation();
	}
	@Override
	public String getDescription() {
		return event.getDescription();
	}
	@Override
	public int getPricePerParticipant() {
		return event.getPricePerParticipant();
	}
	
	public Event getEvent() {
		return event;
	}
	
	// utility
	public abstract String getDisplayName();
	public abstract String getDisplayDescription();
	public abstract String getDisplayPrice();
}
