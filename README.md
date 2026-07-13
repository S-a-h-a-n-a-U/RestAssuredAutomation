#Question

API Automation - Rest Assured  
Create a project in Intellij and test your API in the same: 
Create a booking and validate the success of the API 
Get the details of the booking created in step 1. Validate the JSON response and data. 
Update the same booking and validate the updation as done in step 1 and 2 
Delete the same booking and validate the deletion as well 
Note: Use variables to pass information from step 1 to 4. Make the flow generic and handle multiple iterations for run (create 10 bookings in one run). Read data from variables and files 
For above tasks use the following demo site: 
https://restful-booker.herokuapp.com/apidoc/index.html#api-Booking-GetBooking 



   
#Prerequisites

Before running this project, ensure the following are installed:

- Java JDK 24 (or compatible JDK)
- Apache Maven 3.9+
- Git
- IntelliJ IDEA (optional)

Verify installations:

```bash
java -version
mvn -version
git --version

## Clone the Repository

```bash
git clone https://github.com/S-a-h-a-n-a-U/RestAssuredAutomation.git
```

Navigate to the project:

```bash
cd RestAssuredAutomation




 Run the Tests

Execute all TestNG tests using Maven:

```bash
mvn clean test

 Alternative (IntelliJ)

1. Open IntelliJ IDEA.
2. Select **Open Project**.
3. Choose the cloned repository.
4. Wait for Maven dependencies to download.
5. Right-click `BookingTest.java` or `testng.xml`.
6. Click **Run**.
