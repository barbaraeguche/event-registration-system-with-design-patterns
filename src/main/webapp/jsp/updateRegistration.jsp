<%@ page import="java.util.List" %>
<%@ page import="com.assignments.eventRegistrationSystemWithDesignPatterns.model.Registration" %>
<%@ page import="com.assignments.eventRegistrationSystemWithDesignPatterns.manager.RegistrationManager" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
	// manager instance uses adapter pattern internally
	RegistrationManager registrationManager = new RegistrationManager();
	
	@SuppressWarnings("unchecked")
	List<Registration> registrations = (List<Registration>) session.getAttribute("registrations");
	
	if (registrations != null) {
		String action = request.getParameter("action");
		int index = Integer.parseInt(request.getParameter("index"));
		
		if (action.equals("update")) {
			int participants = Integer.parseInt(request.getParameter("participants"));
			
			List<Registration> updatedRegistrations = registrationManager.updateRegistration(
				registrations, index, participants
			);
			
			// check if update returned null (means 0 participants warning)
			if (updatedRegistrations == null) {
%>
<!DOCTYPE html>
<html>
<head>
	<title>Warning</title>
</head>
<body>
				<script>
					alert("Warning: 0 participants entered. Registration not removed.");
					window.location = "<%= request.getContextPath() %>/jsp/registrationSummary.jsp";
				</script>
</body>
</html>
<%
				return;
			}
			
			registrations = updatedRegistrations;
		} else if (action.equals("cancel")) {
			registrations = registrationManager.cancelRegistration(registrations, index);
		}
		
		// store the updated registrations
		session.setAttribute("registrations", registrations);
	}
	
	// stay on the same page
	response.sendRedirect("%s/jsp/registrationSummary.jsp".formatted(request.getContextPath()));
%>
