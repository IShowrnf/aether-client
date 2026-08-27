package net.lax1dude.eaglercraft.v1_8.aether.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.FontRenderer;
import net.lax1dude.eaglercraft.v1_8.aether.theme.AetherTheme;

/**
 * Panel container that can be dragged, resized, and scrolled.
 * Contains AetherComponents as children.
 */
public class AetherPanel {
    public int x, y, width, height;
    private String title;
    private boolean dragging = false;
    private int dragOffsetX, dragOffsetY;
    private boolean resizing = false;
    private int minW = 220, minH = 120;
    private int scroll = 0;
    private int contentHeight = 0;

    private final java.util.List<net.lax1dude.eaglercraft.v1_8.aether.gui.components.AetherComponent> children = new java.util.ArrayList<>();
    private final FontRenderer fr;

    public AetherPanel(FontRenderer fr, String title, int x, int y, int w, int h) {
        this.fr = fr;
        this.title = title;
        this.x = x; this.y = y; this.width = Math.max(w, minW); this.height = Math.max(h, minH);
    }

    public void add(net.lax1dude.eaglercraft.v1_8.aether.gui.components.AetherComponent c) {
        children.add(c);
    }

    public java.util.List<net.lax1dude.eaglercraft.v1_8.aether.gui.components.AetherComponent> getChildren() { return children; }

    public void render(int mouseX, int mouseY, float partialTicks) {
        // Panel background
        Gui.drawRect(x, y, x + width, y + height, AetherTheme.PANEL);
        // Title bar
        Gui.drawRect(x, y, x + width, y + 18, AetherTheme.SECONDARY_PANEL);
        fr.drawString(title, x + 6, y + 4, AetherTheme.TEXT, false);

        // Compute layout for children
        int cx = x + 6;
        int cy = y + 22 - scroll;
        contentHeight = 0;
        for (net.lax1dude.eaglercraft.v1_8.aether.gui.components.AetherComponent c : children) {
            c.setPosition(cx, cy);
            c.setSize(width - 12, c.height);
            // Use scissor to clip children to panel content area
            AetherRenderUtils.enableScissor(x, y + 22, width, height - 28);
            if (cy + c.height >= y + 22 && cy <= y + height - 8) {
                c.render(mouseX, mouseY, partialTicks);
            }
            AetherRenderUtils.disableScissor();
            cy += c.height + 4;
            contentHeight = cy - (y + 22);
        }

        // Panel border
        Gui.drawRect(x, y, x + 1, y + height, AetherTheme.BORDER);
        Gui.drawRect(x + width - 1, y, x + width, y + height, AetherTheme.BORDER);
        Gui.drawRect(x, y + height - 1, x + width, y + height, AetherTheme.BORDER);

        // Resize handle (simple corner)
        Gui.drawRect(x + width - 10, y + height - 10, x + width, y + height, AetherTheme.BORDER);
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        // Title drag
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 18) {
            dragging = true;
            dragOffsetX = mouseX - x; dragOffsetY = mouseY - y;
            return;
        }
        // Resize
        if (mouseX >= x + width - 10 && mouseX <= x + width && mouseY >= y + height - 10 && mouseY <= y + height) {
            resizing = true;
            return;
        }

        // Dispatch to children
        for (net.lax1dude.eaglercraft.v1_8.aether.gui.components.AetherComponent c : children) {
            if (c.containsPoint(mouseX, mouseY)) {
                c.mouseClicked(mouseX, mouseY, button);
                c.setFocused(true);
            } else {
                c.setFocused(false);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int button) {
        dragging = false; resizing = false;
        for (net.lax1dude.eaglercraft.v1_8.aether.gui.components.AetherComponent c : children) c.mouseReleased(mouseX, mouseY, button);
    }

    public void mouseDragged(int mouseX, int mouseY, int button, long time) {
        if (dragging) {
            x = mouseX - dragOffsetX; y = mouseY - dragOffsetY;
        } else if (resizing) {
            width = Math.max(minW, mouseX - x);
            height = Math.max(minH, mouseY - y);
        } else {
            for (net.lax1dude.eaglercraft.v1_8.aether.gui.components.AetherComponent c : children) c.mouseDragged(mouseX, mouseY, button, time);
        }
    }

    public void handleMouseScroll(int delta) {
        // delta: positive = up, negative = down
        scroll -= delta * 8; // sensitivity
        if (scroll < 0) scroll = 0;
        int maxScroll = Math.max(0, contentHeight - (height - 32));
        if (scroll > maxScroll) scroll = maxScroll;
    }
}
