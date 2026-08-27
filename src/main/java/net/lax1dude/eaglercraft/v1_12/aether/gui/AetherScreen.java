package net.lax1dude.eaglercraft.v1_12.aether.gui;

import net.lax1dude.eaglercraft.v1_12.aether.theme.AetherTheme;
import net.lax1dude.eaglercraft.v1_12.aether.config.AetherConfigManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * AetherScreen adapted for Eaglercraft 1.12
 */
public class AetherScreen extends GuiScreen {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = mc.fontRenderer;

    private float openProgress = 0f;
    private boolean opening = true;

    private AetherPanelManager panelManager;

    @Override
    public void initGui() {
        this.openProgress = 0f; this.opening = true;
        this.panelManager = new AetherPanelManager(this.width, this.height);
    }

    @Override
    public void updateScreen() {
        if (opening) {
            openProgress += 0.08f;
            if (openProgress >= 1f) { openProgress = 1f; opening = false; }
        }
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        // First let components handle the key. If consumed, do nothing else.
        if (panelManager != null && panelManager.keyTyped(typedChar, keyCode)) return;
        // Close on ESC only if no expanded controls
        if (keyCode == 1) { // ESC
            this.mc.displayGuiScreen(null);
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (panelManager != null) panelManager.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        try {
            int d = org.lwjgl.input.Mouse.getDWheel();
            if (d != 0 && panelManager != null) panelManager.handleMouseScroll(d > 0 ? 1 : -1);
        } catch (Throwable t) {}
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawDimOverlay(0x88000000);
        int headerY = 20; int headerHeight = 48; int w = this.width;
        String title = "✦ AETHER";
        drawCenteredString(fr, title, w/2, headerY + 8, AetherTheme.TEXT);
        int lineW = (int)(w * 0.7f); int lineX = (w - lineW)/2; int lineY = headerY + headerHeight - 6;
        drawRect(lineX, lineY, lineX + lineW, lineY + 1, AetherTheme.BORDER);
        if (panelManager == null) panelManager = new AetherPanelManager(this.width, this.height);
        panelManager.render(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawDimOverlay(int color) { drawRect(0,0,this.width,this.height,color); }
}
