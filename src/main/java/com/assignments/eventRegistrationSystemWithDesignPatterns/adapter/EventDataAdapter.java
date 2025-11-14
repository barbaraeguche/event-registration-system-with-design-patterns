package com.assignments.eventRegistrationSystemWithDesignPatterns.adapter;

import com.assignments.eventRegistrationSystemWithDesignPatterns.data.EventData;
import com.assignments.eventRegistrationSystemWithDesignPatterns.decorator.DiscountEventDecorator;
import com.assignments.eventRegistrationSystemWithDesignPatterns.decorator.EventDecorator;
import com.assignments.eventRegistrationSystemWithDesignPatterns.decorator.FeaturedEventDecorator;
import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Event;

import java.util.List;

/**
 * adapter pattern: adapts EventData (adaptee) to EventDataSource interface (target).
 * this allows the application to work with EventData through a standardized interface
 * without modifying the EventData class itself.
 */
public class EventDataAdapter implements EventDataSource {
	
	// member variable
	private final EventData eventData;
	
	public EventDataAdapter() {
		this.eventData = EventData.getInstance();
	}
	
	@Override
	public List<Event> fetchAllEvents() {
		// return base events without decorators
		// decorators are only applied in findEventById() when registering
		return eventData.getAllEvents();
	}
	
	@Override
	public Event findEventById(String id) {
		Event event = eventData.getEventById(id);
		
		if (event == null) { return null; }
		return applyDecorator(event);
	}
	
	@Override
	public int getTotalEventCount() {
		return eventData.getAllEvents().size();
	}
	
	@Override
	public boolean eventExists(String id) {
		return eventData.getEventById(id) != null;
	}
	
	private Event applyDecorator(Event event) {
		// prevent double-decoration
		if (event instanceof EventDecorator) {
			return event;
		}
		
		return switch (event.getId()) {
			case "1" -> new FeaturedEventDecorator(event);
			case "2" -> new DiscountEventDecorator(event, 20);
			default -> event;
		};
	}
}
