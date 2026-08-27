package net.lax1dude.eaglercraft.v1_12.aether.gui.components;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.lax1dude.eaglercraft.v1_12.aether.theme.AetherTheme;

import java.util.Arrays;
import java.util.List;

/**
 * Minimal color picker with presets + alpha for 1.12
 */
public class AetherColorPicker extends AetherComponent {
    private FontRenderer fr;
    private int color = AetherTheme.ACCENT_VIOLET;
    private boolean open = false;

    private static final List<Integer> PRESETS = Arrays.asList(
        AetherTheme.ACCENT_VIOLET, AetherTheme.ACCENT_CYAN, 0xFF3B82F6, 0xFFEF4444, 0xFF10B981, 0xFFFF7A00, 0xFFFFFFFF
    );

    public AetherColorPicker(FontRenderer fr, int defaultColor) {
        this.fr = fr; this.color = defaultColor; this.width = 160; this.height = 18;
    }

    public int getColor() { return color; }
    public void setColor(int c) { this.color = c; }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, AetherTheme.SECONDARY_PANEL);
        fr.drawString(String.format("#%06X", (color & 0xFFFFFF)), x + 6, y + 4, AetherTheme.TEXT, false);
        Gui.drawRect(x + width - 22, y + 3, x + width - 4, y + height - 3, color);
        if (open) {
            int boxY = y + height + 4; int sw = 20; int gap = 6; int cx = x + 6;
            for (int i = 0; i < PRESETS.size(); ++i) {
                int sx = cx + i * (sw + gap);
                Gui.drawRect(sx, boxY, sx + sw, boxY + sw, PRESETS.get(i));
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (!open && containsPoint(mouseX, mouseY)) { open = true; return; }
        if (open) {
            int boxY = y + height + 4; int sw = 20; int gap = 6; int cx = x + 6;
            for (int i = 0; i < PRESETS.size(); ++i) {
                int sx = cx + i * (sw + gap);
                if (mouseX >= sx && mouseX <= sx + sw && mouseY >= boxY && mouseY <= boxY + sw) {
                    color = PRESETS.get(i); open = false; return;
                }
            }
            open = false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {}
    @Override
    public void mouseDragged(int mouseX, int mouseY, int button, long timeSinceClick) {}
}
