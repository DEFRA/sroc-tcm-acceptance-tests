package uk.gov.defra;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AcceptanceTestDriver {

    private WebDriver driver;

    private DriverName driverName;
    private Boolean headless;

    private enum DriverName {
        chrome,
        gecko
    }

    public AcceptanceTestDriver(String selectedBrowser, Boolean headless) {
        this.headless = headless;
        determineDriverName(selectedBrowser);
        setDriverSystemProperty();
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initialiseDriver();
        }
        return driver;
    }

    private void determineDriverName(String selectedBrowser) {
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
        String filename = "drivers" + File.separatorChar + driverName.name() + "driver" + getDriverExtension();

        return currentDir.resolve(filename).toString();
    }

    private String getDriverExtension() {
        String extension = "";

        if (System.getProperty("os.name").startsWith("Windows")) {
            extension = ".exe";
        }

        return extension;
    }

    private void setDriverSystemProperty() {
        System.setProperty("webdriver." + driverName.name() + ".driver", getDriverPath());
    }

    private void initialiseDriver() {
        if (driverName == DriverName.chrome) {
            driver = new ChromeDriver(getChromeOptions());
        } else {
            driver = new FirefoxDriver(getFirefoxOptions());
        }
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(headless);

        return options;
    }

    private FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(headless);

        return options;
    }
}
