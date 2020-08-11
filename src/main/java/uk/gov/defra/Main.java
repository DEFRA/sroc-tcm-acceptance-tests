package uk.gov.defra;

import uk.gov.defra.tests.ExampleTest;
import uk.gov.defra.tests.MainTest;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            msg("Wrong number of arguments: " + args.length + ". Expected only 1.");
            System.exit(1);
        }

        Configuration config = processConfiguration(args[0]);
        if (config == null) System.exit(1);


        if (config.getTest().equals("example")) {
            runExampleTest(config);
        } else {
            runMainTest(config);
        }

        msg("All done. Have a nice day!");
    }

    private static Configuration processConfiguration(String source) {
        Configuration config = null;

        try {
            config = Configuration.readConfigurationFile(source);
            config.validate();
        } catch (Exception e) {
            msg("Issue with config: " + e.getMessage());
        }

        return config;
    }

    private static void runExampleTest(Configuration config) {
        ExampleTest test = new ExampleTest(config);
        test.run();
    }

    private static void runMainTest(Configuration config) {
        MainTest test = new MainTest(config);
        test.run();
    }

    private static void msg(String message) {
        System.out.println(message);
    }
}
