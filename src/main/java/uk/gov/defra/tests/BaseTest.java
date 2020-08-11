package uk.gov.defra.tests;

import org.openqa.selenium.WebDriver;
import uk.gov.defra.AcceptanceTestDriver;
import uk.gov.defra.Configuration;

public abstract class BaseTest implements TestInterface {

    protected final AcceptanceTestDriver testDriver;
    protected final String rootUrl;
    protected WebDriver driver;

    public BaseTest(Configuration config) {
        this.testDriver = new AcceptanceTestDriver(config.getBrowser(), config.getHeadless());
        this.rootUrl = config.getRootUrl();
        this.driver = testDriver.getDriver();
    }
}
