package io.ishowrnf.aether.gui;

import io.ishowrnf.aether.theme.AetherTheme;

/**
 * Lightweight rendering utilities. These are intentionally minimal and
 * provide fallbacks for rounded rectangles and scissor/clipping helpers.
 *
 * For best results in Eaglercraft, the enableScissor/disableScissor methods
 * will attempt to use LWJGL GL11 if present; if not found they are no-ops.
 */
public final class AetherRenderUtils {
    private AetherRenderUtils() {}

    public static void drawRoundedRect(int x, int y, int w, int h, int radius, int color) {
        // center
        net.minecraft.client.gui.Gui.drawRect(x + radius, y, x + w - radius, y + h, color);
        net.minecraft.client.gui.Gui.drawRect(x, y + radius, x + w, y + h - radius, color);
        // corners as simple squares (approximation)
        net.minecraft.client.gui.Gui.drawRect(x, y, x + radius, y + radius, color);
        net.minecraft.client.gui.Gui.drawRect(x + w - radius, y, x + w, y + radius, color);
        net.minecraft.client.gui.Gui.drawRect(x, y + h - radius, x + radius, y + h, color);
        net.minecraft.client.gui.Gui.drawRect(x + w - radius, y + h - radius, x + w, y + h, color);
    }

    public static void drawRoundedRectOutline(int x, int y, int w, int h, int radius, int color) {
        // draw approximate outline using thin rects
        net.minecraft.client.gui.Gui.drawRect(x, y, x + w, y + 1, color);
        net.minecraft.client.gui.Gui.drawRect(x, y + h - 1, x + w, y + h, color);
        net.minecraft.client.gui.Gui.drawRect(x, y, x + 1, y + h, color);
        net.minecraft.client.gui.Gui.drawRect(x + w - 1, y, x + w, y + h, color);
    }

    public static void enableScissor(int x, int y, int w, int h) {
        try {
            Class<?> gl11 = Class.forName("org.lwjgl.opengl.GL11");
            // compute framebuffer scaled coordinates
            net.minecraft.client.gui.ScaledResolution sr = new net.minecraft.client.gui.ScaledResolution(net.minecraft.client.Minecraft.getMinecraft());
            int scale = sr.getScaleFactor();
            int fy = sr.getScaledHeight() - (y + h);
            int rx = x * scale;
            int ry = fy * scale;
            int rw = w * scale;
            int rh = h * scale;
            // glEnable(GL_SCISSOR_TEST)
            java.lang.reflect.Method mEnable = gl11.getMethod("glEnable", int.class);
            java.lang.reflect.Field fScissor = gl11.getField("GL_SCISSOR_TEST");
            mEnable.invoke(null, fScissor.getInt(null));
            // glScissor(x,y,w,h)
            java.lang.reflect.Method mScissor = gl11.getMethod("glScissor", int.class, int.class, int.class, int.class);
            mScissor.invoke(null, rx, ry, rw, rh);
        } catch (Throwable t) {
            // No-op fallback if LWJGL isn't available (web builds)
        }
    }

    public static void disableScissor() {
        try {
            Class<?> gl11 = Class.forName("org.lwjgl.opengl.GL11");
            java.lang.reflect.Method mDisable = gl11.getMethod("glDisable", int.class);
            java.lang.reflect.Field fScissor = gl11.getField("GL_SCISSOR_TEST");
            mDisable.invoke(null, fScissor.getInt(null));
        } catch (Throwable t) {
            // No-op fallback
        }
    }
}
