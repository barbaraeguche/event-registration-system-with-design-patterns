package com.assignments.eventRegistrationSystemWithDesignPatterns.decorator;

import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Event;

/**
 * decorator pattern: concrete decorator that adds discount functionality.
 * this decorator modifies the event price and displays the information to show a discount.
 */
public class DiscountEventDecorator extends EventDecorator {
	
	// member variables
	private final int originalPrice, discountPercentage, discountedPrice;
	
	public DiscountEventDecorator(Event event, int discountPercentage) {
		// calculate the discount before calling super to pass the correct price
		super(createDiscountedEvent(event, discountPercentage));
		
		this.discountPercentage = Math.max(0, Math.min(100, discountPercentage));
		this.originalPrice = event.getPricePerParticipant();
		this.discountedPrice = originalPrice - (originalPrice * this.discountPercentage / 100);
	}
	
	/**
	 * helper method: creates a new event with discounted price.
	 * this is called before the constructor runs, so we can pass it to super()
	 */
	private static Event createDiscountedEvent(Event event, int discountPercentage) {
		int clampedDiscount = Math.max(0, Math.min(100, discountPercentage));
		int originalPrice = event.getPricePerParticipant();
		int discountedPrice = originalPrice - (originalPrice * clampedDiscount / 100);
		
		return new Event(
			event.getId(), event.getName(),
			event.getDateTime(), event.getLocation(),
			event.getDescription(), discountedPrice
		);
	}
	
	// getters
	public int getOriginalPrice() {
		return originalPrice;
	}
	public int getDiscountPercentage() {
		return discountPercentage;
	}
	
	// utility
	@Override
	public int getPricePerParticipant() {
		return discountedPrice;
	}
	
	@Override
	public String getDisplayName() {
		return event.getName() + " [" + discountPercentage + "% OFF]";
	}
	
	@Override
	public String getDisplayDescription() {
		return event.getDescription() + " Special discount available for this event!";
	}
	
	@Override
	public String getDisplayPrice() {
		return "$" + discountedPrice + " / participant (was $" + originalPrice + ")";
	}
}
