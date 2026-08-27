package net.lax1dude.eaglercraft.v1_12.aether.key;

import net.lax1dude.eaglercraft.v1_12.aether.config.AetherConfigManager;

/**
 * Simple keybind manager for 1.12 port
 */
public class AetherKeybindManager {
    private static final String KEY_GUI = "key.gui"; private static final String DEF_GUI = "RSHIFT";
    public static String getGuiKey() { return AetherConfigManager.get(KEY_GUI, DEF_GUI); }
    public static void setGuiKey(String k) { AetherConfigManager.set(KEY_GUI, k); }
}
