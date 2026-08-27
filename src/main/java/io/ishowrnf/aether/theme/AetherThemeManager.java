package io.ishowrnf.aether.theme;

import java.util.HashMap;
import java.util.Map;

/**
 * Very small theme manager. Add theme presets and allow switching.
 */
public class AetherThemeManager {
    private static final Map<String, AetherTheme> themes = new HashMap<>();
    private static String current = "AETHER";

    static {
        // only default theme for now. Additional themes can be added as separate constants.
        themes.put("AETHER", new AetherTheme());
    }

    public static void setTheme(String name) {
        if (themes.containsKey(name)) current = name;
    }

    public static String getCurrentTheme() { return current; }
}
