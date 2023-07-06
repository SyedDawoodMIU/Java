package bank.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FactoryProvider {
    private static final String CONFIG_FILE = "src\\main\\resources\\config.properties";
    private static final String ENVIRONMENT_KEY = "environment";
    private static final String PRODUCTION_ENVIRONMENT = "production";

    public static AbstractFactory getFactory() {
        String environment = loadEnvironmentFromConfigFile();
        if (environment.equals(PRODUCTION_ENVIRONMENT)) {
            return new ProductionFactory();
        } else {
            return new TestFactory();
        }
    }

    private static String loadEnvironmentFromConfigFile() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
            return properties.getProperty(ENVIRONMENT_KEY);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}