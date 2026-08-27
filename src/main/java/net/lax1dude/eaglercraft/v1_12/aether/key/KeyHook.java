package net.lax1dude.eaglercraft.v1_12.aether.key;

import net.lax1dude.eaglercraft.v1_12.aether.config.AetherConfigManager;
import net.minecraft.client.Minecraft;

public class KeyHook {
    private static boolean lastPressed = false;
    public static void poll() {
        try {
            String guiKeyName = net.lax1dude.eaglercraft.v1_12.aether.key.AetherKeybindManager.getGuiKey();
            int keyCode = getKeyIndex(guiKeyName);
            boolean pressed = isKeyDown(keyCode);
            if (pressed && !lastPressed) {
                Minecraft mc = Minecraft.getMinecraft();
                if (mc.currentScreen == null) mc.displayGuiScreen(new net.lax1dude.eaglercraft.v1_12.aether.gui.AetherScreen());
            }
            lastPressed = pressed;
        } catch (Throwable t) {}
    }
    private static int getKeyIndex(String name) {
        try { return org.lwjgl.input.Keyboard.getKeyIndex(name); } catch (Throwable t) { if ("RSHIFT".equalsIgnoreCase(name)) return 54; return 42; }
    }
    private static boolean isKeyDown(int keyCode) { try { return org.lwjgl.input.Keyboard.isKeyDown(keyCode); } catch (Throwable t) { return false; } }
}
