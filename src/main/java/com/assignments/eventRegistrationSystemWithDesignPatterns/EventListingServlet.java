package com.assignments.eventRegistrationSystemWithDesignPatterns;

import com.assignments.eventRegistrationSystemWithDesignPatterns.decorator.DiscountEventDecorator;
import com.assignments.eventRegistrationSystemWithDesignPatterns.decorator.EventDecorator;
import com.assignments.eventRegistrationSystemWithDesignPatterns.decorator.FeaturedEventDecorator;
import com.assignments.eventRegistrationSystemWithDesignPatterns.manager.RegistrationManager;
import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Event;
import com.assignments.eventRegistrationSystemWithDesignPatterns.model.Registration;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * servlet that displays event listings and handles registrations.
 * it demonstrates usage of multiple design patterns:
 * - singleton: EventData accessed through getInstance()
 * - factory: registration creation through RegistrationFactory
 * - adapter: EventDataAdapter adapts EventData to EventDataSource interface
 * - decorator: enhanced event display with EventDecorator
 */
@WebServlet("/servlet/events")
public class EventListingServlet extends HttpServlet {
	
	// member variable
	private final RegistrationManager registrationManager = new RegistrationManager();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		List<Event> events = registrationManager.getAllEvents();
		
		@SuppressWarnings("unchecked")
		List<Registration> registrations = (List<Registration>) session.getAttribute("registrations");
		int registrationCount = registrationManager.getRegistrationCount(registrations);
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Event Listing</title>");
		out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/style.css'>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<div class='container'>");
		out.println("<header>");
		out.println("<h2>Upcoming Events</h2>");
		out.println("<a href='" + request.getContextPath() + "/servlet/registrations' class='my-registrations'>");
		out.println("My registrations (" + registrationCount + ")");
		out.println("</a>");
		out.println("</header>");
		
		out.println("<div class='events-list'>");
		out.println("<p>Choose the number of participants then click <b>Register</b>.</p>");
		out.println("<div>");
		
		for (Event event : events) {
			EventDecorator decoratedEvent = decorateEvent(event);
			
			out.println("<div class='event-card'>");
			
			out.println("<h3>" + decoratedEvent.getDisplayName() + "</h3>");
			out.println("<p>Date: " + decoratedEvent.getDateTime() + " | Location: " + decoratedEvent.getLocation() + "</p>");
			out.println("<p>" + decoratedEvent.getDisplayDescription() + "</p>");
			
			out.println("<form method='post' action='" + request.getContextPath() + "/servlet/events' class='registration-form'>");
			out.println("<input type='hidden' name='eventId' value='" + decoratedEvent.getId() + "'>");
			out.println("<label for='participants-" + decoratedEvent.getId() + "'>Participants:");
			out.println("<select name='participants' id='participants-" + decoratedEvent.getId() + "'>");
			for (int i = 1; i <= 5; i++) {
				out.println("<option value='" + i + "'>" + i + "</option>");
			}
			out.println("</select>");
			out.println("</label>");
			out.println("<div>");
			out.println("<button type='submit' class='btn-register'>Register</button>");
			out.println("<p>" + decoratedEvent.getDisplayPrice() + "</p>");
			out.println("</div>");
			out.println("</form>");
			
			out.println("</div>");
		}
		
		out.println("</div");
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String eventId = request.getParameter("eventId");
		int participants = Integer.parseInt(request.getParameter("participants"));
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Registration> registrations = (List<Registration>) session.getAttribute("registrations");
		
		// register for an event
		registrations = registrationManager.registerForEvent(registrations, eventId, participants);
		session.setAttribute("registrations", registrations);
		
		response.sendRedirect(request.getContextPath() + "/servlet/events");
	}
	
	/**
	 * decorator pattern: decorate events based on their id
	 * event 1: featured
	 * event 2: 20% discount
	 * event 3: normal (no decoration)
	 */
	private EventDecorator decorateEvent(Event event) {
		return switch (event.getId()) {
			case "1" -> new FeaturedEventDecorator(event);
			case "2" -> new DiscountEventDecorator(event, 20);
			default -> new EventDecorator(event) {
				@Override
				public String getDisplayName() {
					return event.getName();
				}
				
				@Override
				public String getDisplayDescription() {
					return event.getDescription();
				}
				
				@Override
				public String getDisplayPrice() {
					return "Price: $" + event.getPricePerParticipant() + " / participant";
				}
			};
		};
	}
}
