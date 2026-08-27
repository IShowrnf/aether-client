package net.lax1dude.eaglercraft.v1_12.aether.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.FontRenderer;
import net.lax1dude.eaglercraft.v1_12.aether.theme.AetherTheme;

public class AetherRenderUtils {
    private AetherRenderUtils() {}

    public static void drawRoundedRect(int x, int y, int w, int h, int radius, int color) {
        Gui.drawRect(x + radius, y, x + w - radius, y + h, color);
        Gui.drawRect(x, y + radius, x + w, y + h - radius, color);
        Gui.drawRect(x, y, x + radius, y + radius, color);
        Gui.drawRect(x + w - radius, y, x + w, y + radius, color);
        Gui.drawRect(x, y + h - radius, x + radius, y + h, color);
        Gui.drawRect(x + w - radius, y + h - radius, x + w, y + h, color);
    }

    public static void enableScissor(int x, int y, int w, int h) {
        try {
            Class<?> gl11 = Class.forName("org.lwjgl.opengl.GL11");
            net.minecraft.client.gui.ScaledResolution sr = new net.minecraft.client.gui.ScaledResolution(net.minecraft.client.Minecraft.getMinecraft());
            int scale = sr.getScaleFactor();
            int fy = sr.getScaledHeight() - (y + h);
            int rx = x * scale; int ry = fy * scale; int rw = w * scale; int rh = h * scale;
            java.lang.reflect.Method mEnable = gl11.getMethod("glEnable", int.class);
            java.lang.reflect.Field fScissor = gl11.getField("GL_SCISSOR_TEST");
            mEnable.invoke(null, fScissor.getInt(null));
            java.lang.reflect.Method mScissor = gl11.getMethod("glScissor", int.class, int.class, int.class, int.class);
            mScissor.invoke(null, rx, ry, rw, rh);
        } catch (Throwable t) {}
    }

    public static void disableScissor() {
        try {
            Class<?> gl11 = Class.forName("org.lwjgl.opengl.GL11");
            java.lang.reflect.Method mDisable = gl11.getMethod("glDisable", int.class);
            java.lang.reflect.Field fScissor = gl11.getField("GL_SCISSOR_TEST");
            mDisable.invoke(null, fScissor.getInt(null));
        } catch (Throwable t) {}
    }
}
