<%@ page import="java.util.List" %>
<%@ page import="com.assignments.eventRegistrationSystemWithDesignPatterns.model.Registration" %>
<%@ page import="com.assignments.eventRegistrationSystemWithDesignPatterns.manager.RegistrationManager" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
	// manager instance uses adapter pattern internally
	RegistrationManager registrationManager = new RegistrationManager();
	
	// get the passed parameters
	String eventId = request.getParameter("eventId");
	int participants = Integer.parseInt(request.getParameter("participants"));
	
	@SuppressWarnings("unchecked")
	List<Registration> registrations = (List<Registration>) session.getAttribute("registrations");
	
	// internally uses:
	// - adapter (EventDataAdapter to access EventData)
	// - singleton (EventData.getInstance())
	// - factory (RegistrationFactory.createRegistration())
	registrations = registrationManager.registerForEvent(registrations, eventId, participants);
	
	// store updated registrations
	session.setAttribute("registrations", registrations);
	// stay on the same page
	response.sendRedirect("%s/jsp/eventListing.jsp".formatted(request.getContextPath()));
%>
