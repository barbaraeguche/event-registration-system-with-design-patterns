package com.assignments.eventRegistrationSystemWithDesignPatterns.decorator;

import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Event;

/**
 * decorator pattern: concrete decorator that adds "Featured" badge.
 * it enhances event presentation for special/popular events without changing the event itself.
 */
public class FeaturedEventDecorator extends EventDecorator {
	
	public FeaturedEventDecorator(Event event) {
		super(event);
	}
	
	// utility
	@Override
	public String getDisplayName() {
		return event.getName() + " [FEATURED]";
	}
	
	@Override
	public String getDisplayDescription() {
		return event.getDescription() + " This is a featured event!";
	}
	
	@Override
	public String getDisplayPrice() {
		return "$" + event.getPricePerParticipant() + " / participant (Featured Event)";
	}
}
