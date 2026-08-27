package net.lax1dude.eaglercraft.v1_12.aether.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.FontRenderer;
import net.lax1dude.eaglercraft.v1_12.aether.theme.AetherTheme;

import java.util.ArrayList;

/**
 * Panel container (simplified) for 1.12 port
 */
public class AetherPanel {
    public int x, y, width, height;
    private String title;
    private int scroll = 0;
    private int contentHeight = 0;
    private final java.util.List<net.lax1dude.eaglercraft.v1_12.aether.gui.components.AetherComponent> children = new ArrayList<>();
    private final FontRenderer fr;

    public AetherPanel(FontRenderer fr, String title, int x, int y, int w, int h) {
        this.fr = fr; this.title = title; this.x = x; this.y = y; this.width = w; this.height = h;
    }

    public void add(net.lax1dude.eaglercraft.v1_12.aether.gui.components.AetherComponent c) { children.add(c); }
    public java.util.List<net.lax1dude.eaglercraft.v1_12.aether.gui.components.AetherComponent> getChildren() { return children; }

    public String getTitle() { return title; }

    public void render(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, AetherTheme.PANEL);
        Gui.drawRect(x, y, x + width, y + 18, AetherTheme.SECONDARY_PANEL);
        fr.drawString(title, x + 6, y + 4, AetherTheme.TEXT, false);

        int cx = x + 6; int cy = y + 22 - scroll; contentHeight = 0;
        for (net.lax1dude.eaglercraft.v1_12.aether.gui.components.AetherComponent c : children) {
            c.setPosition(cx, cy);
            c.setSize(width - 12, c.height);
            AetherRenderUtils.enableScissor(x, y + 22, width, height - 28);
            if (cy + c.height >= y + 22 && cy <= y + height - 8) c.render(mouseX, mouseY, partialTicks);
            AetherRenderUtils.disableScissor();
            cy += c.height + 4; contentHeight = cy - (y + 22);
        }

        Gui.drawRect(x, y, x + 1, y + height, AetherTheme.BORDER);
        Gui.drawRect(x + width - 1, y, x + width, y + height, AetherTheme.BORDER);
        Gui.drawRect(x, y + height - 1, x + width, y + height, AetherTheme.BORDER);
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        for (net.lax1dude.eaglercraft.v1_12.aether.gui.components.AetherComponent c : children) {
            if (c.containsPoint(mouseX, mouseY)) { c.mouseClicked(mouseX, mouseY, button); c.setFocused(true); }
            else c.setFocused(false);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int button) { for (net.lax1dude.eaglercraft.v1_12.aether.gui.components.AetherComponent c : children) c.mouseReleased(mouseX, mouseY, button); }
    public void mouseDragged(int mouseX, int mouseY, int button, long time) { for (net.lax1dude.eaglercraft.v1_12.aether.gui.components.AetherComponent c : children) c.mouseDragged(mouseX, mouseY, button, time); }
    public void handleMouseScroll(int delta) { scroll -= delta * 8; if (scroll < 0) scroll = 0; int max = Math.max(0, contentHeight - (height - 32)); if (scroll > max) scroll = max; }
}
