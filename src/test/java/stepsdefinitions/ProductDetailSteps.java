package stepsdefinitions;

import enums.Context;
import pageobjects.ProductDetailPage;
import utilities.TestContext;
import io.cucumber.java.en.Then;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDetailSteps {

    TestContext testContext;
    ProductDetailPage productDetailPage;

    public ProductDetailSteps(TestContext context) {
        testContext = context;
        productDetailPage = testContext.getPageObjectManager().getProductDetailPage();
    }

    @Then("Product detail with {string} type is displayed")
    public void productDetailWithType(String type) {
        assertTrue(productDetailPage.defaultProductDetailPageIsDisplayed());

        switch (type) {
            case "non fisik":
                assertTrue(productDetailPage.nonFisikProductDetailDataIsDisplayed());
                break;
            case "fisik":
                assertTrue(productDetailPage.fisikProductDetailDataIsDisplayed());
                break;
        }

        String actualBreadcrumbName = productDetailPage.getAllBreadcrumbName(3).toLowerCase();
        String expectedBreadcrumbName = testContext.scenarioContext.getContext(Context.EXPECTED_DATA).toString().toLowerCase();
        assertTrue(actualBreadcrumbName.contains(expectedBreadcrumbName));

        assertTrue(productDetailPage.moreBreadcrumbIsDisplayed());
    }
}
