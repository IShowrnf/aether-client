package io.ishowrnf.aether.key;

import io.ishowrnf.aether.config.AetherConfigManager;

/**
 * Simple keybind manager that persists the GUI toggle key using the properties-based config.
 */
public class AetherKeybindManager {
    private static final String KEY_GUI = "key.gui";
    private static final String DEF_GUI = "RSHIFT";

    public static String getGuiKey() {
        return AetherConfigManager.get(KEY_GUI, DEF_GUI);
    }

    public static void setGuiKey(String k) {
        AetherConfigManager.set(KEY_GUI, k);
    }
}
