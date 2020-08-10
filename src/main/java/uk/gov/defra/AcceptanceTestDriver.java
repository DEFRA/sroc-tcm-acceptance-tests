package uk.gov.defra;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AcceptanceTestDriver {

    private WebDriver driver;

    private DriverName driverName;

    public enum DriverName {
        chrome,
        gecko
    }

    public AcceptanceTestDriver(String selectedBrowser) {
        determineDriverName(selectedBrowser);
        setDriverSystemProperty();
    }

    public DriverName getDriverName() {
        return driverName;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initialiseDriver();
        }
        return driver;
    }

    private void determineDriverName(String selectedBrowser) throws IllegalArgumentException {
        switch (selectedBrowser.toLowerCase()) {
            case "chrome":
                this.driverName = DriverName.chrome;
                break;
            case "firefox":
                this.driverName = DriverName.gecko;
                break;
            default:
                throw new IllegalArgumentException("Selected browser " + selectedBrowser + " is not recognised.");
        }
    }

    private String getDriverPath() {
        Path currentRelativePath = Paths.get("");
        Path currentDir = currentRelativePath.toAbsolutePath();
        String filename = "drivers" + File.separatorChar + driverName.name() + "driver" + driverExtension();

        return currentDir.resolve(filename).toString();
    }

    private void setDriverSystemProperty() {
        System.setProperty("webdriver." + driverName.name() + ".driver", getDriverPath());
    }

    private void initialiseDriver() {
        switch (driverName) {
            case chrome:
                driver = new ChromeDriver();
                break;
            case gecko:
                driver = new FirefoxDriver();
                break;
        }
    }

    private String driverExtension() {
        String extension = "";

        if (System.getProperty("os.name").startsWith("Windows")) {
            extension = ".exe";
        }

        return extension;
    }
}
