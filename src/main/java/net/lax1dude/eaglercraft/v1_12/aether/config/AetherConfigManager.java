package net.lax1dude.eaglercraft.v1_12.aether.config;

import java.io.*;
import java.util.Properties;

/**
 * Simple properties-backed config for Eaglercraft 1.12 port.
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
