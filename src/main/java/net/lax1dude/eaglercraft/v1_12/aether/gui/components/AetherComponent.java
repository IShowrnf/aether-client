package net.lax1dude.eaglercraft.v1_12.aether.gui.components;

/**
 * Base class for Aether GUI components (1.12)
 */
public abstract class AetherComponent {
    protected int x, y, width, height;
    protected boolean hovered = false;
    protected boolean focused = false;

    public AetherComponent() {}

    public void setPosition(int x, int y) { this.x = x; this.y = y; }
    public void setSize(int w, int h) { this.width = w; this.height = h; }

    public boolean containsPoint(int mx, int my) { return mx >= x && my >= y && mx < x + width && my < y + height; }
    public void setHovered(boolean h) { this.hovered = h; }
    public boolean isHovered() { return hovered; }
    public void setFocused(boolean f) { this.focused = f; }
    public boolean isFocused() { return focused; }

    // Rendering and input hooks
    public abstract void render(int mouseX, int mouseY, float partialTicks);
    public abstract void mouseClicked(int mouseX, int mouseY, int button);
    public abstract void mouseReleased(int mouseX, int mouseY, int button);
    public abstract void mouseDragged(int mouseX, int mouseY, int button, long timeSinceClick);

    // Keyboard input: return true if the event was consumed
    public boolean keyTyped(char typedChar, int keyCode) { return false; }

    // Called when focus is lost so components can cancel transient state
    public void onFocusLost() {}
}
