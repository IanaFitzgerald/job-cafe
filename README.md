# JobCafe Selenium UI Test Automation
This project is an automated UI test suite for the [JobCafe website](http://167.99.178.249:3000/) . It uses Selenium WebDriver with JUnit 5 to validate key functionalities such as navigation, form interactions, search features, and UI element visibility.
![mainpage](images/Screenshot1.png)

## 📌 Project Overview
The JobCafe Automated UI Tests project is designed to ensure that key components of the JobCafe website work as expected. The tests cover the following functionalities:

- **Logo and "Coming Soon" Image Visibility**: Verifies that the logo and "Coming Soon" image are displayed correctly.
- **Navigation**: Tests navigation to the "About Us" page.
- **Search Functionality**: Validates the search functionality for jobs by position, location, and company.
- **Form Interactions**: Tests form behaviors, including reset functionality and error messages for invalid inputs.
![mainpage](images/Screenshot2.png)

## 🛠️ Technologies Used

- **Selenium WebDriver**: For browser automation and interaction with web elements.
- **JUnit 5**: For test execution and assertions.
- **WebDriverManager**: For managing WebDriver binaries.
- **Maven**: For dependency management and project build.


## 🚀 How to Run Tests
### 1️⃣ Install Dependencies
Ensure **Maven** and **JDK 13+** are installed.
**Chrome Browser**: Installed on your machine.

### 2️⃣ Clone Repository
```sh
git clone https://github.com/YOUR_USERNAME/JobCafeTestProject.git
cd JobCafeTestProject

### 3️⃣ Run Tests
Execute the following command to run the tests:
mvn test




