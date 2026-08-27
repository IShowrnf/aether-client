package net.lax1dude.eaglercraft.v1_12.aether.gui.components;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.FontRenderer;
import net.lax1dude.eaglercraft.v1_12.aether.theme.AetherTheme;

public class AetherToggle extends AetherComponent {
    private String label;
    private boolean value;
    private FontRenderer fr;

    public AetherToggle(FontRenderer fr, String label, boolean defaultValue) {
        this.fr = fr; this.label = label; this.value = defaultValue; this.width = 140; this.height = 14;
    }

    public boolean getValue() { return value; }
    public void setValue(boolean v) { this.value = v; }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        int textX = x + 4; int textY = y + 3; fr.drawString(label, textX, textY, AetherTheme.TEXT, false);
        int cx = x + width - 10; int cy = y + 3; int color = value ? AetherTheme.ACCENT_VIOLET : AetherTheme.MUTED;
        Gui.drawRect(cx - 6, cy, cx + 6, cy + 10, color);
        if (containsPoint(mouseX, mouseY)) Gui.drawRect(x, y, x + width, y + height, 0x10000000);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) { if (containsPoint(mouseX, mouseY)) this.value = !this.value; }
    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {}
    @Override
    public void mouseDragged(int mouseX, int mouseY, int button, long timeSinceClick) {}
}
