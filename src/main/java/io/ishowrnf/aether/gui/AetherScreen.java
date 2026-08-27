package io.ishowrnf.aether.gui;

import io.ishowrnf.aether.theme.AetherTheme;
import io.ishowrnf.aether.config.AetherConfigManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

/**
 * AetherScreen — initial GuiScreen scaffold for the Aether ClickGUI.
 *
 * This class is intentionally minimal and focuses on lifecycle integration
 * with Eaglercraft / Minecraft 1.8-style GuiScreen. It draws a dim background,
 * a header with the Aether logo/title, and placeholder category panels.
 *
 * Extend this with component rendering (panels, toggles, sliders, pickers).
 */
public class AetherScreen extends GuiScreen {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = mc.fontRendererObj;

    // simple open animation state (0..1)
    private float openProgress = 0f;
    private boolean opening = true;

    public AetherScreen() {
    }

    @Override
    public void initGui() {
        // Called when the screen is displayed or when the resolution changes.
        // Load config, prepare panel positions here.
        // Example: read gui scale or panel positions from AetherConfigManager
    }

    @Override
    public void updateScreen() {
        // Update animations
        if (opening) {
            openProgress += 0.08f; // fast open
            if (openProgress >= 1f) {
                openProgress = 1f;
                opening = false;
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false; // mirror Eaglercraft behaviour — do not pause
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        // Close on ESC
        if (keyCode == 1) { // ESC
            this.mc.displayGuiScreen(null);
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        // TODO: dispatch click to panels/components
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Draw dim background over the game
        drawDefaultBackground();
        drawDimOverlay(0x88000000); // subtle dim

        // Compute layout
        ScaledResolution sr = new ScaledResolution(mc);
        int w = this.width;
        int h = this.height;

        // Header
        int headerY = 20;
        int headerHeight = 48;

        // Slight scale animation based on openProgress
        float scale = 0.96f + 0.04f * openProgress; // 0.96 -> 1.0

        // Draw centered header text
        String title = "✦ AETHER";
        int titleColor = AetherTheme.TEXT;
        drawCenteredString(fr, title, w / 2, headerY + 8, titleColor);

        // Draw a thin accent line under header
        int lineW = (int) (w * 0.7f);
        int lineX = (w - lineW) / 2;
        int lineY = headerY + headerHeight - 6;
        drawRect(lineX, lineY, lineX + lineW, lineY + 1, AetherTheme.BORDER);

        // Placeholder category panels (4 panels across)
        int cols = 4;
        int gap = 12;
        int panelWidth = (w - (cols + 1) * gap) / cols;
        int panelHeight = (int) (h * 0.55f);
        int startY = headerY + headerHeight + 12;

        for (int i = 0; i < cols; ++i) {
            int px = gap + i * (panelWidth + gap);
            int py = startY;
            // Panel background
            drawRect(px, py, px + panelWidth, py + panelHeight, AetherTheme.PANEL);
            // Panel border
            drawRect(px, py, px + panelWidth, py + 1, AetherTheme.BORDER);
            // Category title
            String cat = getCategoryName(i);
            int tx = px + 10;
            int ty = py + 8;
            fr.drawString(cat, tx, ty, AetherTheme.TEXT, false);
            // Simple placeholder module rows
            for (int r = 0; r < 8; ++r) {
                int rowY = ty + 14 + r * 16;
                String moduleName = "Module " + (r + 1);
                fr.drawString(moduleName, tx, rowY, AetherTheme.TEXT, false);
                // Toggle indicator (circle) on right
                int iconX = px + panelWidth - 14;
                int iconY = rowY - 2;
                drawRect(iconX - 6, iconY, iconX, iconY + 8, AetherTheme.BORDER);
            }
        }

        // Draw hover tooltips, dropdown overlays, etc (TODO)

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private String getCategoryName(int idx) {
        switch (idx) {
            case 0: return "COMBAT";
            case 1: return "MOVEMENT";
            case 2: return "PLAYER";
            default: return "VISUAL";
        }
    }

    private void drawDimOverlay(int color) {
        drawRect(0, 0, this.width, this.height, color);
    }
}
