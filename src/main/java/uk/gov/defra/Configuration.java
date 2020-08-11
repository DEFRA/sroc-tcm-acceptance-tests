package uk.gov.defra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

public class Configuration {

    private String browser;
    private Boolean headless;
    private String rootUrl;
    private String test;

    private final String password;

    public Configuration() {
        this.password = System.getenv("TCM_TEST_PASSWORD");
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Boolean getHeadless() {
        return headless;
    }

    public void setHeadless(Boolean headless) {
        this.headless = headless;
    }

    public String getRootUrl() {
        return rootUrl;
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getPassword() {
        return password;
    }

    public void validate() {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("The env var TCM_TEST_PASSWORD has not been set.");
        }
        if (browser == null || browser.isEmpty()) {
            throw new IllegalArgumentException("Browser has not been set in your config file.");
        }
        if (rootUrl == null || rootUrl.isEmpty()) {
            throw new IllegalArgumentException("Root url has not been set in your config file.");
        }
    }

    public static Configuration readConfigurationFile(String source) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File configFile = new File(source);

        return mapper.readValue(configFile, Configuration.class);
    }
}
