import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

/**
 This is an automated UI test class for the JobCafe website.
 It uses Selenium WebDriver with JUnit 5 to validate various functionalities
 such as navigation, form interactions, search features, and UI element visibility.
 The tests ensure that key components of the site work as expected,
 including verifying the presence of the logo, searching for jobs,
 navigating between pages, and testing form behaviors like reset functionality.
 The tests are executed in a predefined order to ensure dependencies are handled correctly.
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JobCafeTest {
    public static WebDriver driver;

    // URL of the main page
    public static final String MAIN_URL = "http://167.99.178.249:3000/";

    // XPaths for different elements
    public static final String LOGO = "//img[@src = '/img/site_bg.jpg']";
    public static final String JOB_PAGE = "//a[@href ='/job-page']";
    public static final String ABOUT_PAGE = "//a[@href ='/about']";
    public static final String SEARCH_BUTTON = "//button[@class ='search-butom']";
    public static final String POSITION_FIELD = "//input[@placeholder ='position']";
    public static final String LOCATION_FIELD = "//input[@placeholder ='location']";
    public static final String COMPANY_FIELD = "//input[@placeholder ='company']";
    public static final String RESET_BUTTON = "//*[contains(text(), 'reset')]";

    // Setup WebDriver before all tests
    @BeforeAll
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(MAIN_URL);
    }
    // Close WebDriver after all tests
    @AfterAll
    public static void closeDriver() {
        driver.quit();
    }

    // Test to verify that the logo and "Coming Soon" image are visible
    @Test
    @Order(1)
    public void logoFindTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src = '/img/site_bg.jpg']")));
        System.out.println("Logo is visible");
        WebElement comingSoonImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt = 'coming soon']")));
        System.out.println("Coming soon image is on the page");
    }

    // Test to verify navigation to the "About Us" page
    @Test
    public void openAboutUsPageTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ABOUT_PAGE))).click();
        WebElement logoAboutUsPage = driver.findElement(By.xpath("//*[contains(text(), 'CAFE')]"));
        Assertions.assertEquals("CAFE", logoAboutUsPage.getText(), "Logo text does not match the expected value.");

    }

    // Parameterized test to verify location search functionality
    @ParameterizedTest
    @ValueSource(strings = {"Toronto", "Tel-Aviv", "Chicago", "New-York"})
    public void jobPageLocationTest(String locationInput){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(JOB_PAGE))).click();
        WebElement locationField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOCATION_FIELD)));

        locationField.sendKeys(locationInput);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_BUTTON))).click();
        String locationValue = locationField.getAttribute("value");
        Assertions.assertEquals(locationInput, locationValue, "The city selected doesn't match the input.");
        locationField.clear();
    }

    // Parameterized test to verify position search functionality
    @ParameterizedTest
    @ValueSource(strings = {"QA", "Developer", "Project", "Manager"})
    public void jobPagePositionTest(String positionInput){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(JOB_PAGE))).click();
        WebElement positionField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(POSITION_FIELD)));

        positionField.sendKeys(positionInput);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_BUTTON))).click();
        String positionValue = positionField.getAttribute("value");
        Assertions.assertEquals(positionInput, positionValue, "The position selected doesn't match the input.");
        positionField.clear();

    }

    // Parameterized test to verify company search functionality
    @ParameterizedTest
    @ValueSource(strings = {"Apple", "Facebook", "Google"})
    public void jobPageCompanyTest(String companyInput) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(JOB_PAGE))).click();
        WebElement companyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COMPANY_FIELD)));

        companyField.sendKeys(companyInput);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_BUTTON))).click();
        String positionValue = companyField.getAttribute("value");
        Assertions.assertEquals(companyInput, positionValue, "The company  selected doesn't match the input.");
        companyField.clear();
    }

    // Test to verify search results with combined inputs
    @Test
    public void combinedTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(JOB_PAGE))).click();

        WebElement positionField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(POSITION_FIELD)));
        positionField.sendKeys("Manager");
        WebElement locationField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOCATION_FIELD)));
        locationField.sendKeys("USA");
        WebElement companyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COMPANY_FIELD)));
        companyField.sendKeys("Apple");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_BUTTON))).click();

        List<WebElement> results = driver.findElements(By.xpath("//*[contains(text(), 'found')]"));
        Assertions.assertTrue(results.size() > 0, "No job results found.");

        positionField.clear();
        locationField.clear();
        companyField.clear();
    }

    // Test to verify error message when no results are found with incorrect search inputs
    @Test
    public void incorrectlyFilledTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(JOB_PAGE))).click();

        WebElement positionField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(POSITION_FIELD)));
        positionField.sendKeys("manager");
        WebElement locationField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOCATION_FIELD)));
        locationField.sendKeys("Russia");
        WebElement companyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COMPANY_FIELD)));
        companyField.sendKeys("Fusiom");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_BUTTON))).click();

        WebElement noResultsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'No results found!')]")));
        String messageText = noResultsMessage.getText();
        Assertions.assertEquals("No results found!", messageText, "The error message is not as expected.");
        positionField.clear();
        locationField.clear();
        companyField.clear();
    }

    // This test verifies that the reset button clears the values in the Position, Location, and Company input fields when clicked
    @Test
    public void resetButtonTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(JOB_PAGE))).click();

        WebElement positionField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(POSITION_FIELD)));
        positionField.sendKeys("manager");
        WebElement locationField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOCATION_FIELD)));
        locationField.sendKeys("Russia");
        WebElement companyField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COMPANY_FIELD)));
        companyField.sendKeys("Fusiom");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(RESET_BUTTON))).click();

        Assertions.assertTrue(positionField.getAttribute("value").isEmpty(), "Position field is not empty");
        Assertions.assertTrue(locationField.getAttribute("value").isEmpty(), "Location field is not empty");
        Assertions.assertTrue(companyField.getAttribute("value").isEmpty(), "Company field is not empty");
        positionField.clear();
        locationField.clear();
        companyField.clear();
    }

}
