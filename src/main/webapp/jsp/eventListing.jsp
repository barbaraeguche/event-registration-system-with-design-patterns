<%@ page import="java.util.List" %>
<%@ page import="com.assignments.eventRegistrationSystemWithDesignPatterns.model.Event" %>
<%@ page import="com.assignments.eventRegistrationSystemWithDesignPatterns.model.Registration" %>
<%@ page import="com.assignments.eventRegistrationSystemWithDesignPatterns.manager.RegistrationManager" %>
<%@ page import="com.assignments.eventRegistrationSystemWithDesignPatterns.decorator.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%!
	// manager instance uses adapter pattern internally
	private final RegistrationManager registrationManager = new RegistrationManager();
	
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
%>
<%
	List<Event> events = registrationManager.getAllEvents();
	
	@SuppressWarnings("unchecked")
	List<Registration> registrations = (List<Registration>) session.getAttribute("registrations");
	int registrationCount = registrationManager.getRegistrationCount(registrations);
%>
<!DOCTYPE html>
<html>
<head>
	<title>Event Listing</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<div class="container">
	<header>
		<h2>Upcoming Events</h2>
		<a href="<%= request.getContextPath() %>/jsp/registrationSummary.jsp" class="my-registrations">
			My registrations (<%= registrationCount %>)
		</a>
	</header>
	
	<div class="events-list">
		<p>Choose the number of participants then click <b>Register</b>.</p>
		
		<div>
			<%
				for (Event event : events) {
					// use decorator pattern for enhanced display
					EventDecorator decoratedEvent = decorateEvent(event);
			%>
					<div class="event-card">
						<%-- heading with decorated name --%>
						<h3><%= decoratedEvent.getDisplayName() %></h3>
						<p>Date: <%= decoratedEvent.getDateTime() %> | Location: <%= decoratedEvent.getLocation() %></p>
						
						<%-- description with decoration --%>
						<p><%= decoratedEvent.getDisplayDescription() %></p>
						
						<%-- registration + price --%>
						<form
							method="post"
							class="registration-form"
							action="<%= request.getContextPath() %>/jsp/registerEvent.jsp"
						>
							<input type="hidden" name="eventId" value="<%= decoratedEvent.getId() %>">
							
							<label for="participants-<%= decoratedEvent.getId() %>">
								Participants:
								<select name="participants" id="participants-<%= decoratedEvent.getId() %>">
									<% for (int i = 1; i <= 5; i++) { %>
										<option value="<%= i %>"><%= i %></option>
									<% } %>
								</select>
							</label>
							
							<div>
								<button type="submit" class="btn-register">Register</button>
								<p><%= decoratedEvent.getDisplayPrice() %></p>
							</div>
						</form>
					</div>
			<% } %>
		</div>
	</div>
</div>
</body>
</html>
