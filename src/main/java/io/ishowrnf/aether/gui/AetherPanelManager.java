package io.ishowrnf.aether.gui;

import io.ishowrnf.aether.gui.components.AetherToggle;
import io.ishowrnf.aether.gui.components.AetherSlider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel manager that constructs category panels and module placeholders.
 */
public class AetherPanelManager {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = mc.fontRendererObj;
    private final List<AetherPanel> panels = new ArrayList<>();

    public AetherPanelManager(int screenWidth, int screenHeight) {
        buildDefaultPanels(screenWidth, screenHeight);
    }

    private void buildDefaultPanels(int w, int h) {
        int cols = 4;
        int gap = 12;
        int panelWidth = (w - (cols + 1) * gap) / cols;
        int panelHeight = (int) (h * 0.55f);
        int startY = 20 + 48 + 12;
        String[] cats = new String[]{"COMBAT","MOVEMENT","PLAYER","VISUAL"};
        for (int i = 0; i < cols; ++i) {
            int px = gap + i * (panelWidth + gap);
            AetherPanel p = new AetherPanel(fr, cats[i], px, startY, panelWidth, panelHeight);
            // add placeholder modules
            for (int r = 0; r < 8; ++r) {
                AetherToggle t = new AetherToggle(fr, "Module " + (r + 1), false);
                p.add(t);
                // add a slider to some modules
                if (r % 3 == 0) {
                    AetherSlider s = new AetherSlider(fr, "Value", 0f, 100f, 50f);
                    p.add(s);
                }
            }
            panels.add(p);
        }
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        for (AetherPanel p : panels) p.render(mouseX, mouseY, partialTicks);
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        for (AetherPanel p : panels) p.mouseClicked(mouseX, mouseY, button);
    }

    public void mouseReleased(int mouseX, int mouseY, int button) {
        for (AetherPanel p : panels) p.mouseReleased(mouseX, mouseY, button);
    }

    public void mouseDragged(int mouseX, int mouseY, int button, long time) {
        for (AetherPanel p : panels) p.mouseDragged(mouseX, mouseY, button, time);
    }

    public void handleMouseScroll(int delta) {
        for (AetherPanel p : panels) p.handleMouseScroll(delta);
    }
}
