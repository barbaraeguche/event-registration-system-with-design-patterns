package com.assignments.eventRegistrationSystemWithDesignPatterns;

import com.assignments.eventRegistrationSystemWithDesignPatterns.manager.RegistrationManager;
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
 * servlet that displays registration summary and handles updates/cancellations.
 * it demonstrates the usage of multiple design patterns:
 * - singleton: EventData accessed through getInstance()
 * - factory: registration updates validated through RegistrationFactory
 * - adapter: EventDataAdapter adapts EventData to EventDataSource interface
 */
@WebServlet("/servlet/registrations")
public class RegistrationSummaryServlet extends HttpServlet {
	
	// member variable
	private final RegistrationManager registrationManager = new RegistrationManager();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		List<Registration> registrations = (List<Registration>) session.getAttribute("registrations");
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Registration Summary</title>");
		out.println("<link rel='stylesheet' href='" + request.getContextPath() + "/css/style.css'>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<div class='container'>");
		out.println("<header>");
		out.println("<h2>My Registrations</h2>");
		out.println("<a href='" + request.getContextPath() + "/servlet/events' class='upcoming-events'>Continue Browsing</a>");
		out.println("</header>");
		
		if (registrations == null || registrations.isEmpty()) {
			out.println("<p class='no-registrations'>You have no registrations yet.</p>");
		} else {
			out.println("<table class='registration-table'>");
			out.println("<thead>");
			out.println("<tr>");
			out.println("<th>Event</th>");
			out.println("<th>Date / Time</th>");
			out.println("<th>Participants</th>");
			out.println("<th>Price / participant</th>");
			out.println("<th>Total</th>");
			out.println("<th>Actions</th>");
			out.println("</tr>");
			out.println("</thead>");
			out.println("<tbody>");
			
			for (int i = 0; i < registrations.size(); i++) {
				Registration reg = registrations.get(i);
				
				out.println("<tr>");
				out.println("<td>" + reg.getEvent().getName() + "</td>");
				out.println("<td>" + reg.getEvent().getDateTime() + "</td>");
				
				out.println("<td>");
				out.println("<form method='post' style='display:inline;' action='" + request.getContextPath() + "/servlet/registrations'>");
				out.println("<input type='hidden' name='action' value='update'>");
				out.println("<input type='hidden' name='index' value='" + i + "'>");
				out.println("<input type='number' min='0' max='5' name='participants' class='participant-input' value='" + reg.getNumOfParticipants() + "'>");
				out.println("<button type='submit' class='btn-update'>Update</button>");
				out.println("</form>");
				out.println("</td>");
				
				out.println("<td>$" + reg.getEvent().getPricePerParticipant() + "</td>");
				out.println("<td>$" + reg.getTotalCost() + "</td>");
				
				out.println("<td>");
				out.println("<form method='post' style='display:inline;' action='" + request.getContextPath() + "/servlet/registrations'>");
				out.println("<input type='hidden' name='action' value='cancel'>");
				out.println("<input type='hidden' name='index' value='" + i + "'>");
				out.println("<button type='submit' class='btn-cancel'>Cancel</button>");
				out.println("</form>");
				out.println("</td>");
				
				out.println("</tr>");
			}
			
			out.println("</tbody>");
			out.println("</table>");
			
			out.println("<div class='total-cost'>");
			out.println("<p><strong>Total Cost: $" + registrationManager.calculateTotalCost(registrations) + "</strong></p>");
			out.println("</div>");
			
			out.println("<div class='action-buttons'>");
			out.println("<a href='" + request.getContextPath() + "/servlet/events' class='upcoming-events'>Continue Browsing</a>");
			out.println("<form method='post' action='" + request.getContextPath() + "/servlet/confirmation' style='display:inline;'>");
			out.println("<button type='submit' class='btn-confirm'>Confirm Registration</button>");
			out.println("</form>");
			out.println("</div>");
		}
		
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
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
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					
					out.println("<script>alert('Warning: 0 participants entered. Registration not removed.');");
					out.println("window.location='" + request.getContextPath() + "/servlet/registrations';</script>");
					return;
				}
				
				registrations = updatedRegistrations;
			} else if (action.equals("cancel")) {
				registrations = registrationManager.cancelRegistration(registrations, index);
			}
			
			session.setAttribute("registrations", registrations);
		}
		
		response.sendRedirect(request.getContextPath() + "/servlet/registrations");
	}
}
