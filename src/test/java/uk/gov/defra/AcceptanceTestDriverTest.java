package uk.gov.defra;

import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.restoreSystemProperties;
import static org.junit.jupiter.api.Assertions.*;

class AcceptanceTestDriverTest {

    @Test
    void DriverName_is_chrome_when_browser_is_chrome() {
        AcceptanceTestDriver subject = new AcceptanceTestDriver("chrome");

        assertEquals(
                AcceptanceTestDriver.DriverName.chrome,
                subject.getDriverName()
        );
    }

    @Test
    void DriverName_is_gecko_when_browser_is_firefox() {
        AcceptanceTestDriver subject = new AcceptanceTestDriver("firefox");

        assertEquals(
                AcceptanceTestDriver.DriverName.gecko,
                subject.getDriverName()
        );
    }

    @Test
    void Ignores_case_of_browser_value_when_initialised() {
        AcceptanceTestDriver subject = new AcceptanceTestDriver("CHROME");

        assertEquals(
                AcceptanceTestDriver.DriverName.chrome,
                subject.getDriverName()
        );
    }

    @Test
    void Throws_IllegalArgumentException_when_browser_is_unrecognised() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AcceptanceTestDriver("safari")
        );
    }

    @Test
    void Sets_webdriver_system_property_to_chromedriver_when_browser_is_chrome() throws Exception {
        restoreSystemProperties(() -> {
            AcceptanceTestDriver subject = new AcceptanceTestDriver("chrome");
            assertTrue(
                    System.getProperty("webdriver.chrome.driver").endsWith("chromedriver")
            );
        });
    }

    @Test
    void Sets_webdriver_system_property_to_geckodriver_when_browser_is_firefox() throws Exception {
        restoreSystemProperties(() -> {
            AcceptanceTestDriver subject = new AcceptanceTestDriver("firefox");
            assertTrue(
                    System.getProperty("webdriver.gecko.driver").endsWith("geckodriver")
            );
        });
    }

    @Test
    void Adds_exe_to_driver_path_in_webdriver_system_property_when_os_is_windows() throws Exception {
        restoreSystemProperties(() -> {
            System.setProperty("os.name", "Windows 10");
            AcceptanceTestDriver subject = new AcceptanceTestDriver("chrome");
            assertTrue(
                    System.getProperty("webdriver.chrome.driver").endsWith(".exe")
            );
        });
    }
}
