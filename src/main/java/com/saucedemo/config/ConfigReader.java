package com.saucedemo.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigReader {
    private static Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "/src/test/resources/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Could not load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
