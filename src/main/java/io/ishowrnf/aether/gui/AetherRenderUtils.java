package io.ishowrnf.aether.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;

/**
 * Lightweight rendering utilities. These are intentionally minimal and
 * provide fallbacks for rounded rectangles and scissor/clipping helpers.
 *
 * Note: For best results in Eaglercraft, replace enableScissor/disableScissor
 * implementations with the engine's native scissor APIs.
 */
public final class AetherRenderUtils {
    private AetherRenderUtils() {}

    public static void drawRoundedRect(int x, int y, int w, int h, int radius, int color) {
        // center
        Gui.drawRect(x + radius, y, x + w - radius, y + h, color);
        Gui.drawRect(x, y + radius, x + w, y + h - radius, color);
        // corners as simple squares (approximation)
        Gui.drawRect(x, y, x + radius, y + radius, color);
        Gui.drawRect(x + w - radius, y, x + w, y + radius, color);
        Gui.drawRect(x, y + h - radius, x + radius, y + h, color);
        Gui.drawRect(x + w - radius, y + h - radius, x + w, y + h, color);
    }

    public static void enableScissor(int x, int y, int w, int h) {
        // Fallback: no-op. Replace with GL scissor calls in Eaglercraft when porting.
        // Example replacement (GL coordinates):
        // ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        // int scale = sr.getScaleFactor();
        // GL11.glEnable(GL11.GL_SCISSOR_TEST);
        // GL11.glScissor(x * scale, (sr.getScaledHeight() - (y + h)) * scale, w * scale, h * scale);
    }

    public static void disableScissor() {
        // no-op fallback
    }
}
