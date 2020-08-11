package uk.gov.defra;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    @Test
    void Password_is_taken_from_env_var() throws Exception {
        withEnvironmentVariable("TCM_TEST_PASSWORD", "jupiter").execute(() -> {
            Configuration subject = new Configuration();

            assertEquals(
                    "jupiter",
                    subject.getPassword()
            );
        });
    }

    @Test
    void Validate_throws_error_if_password_is_missing() {
        Configuration subject = new Configuration();

        assertThrows(
                IllegalArgumentException.class,
                subject::validate
        );
    }

    @Test
    void Validate_throws_error_if_browser_is_missing() throws Exception {
        withEnvironmentVariable("TCM_TEST_PASSWORD", "jupiter").execute(() -> {
            Configuration subject = new Configuration();

            assertThrows(
                    IllegalArgumentException.class,
                    subject::validate
            );
        });
    }

    @Test
    void Validate_throws_error_if_root_url_is_missing() throws Exception {
        withEnvironmentVariable("TCM_TEST_PASSWORD", "jupiter").execute(() -> {
            Configuration subject = new Configuration();
            subject.setBrowser("chrome");

            assertThrows(
                    IllegalArgumentException.class,
                    subject::validate
            );
        });
    }

    @Test
    void Validate_throws_no_error_if_everything_is_present() throws Exception {
        withEnvironmentVariable("TCM_TEST_PASSWORD", "jupiter").execute(() -> {
            Configuration subject = new Configuration();
            subject.setBrowser("chrome");
            subject.setRootUrl("https://github.com");

            assertDoesNotThrow(
                    subject::validate
            );
        });
    }

    @Test
    void We_can_instantiate_an_instance_based_on_a_config_file() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(
                Objects.requireNonNull(
                        classLoader.getResource("fixtures" + File.separatorChar + "valid.config.yml")
                ).getFile()
        );
        String absolutePath = file.getAbsolutePath();

        Configuration subject = Configuration.readConfigurationFile(absolutePath);

        assertEquals("chrome", subject.getBrowser());
        assertEquals(true, subject.getHeadless());
        assertEquals("https://example.com", subject.getRootUrl());
        assertEquals("main", subject.getTest());
    }

}