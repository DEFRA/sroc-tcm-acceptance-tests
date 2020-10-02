package uk.gov.defra.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.gov.defra.Configuration;

public class ExampleTest extends BaseTest {

    public ExampleTest(Configuration config) {
        super(config);
    }

    public void run() {
        try {
            driver.navigate().to(rootUrl);

            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement moreInfoLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a")));

            moreInfoLink.click();
            WebElement ianaHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));

            System.out.println(("Page heading: " + ianaHeading.getText()));
        } finally {
            driver.quit();
        }
    }
}
