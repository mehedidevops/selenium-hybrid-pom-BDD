package stepsdefinitions;

import enums.Context;
import pageobjects.HomePage;
import pageobjects.ProductListPage;
import utilities.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;



public class SearchSteps {

    TestContext testContext;
    HomePage homePage;
    ProductListPage productListPage;

    public SearchSteps(TestContext context) {
        testContext = context;
        homePage = testContext.getPageObjectManager().getHomePage();
        productListPage = testContext.getPageObjectManager().getProductListPage();
    }

    @When("Input data with {string} in search bar")
    public void inputDataSearch(String data) {
        homePage.setSearchInput(data);
    }

    @Then("Search product link with {string} data is displayed")
    public void searchProductLinkWithDataIsDisplayed(String data) {
        String dataLowerCase = data.toLowerCase();
        String actualSearchProductLink = homePage.getSearchProductLink().toLowerCase();

        assertEquals("tampilkan pencarian dari " + dataLowerCase, actualSearchProductLink);
    }

    @When("Search product with {string} keyword")
    public void searchProductWithKeyword(String productName) {
        homePage.searchProductData(productName);
        testContext.scenarioContext.setContext(Context.EXPECTED_DATA, productName);
    }

    @Then("Product list is displayed")
    public void productListIsDisplayed() {
        assertTrue(productListPage.searchResultPageIsDisplayed());

        String actualResultTitle = productListPage.getResultTitle().toLowerCase();
        String expectedProductName = testContext.scenarioContext.getContext(Context.EXPECTED_DATA).toString().toLowerCase();

        assertEquals(expectedProductName, actualResultTitle);

        int totalProduct = Integer.parseInt(productListPage.getProductAmount());

        for (int i = 0; i < totalProduct; i++) {
            String actualProductTitle = productListPage.getAllProductTitle(i).toLowerCase();

            assertTrue(testContext.scenarioContext.isContains(Context.EXPECTED_DATA)); //Context assertion
            assertTrue(actualProductTitle.contains(expectedProductName)); //Common assertion
        }
    }

    @When("Select product in {int} list")
    public void selectProductInList(int list) {
        assertTrue(productListPage.searchResultPageIsDisplayed());
        productListPage.selectProductInList(list);
    }
}
