# Team JavaBean (28) [![Build Status](https://travis-ci.com/CSCC01/Team28.svg?token=fq3Wk2j1spZMFDVV8KGd&branch=master)](https://travis-ci.com/CSCC01/Team28)

Welcome to Team 28's CSCC01 Group project.

A video of our demo can be found at: https://www.youtube.com/watch?v=_rxiDKTua7A&fbclid=IwAR1omUi16ZiyvdYSsbo0t4spZyHOb0KQ_qs4wKXj9NR80SrNjUVbpKP5mHc

Please watch in at least 720p for clarity.

## Application

### Structure
##### Source Code
The source code for the application is located in the `NewcomerReportServices` directory located in the project root.

##### Summary Reports
When generating a summary report through the application, it will be located at `SummaryReports/report.txt` in the project root.

**_Note:_** If a report already existing the folder, it will be overwritten when generating a new report.

##### Database
The applications database is located in `NewcomerReportServices/sqlite/db/`

### Compiling / Running
To compile the application, execute `mvn compile` within the `NewcomerReportServices` directory.

To run the application, run the `Main` class located in `NewcomerReportServices/src/main/java/ui`. Currently, this has been tested to work when running through Eclipse and IntelliJ.

You can also run our latest jar file `NewcomerReportServices-1.0-SNAPSHOT.jar` inside of the `NewcomerReportServices` directory using the command `java -jar NewcomerReportService-1.0-SNAPSHOT.jar` on Windows (and the corresponding command on your OS).

You can build your own jar file through running the command `mvn clean package` within the `NewcomerReportServices` directory. Your jar file will be located within the target folder.

**_Note:_** You must move the jar file to the NewcomerReportServices directory before you can execute the jar. This is because of needing the sqlite database.

**_System Requirements:_** To run the application, it is required that you have a JDK version of 10 of less.

**_Note:_** There is currently a JavaFx bug that prevents the application from running properly on the latest MAC OS, the fix is in-progress by the team working on Javafx.

### Testing
To run the unit tests, execute `mvn test` within the `NewcomerReportServices` directory.

## Deliverables
### Reports
All deliverable reports can be found within the `Reports` folder at the root of the project.

### Product Backlog
The `Product Backlog` directory contains versioned copies of the projects personas and user stories.  

### Project Backlog
The `Project Backlog` directory contains meeting minutes and our sprint plans.
