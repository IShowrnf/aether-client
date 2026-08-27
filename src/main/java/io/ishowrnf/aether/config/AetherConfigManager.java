package io.ishowrnf.aether.config;

import java.io.*;
import java.util.Properties;

/**
 * AetherConfigManager — simple properties-backed config stub.
 *
 * This is intentionally lightweight: it avoids external JSON libraries and
 * uses java.util.Properties to persist basic GUI settings. Replace with
 * a JSON implementation if preferred.
 */
public class AetherConfigManager {
    private static final File CONFIG_FILE = new File("aether-config.properties");
    private static final Properties props = new Properties();

    static {
        load();
    }

    public static synchronized void load() {
        if (!CONFIG_FILE.exists()) return;
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void save() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            props.store(fos, "Aether GUI Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void set(String key, String value) {
        props.setProperty(key, value);
        save();
    }

    public static synchronized String get(String key, String def) {
        return props.getProperty(key, def);
    }
}
