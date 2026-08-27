package net.lax1dude.eaglercraft.v1_8.aether.gui;

import net.lax1dude.eaglercraft.v1_8.aether.gui.components.AetherComponent;
import net.lax1dude.eaglercraft.v1_8.aether.gui.components.AetherKeybind;

/**
 * Extend AetherPanelManager with keyboard forwarding.
 */
public class AetherPanelManagerWithKeys extends AetherPanelManager {
    public AetherPanelManagerWithKeys(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
    }

    public void keyTyped(char typedChar, int keyCode) {
        // Forward to any focused AetherKeybind component
        for (AetherPanel p : this.getPanels()) {
            for (AetherComponent c : p.getChildren()) {
                if (c instanceof AetherKeybind && c.isFocused()) {
                    ((AetherKeybind)c).onKeyTyped(typedChar, keyCode);
                    return;
                }
            }
        }
    }
}
