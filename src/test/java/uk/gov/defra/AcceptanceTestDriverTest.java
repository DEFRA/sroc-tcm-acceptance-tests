package uk.gov.defra;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.restoreSystemProperties;
import static org.junit.jupiter.api.Assertions.*;

class AcceptanceTestDriverTest {

    @Test
    void Sets_webdriver_system_property_to_chromedriver_when_browser_is_chrome() throws Exception {
        restoreSystemProperties(() -> {
            new AcceptanceTestDriver("chrome", true);
            assertTrue(
                    System.getProperty("webdriver.chrome.driver").endsWith("chromedriver")
            );
        });
    }

    @Test
    void Sets_webdriver_system_property_to_geckodriver_when_browser_is_firefox() throws Exception {
        restoreSystemProperties(() -> {
            new AcceptanceTestDriver("firefox", true);
            assertTrue(
                    System.getProperty("webdriver.gecko.driver").endsWith("geckodriver")
            );
        });
    }

    @Test
    void Ignores_case_of_browser_value_when_initialised() throws Exception {
        restoreSystemProperties(() -> {
            new AcceptanceTestDriver("CHROME", true);
            assertTrue(
                    System.getProperty("webdriver.chrome.driver").endsWith("chromedriver")
            );
        });
    }

    @Test
    void Throws_IllegalArgumentException_when_browser_is_unrecognised() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AcceptanceTestDriver("safari", true)
        );
    }


    /**
     * Tests that .exe is added to the 'webdriver.[driver].driver' system property.
     *
     * Currently disabled because we found it was only passing if the tests were
     * ordered such that this was last. It seems setting the system property
     * to something else and then calling `Paths.get("");` breaks
     * `java.nio.file.Paths.get()` for subsequent calls. This is even with
     * SystemLambda resetting everything.
     *
     * Used for reference (but no solution) https://stackoverflow.com/a/37579227/6117745
     *
     * @throws Exception
     */
    @Disabled
    @Test
    void Adds_exe_to_driver_path_in_webdriver_system_property_when_os_is_windows() throws Exception {
        restoreSystemProperties(() -> {
            System.setProperty("os.name", "Windows 8.1");
            System.setProperty("os.version", "6.3");
            AcceptanceTestDriver subject = new AcceptanceTestDriver("chrome", true);
            assertTrue(
                    System.getProperty("webdriver.chrome.driver").endsWith(".exe")
            );
        });
    }
}
