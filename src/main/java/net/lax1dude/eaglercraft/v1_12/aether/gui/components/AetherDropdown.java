package net.lax1dude.eaglercraft.v1_12.aether.gui.components;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.lax1dude.eaglercraft.v1_12.aether.theme.AetherTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple dropdown component for 1.12
 */
public class AetherDropdown extends AetherComponent {
    private FontRenderer fr;
    private List<String> options = new ArrayList<>();
    private int selectedIndex = 0;
    private boolean expanded = false;

    public AetherDropdown(FontRenderer fr, java.util.List<String> options, int defaultIndex) {
        this.fr = fr;
        this.options.addAll(options);
        this.selectedIndex = Math.max(0, Math.min(defaultIndex, this.options.size() - 1));
        this.width = 160; this.height = 18;
    }

    public String getSelected() { return options.get(selectedIndex); }
    public void setSelectedIndex(int i) { if (i >= 0 && i < options.size()) selectedIndex = i; }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(x, y, x + width, y + height, AetherTheme.SECONDARY_PANEL);
        fr.drawString(getSelected(), x + 6, y + 4, AetherTheme.TEXT, false);
        fr.drawString(expanded ? "▴" : "▾", x + width - 12, y + 4, AetherTheme.MUTED, false);
        if (expanded) {
            int listY = y + height + 2; int itemH = 16;
            for (int i = 0; i < options.size(); ++i) {
                int iy = listY + i * (itemH + 2);
                Gui.drawRect(x, iy, x + width, iy + itemH, AetherTheme.PANEL);
                fr.drawString(options.get(i), x + 6, iy + 3, AetherTheme.TEXT, false);
                if (i == selectedIndex) Gui.drawRect(x + width - 6, iy + 4, x + width - 2, iy + itemH - 4, AetherTheme.ACCENT_VIOLET);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (!expanded && containsPoint(mouseX, mouseY)) { expanded = true; return; }
        if (expanded) {
            int listY = y + height + 2; int itemH = 16;
            for (int i = 0; i < options.size(); ++i) {
                int iy = listY + i * (itemH + 2);
                if (mouseX >= x && mouseX <= x + width && mouseY >= iy && mouseY <= iy + itemH) {
                    selectedIndex = i; expanded = false; return;
                }
            }
            expanded = false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {}

    @Override
    public void mouseDragged(int mouseX, int mouseY, int button, long timeSinceClick) {}

    @Override
    public boolean keyTyped(char typedChar, int keyCode) {
        if (!isFocused()) return false;
        // LWJGL key codes: up=200, down=208, enter=28, esc=1, left=203, right=205
        if (!expanded) {
            if (keyCode == 28 || keyCode == 57) { // enter or space
                expanded = true; return true;
            }
            return false;
        }
        if (keyCode == 200) { // up
            selectedIndex = Math.max(0, selectedIndex - 1); return true;
        }
        if (keyCode == 208) { // down
            selectedIndex = Math.min(options.size() - 1, selectedIndex + 1); return true;
        }
        if (keyCode == 28) { // enter
            expanded = false; return true;
        }
        if (keyCode == 1) { // esc
            expanded = false; return true;
        }
        return false;
    }

    @Override
    public void onFocusLost() { expanded = false; }
}
