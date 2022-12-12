package stepsdefinitions;

import static org.junit.jupiter.api.Assertions.*;
import pageobjects.HomePage;
import utilities.TestContext;
import io.cucumber.java.en.Given;

public class HomeSteps {

    HomePage homePage;
    TestContext testContext;

    public HomeSteps(TestContext context) {
        testContext = context;
        homePage = testContext.getPageObjectManager().getHomePage();
    }
    @Given("Home page without authorizations")
    public void homePageWithoutAuthorizations() {
        assertTrue(homePage.defaultHomePageIsDisplayed());
    }
}
