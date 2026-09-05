package io.ishowrnf.aether.gui;

import io.ishowrnf.aether.theme.AetherTheme;
import io.ishowrnf.aether.config.AetherConfigManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

/**
 * AetherScreen — GuiScreen scaffold wired to the panel manager and input forwarding.
 */
public class AetherScreen extends GuiScreen {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = mc.fontRendererObj;

    // simple open animation state (0..1)
    private float openProgress = 0f;
    private boolean opening = true;

    private AetherPanelManager panelManager;
    private AetherNotificationManager notifications;

    public AetherScreen() {
    }

    @Override
    public void initGui() {
        this.openProgress = 0f; this.opening = true;
        this.panelManager = new AetherPanelManager(this.width, this.height);
        this.notifications = new AetherNotificationManager(fr);
        // Load config if needed
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
            // If any child has expanded dropdowns or color pickers, close them first in panelManager
            // For now, simply close screen
            this.mc.displayGuiScreen(null);
            return;
        }
        // Forward key input to focused components (e.g., keybind selectors)
        if (panelManager != null) panelManager.keyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (panelManager != null) panelManager.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int state) {
        super.mouseMovedOrUp(mouseX, mouseY, state);
        if (panelManager != null) panelManager.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if (panelManager != null) panelManager.mouseDragged(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        // Mouse wheel
        int dw = Mouse.getDWheel();
        if (dw != 0 && panelManager != null) {
            panelManager.handleMouseScroll(dw > 0 ? 1 : -1);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Draw dim background over the game
        drawDefaultBackground();
        drawDimOverlay(0x88000000); // subtle dim

        // Header
        int headerY = 20;
        int headerHeight = 48;
        int w = this.width;

        // Draw centered header text
        String title = "✦ AETHER";
        int titleColor = AetherTheme.TEXT;
        drawCenteredString(fr, title, w / 2, headerY + 8, titleColor);

        // Draw a thin accent line under header
        int lineW = (int) (w * 0.7f);
        int lineX = (w - lineW) / 2;
        int lineY = headerY + headerHeight - 6;
        drawRect(lineX, lineY, lineX + lineW, lineY + 1, AetherTheme.BORDER);

        // Panel manager renders panels and their children
        if (panelManager == null) panelManager = new AetherPanelManager(this.width, this.height);
        panelManager.render(mouseX, mouseY, partialTicks);

        // Notifications
        if (notifications != null) notifications.render(this.width, this.height);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawDimOverlay(int color) {
        drawRect(0, 0, this.width, this.height, color);
    }
}
