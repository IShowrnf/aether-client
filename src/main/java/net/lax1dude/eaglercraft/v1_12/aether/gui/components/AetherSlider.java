package net.lax1dude.eaglercraft.v1_12.aether.gui.components;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.FontRenderer;
import net.lax1dude.eaglercraft.v1_12.aether.theme.AetherTheme;

public class AetherSlider extends AetherComponent {
    private FontRenderer fr; private String label; private float min, max, value; private boolean dragging = false;
    public AetherSlider(FontRenderer fr, String label, float min, float max, float defaultValue) { this.fr = fr; this.label = label; this.min = min; this.max = max; this.value = Math.max(min, Math.min(max, defaultValue)); this.width = 160; this.height = 18; }
    public float getValue() { return value; } public void setValue(float v) { this.value = Math.max(min, Math.min(max, v)); }
    @Override public void render(int mouseX, int mouseY, float partialTicks) {
        int labelX = x + 4; int labelY = y + 3; fr.drawString(label, labelX, labelY, AetherTheme.TEXT, false);
        int trackX = x + 4; int trackY = y + 10; int trackW = width - 8; int trackH = 4;
        Gui.drawRect(trackX, trackY, trackX + trackW, trackY + trackH, AetherTheme.SECONDARY_PANEL);
        float t = (value - min) / (max - min); int filledW = (int)(trackW * t); Gui.drawRect(trackX, trackY, trackX + filledW, trackY + trackH, AetherTheme.ACCENT_VIOLET);
        int handleX = trackX + filledW - 4; int handleY = trackY - 3; Gui.drawRect(handleX, handleY, handleX + 8, handleY + 10, AetherTheme.ACCENT_VIOLET);
        String valStr = String.format("%.2f", value); fr.drawString(valStr, x + width - fr.getStringWidth(valStr) - 6, labelY, AetherTheme.TEXT, false);
        if (containsPoint(mouseX, mouseY)) Gui.drawRect(x, y, x + width, y + height, 0x10000000);
    }
    @Override public void mouseClicked(int mouseX, int mouseY, int button) { if (containsPoint(mouseX, mouseY)) { dragging = true; updateValueFromMouse(mouseX); } }
    @Override public void mouseReleased(int mouseX, int mouseY, int button) { dragging = false; }
    @Override public void mouseDragged(int mouseX, int mouseY, int button, long timeSinceClick) { if (dragging) updateValueFromMouse(mouseX); }
    private void updateValueFromMouse(int mouseX) { int trackX = x + 4; int trackW = width - 8; float t = (float)(mouseX - trackX) / (float)trackW; t = Math.max(0f, Math.min(1f, t)); this.value = min + t * (max - min); }
}
