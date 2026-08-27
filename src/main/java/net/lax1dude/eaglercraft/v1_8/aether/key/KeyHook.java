package net.lax1dude.eaglercraft.v1_8.aether.key;

import net.minecraft.client.Minecraft;

/**
 * Key hook that polls the keyboard for the configured GUI key and opens the screen on press.
 *
 * To enable, call KeyHook.poll() from a central input handler (e.g., Minecraft's main loop) once per tick.
 */
public class KeyHook {
    private static boolean lastPressed = false;

    public static void poll() {
        try {
            String guiKeyName = net.lax1dude.eaglercraft.v1_8.aether.key.AetherKeybindManager.getGuiKey();
            int keyCode = getKeyIndex(guiKeyName);
            boolean pressed = isKeyDown(keyCode);
            if (pressed && !lastPressed) {
                // on key down
                Minecraft mc = Minecraft.getMinecraft();
                if (mc.currentScreen == null) {
                    mc.displayGuiScreen(new net.lax1dude.eaglercraft.v1_8.aether.gui.AetherScreen());
                }
            }
            lastPressed = pressed;
        } catch (Throwable t) {
            // swallow
        }
    }

    private static int getKeyIndex(String name) {
        try {
            Class<?> kb = Class.forName("org.lwjgl.input.Keyboard");
            java.lang.reflect.Method m = kb.getMethod("getKeyIndex", String.class);
            Object v = m.invoke(null, name);
            return ((Number)v).intValue();
        } catch (Throwable t) {
            // fallback mapping for common names
            if ("RSHIFT".equalsIgnoreCase(name) || "RIGHT_SHIFT".equalsIgnoreCase(name)) return 54; // common LWJGL index for RSHIFT
            return 42; // default LSHIFT
        }
    }

    private static boolean isKeyDown(int keyCode) {
        try {
            Class<?> kb = Class.forName("org.lwjgl.input.Keyboard");
            java.lang.reflect.Method m = kb.getMethod("isKeyDown", int.class);
            Object r = m.invoke(null, keyCode);
            return (Boolean) r;
        } catch (Throwable t) {
            // fallback: attempt to use Eaglercraft Keyboard wrapper
            try {
                Class<?> kb = Class.forName("net.lax1dude.eaglercraft.v1_8.Keyboard");
                java.lang.reflect.Method m = kb.getMethod("isKeyDown", int.class);
                Object r = m.invoke(null, keyCode);
                return (Boolean) r;
            } catch (Throwable ex) {
                return false;
            }
        }
    }
}
