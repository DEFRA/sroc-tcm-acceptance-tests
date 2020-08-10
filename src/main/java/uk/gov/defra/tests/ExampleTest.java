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
            WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
            searchField.sendKeys("defra");
            searchField.submit();

            WebElement firstResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"r\"]/a/h3")));

            System.out.println(("First result: " + firstResult.getText()));
        } finally {
            driver.quit();
        }
    }
}
