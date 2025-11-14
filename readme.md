# event registration system (with design patterns) 🎟
a web-based event management system that allows users to browse upcoming events, register for them, and manage their 
registrations. this project demonstrates both **servlet-based** and **jsp-based** implementations of the same 
functionality, now enhanced with **design pattern implementations**. this was made as part of my **soen 387 assignment 2**.

## features 👾
- **dual implementation approach:** complete functionality available in both servlet and jsp versions.
- **event browsing:** view a list of upcoming events with details (date, location, description, pricing).
- **registration management:** register for events with customizable participant count (1-5 participants).
- **session-based cart system:** maintain registration state across multiple pages using http sessions.
- **design patterns integration:** implemented creational and structural patterns for enhanced maintainability and flexibility.

## design patterns implemented 🎨

### creational patterns
- **singleton:** `EventData` - ensures single instance of event data source across the application.
- **factory:** `RegistrationFactory` - validates and creates registration objects with proper participant count validation (1-5 participants).

### structural patterns
- **adapter:** `EventDataAdapter` - provides flexible data access interface, making it easy to switch between different data sources.
- **decorator:** `EventDecorator` - enhances event display with:
  - `FeaturedEventDecorator` - adds "[FEATURED]" badge and special styling to event 1.
  - `DiscountEventDecorator` - applies 20% discount to event 2 with adjusted pricing display.

## challenges faced 💢
- **pattern integration:** implementing design patterns without over-engineering the existing codebase.
- **decorator pattern:** ensuring decorators properly wrap events while maintaining original functionality.

## what I learned 💭
- **design patterns in practice:**
  - **singleton pattern** for managing shared resources
  - **factory pattern** for object creation with validation
  - **adapter pattern** for flexible data source integration
  - **decorator pattern** for runtime feature enhancement

## running the project 🏁
to get the project up and running on your local machine, follow these steps:

### prerequisites
- **ensure [jdk](https://www.oracle.com/java/technologies/downloads/) is installed:** this project uses jdk v24.
- **application server:** apache tomcat 10.x or higher (supports jakarta ee 10).

### setup instructions
#### 1. clone the repository
```bash
git clone https://github.com/barbaraeguche/event-registration-system-with-design-patterns.git
```

#### 2. navigate to the project directory
```bash
cd event-registration-system-with-design-patterns
```

#### 3. configure your ide (intellij idea example)
**a. set up tomcat:**
- download and install [apache tomcat 10.x](https://tomcat.apache.org/download-10.cgi)
- go to **intellij idea → settings → build, execution, deployment → application servers** (on mac)
- click **+** and add your tomcat installation directory

**b. build the project with maven:**
- open the terminal in intellij (or use your system terminal)
- run the following command to clean and build the project:
```bash
mvn clean package
```
- this will create the `target` directory with the war file needed for deployment

**c. configure the run configuration:**
- click **run → edit configurations**
- click **+** and select **tomcat server → local**
- name it (e.g., "event-registration-system-with-design-patterns")
- in the **server** tab:
    - set **http port** to `8080` (or your preferred port)
    - set the **url** to `http://localhost:8080/event-registration-system-with-design-patterns/`
- in the **deployment** tab:
    - click **+** and select **artifact**
    - select `event-registration-system-with-design-patterns:war exploded`
    - set **application context** to `/event-registration-system-with-design-patterns`
- click **apply** and **ok**

#### 4. run the application
- click the **run** button (green play icon) or press **⌃R** (control+r on mac)
- wait for tomcat to start and deploy the application
- your browser should automatically open to: [http://localhost:8080/event-registration-system-with-design-patterns/](http://localhost:8080/event-registration-system-with-design-patterns/)

## project structure 📁
```
src/main/java/com/assignments/eventRegistrationSystemWithDesignPatterns/
├── model/
│   ├── Event.java                    # core event model
│   └── Registration.java             # registration model
├── data/
│   └── EventData.java                # singleton: event data source
├── factory/
│   └── RegistrationFactory.java      # factory: validated registration creation
├── adapter/
│   ├── EventDataSource.java          # adapter interface
│   └── EventDataAdapter.java         # adapter implementation
├── decorator/
│   ├── EventDecorator.java           # base decorator
│   ├── FeaturedEventDecorator.java   # featured event decorator
│   └── DiscountEventDecorator.java   # discount event decorator
├── manager/
│   └── RegistrationManager.java      # business logic layer
└── servlets/
    ├── EventListingServlet.java
    ├── ConfirmationServlet.java
    └── RegistrationSummaryServlet.java
```

## tech stack ✨
- **java 24**
- **jakarta servlet 6.0** (jakarta ee 10)
- **jsp 3.1**
- **apache tomcat 10.x**
- **html5 & css3**
- **http sessions**
- **design patterns** (singleton, factory, adapter, decorator)