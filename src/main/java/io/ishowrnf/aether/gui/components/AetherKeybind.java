package io.ishowrnf.aether.gui.components;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.Minecraft;
import io.ishowrnf.aether.theme.AetherTheme;

/**
 * Simple keybind selector. When clicked it enters listening mode and the next
 * key press is stored. Requires parent to forward keyTyped events to the focused
 * component when appropriate.
 */
public class AetherKeybind extends AetherComponent {
    private FontRenderer fr;
    private String boundKey = "NONE";
    private boolean listening = false;

    public AetherKeybind(FontRenderer fr, String defaultKey) {
        this.fr = fr; this.boundKey = defaultKey; this.width = 160; this.height = 18;
    }

    public String getBoundKey() { return boundKey; }
    public void setBoundKey(String k) { this.boundKey = k; }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, AetherTheme.SECONDARY_PANEL);
        String display = listening ? "Press a key..." : boundKey;
        fr.drawString(display, x + 6, y + 4, AetherTheme.TEXT, false);
        if (containsPoint(mouseX, mouseY)) Gui.drawRect(x, y, x + width, y + height, 0x10000000);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (containsPoint(mouseX, mouseY)) {
            listening = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {}

    @Override
    public void mouseDragged(int mouseX, int mouseY, int button, long timeSinceClick) {}

    // Call this from AetherScreen.keyTyped when this component is focused
    public void onKeyTyped(char typedChar, int keyCode) {
        if (!listening) return;
        if (keyCode == 1) { // ESC cancels
            listening = false; return;
        }
        // Basic mapping: use keyCode as string. For nicer names, map common keys.
        String name = net.minecraft.client.settings.KeyBinding.getKeyName(keyCode);
        if (name == null) name = String.valueOf(keyCode);
        boundKey = name; listening = false;
    }
}
