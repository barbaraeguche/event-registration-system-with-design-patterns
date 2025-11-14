<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>Event Registration System (with Design Pattern)</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/home.css">
</head>
<body>
	<div class="welcome-container">
		<h1>🎉 Event Registration System</h1>
		<h2>Design Patterns Implementation</h2>
		<p>Choose your implementation version:</p>
		
		<div class="version-buttons">
			<a href="servlet/events" class="version-btn servlet">
				Servlet Version
			</a>
			<a href="jsp/eventListing.jsp" class="version-btn jsp">
				JSP Version
			</a>
		</div>
		
		<div class="note">
			<h3>About this Project</h3>
			<ul>
				<li>Two separate implementations: Servlets and JSP</li>
				<li>Event listing with registration functionality</li>
				<li>Registration management (update/cancel)</li>
				<li>Session-based cart system</li>
			</ul>
		</div>
		
		<div class="note patterns-note">
			<h3>🎨 Design Patterns Implemented</h3>
			<div class="patterns-grid">
				<div class="pattern-category">
					<h4>Creational Patterns</h4>
					<ul>
						<li><strong>Singleton:</strong> EventData - Single instance of event data source</li>
						<li><strong>Factory:</strong> RegistrationFactory - Validated registration creation</li>
					</ul>
				</div>
				<div class="pattern-category">
					<h4>Structural Patterns</h4>
					<ul>
						<li><strong>Adapter:</strong> EventDataAdapter - Flexible data access interface</li>
						<li><strong>Decorator:</strong> EventDecorator - Enhanced event display (Featured & Discounted events)</li>
					</ul>
				</div>
			</div>
			
			<div class="features-highlight">
				<p><strong>Enhanced Features:</strong></p>
				<ul>
					<li>Event 1: Featured with special badge</li>
					<li>Event 2: 20% discount with adjusted pricing</li>
					<li>Validated registrations (1-5 participants)</li>
					<li>Easy-to-switch data sources via Adapter</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
