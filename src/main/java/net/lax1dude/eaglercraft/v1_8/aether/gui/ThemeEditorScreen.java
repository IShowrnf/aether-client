package net.lax1dude.eaglercraft.v1_8.aether.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.FontRenderer;
import net.lax1dude.eaglercraft.v1_8.aether.config.AetherConfig;
import net.lax1dude.eaglercraft.v1_8.aether.config.AetherConfigJson;
import net.lax1dude.eaglercraft.v1_8.aether.theme.AetherTheme;
import net.lax1dude.eaglercraft.v1_8.aether.gui.components.AetherColorPicker;

/**
 * Minimal theme editor screen: allows editing accent color and saving as a simple theme.
 */
public class ThemeEditorScreen extends GuiScreen {
    private final GuiScreen parent;
    private final FontRenderer fr;
    private AetherColorPicker colorPicker;
    private GuiButton btnSave;

    public ThemeEditorScreen(GuiScreen parent) {
        this.parent = parent;
        this.fr = net.minecraft.client.Minecraft.getMinecraft().fontRendererObj;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.colorPicker = new AetherColorPicker(fr, AetherTheme.ACCENT_VIOLET);
        this.colorPicker.setPosition(this.width / 2 - 90, this.height / 2 - 60);
        this.colorPicker.setSize(180, 18);
        btnSave = new GuiButton(0, this.width / 2 - 50, this.height / 2 + 40, 100, 20, "Save Theme");
        this.buttonList.add(btnSave);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == btnSave) {
            int c = colorPicker.getColor();
            // Save a very small theme representation in JSON
            AetherConfigJson.Config cfg = AetherConfigJson.load();
            cfg.theme = "CUSTOM";
            cfg.panels = cfg.panels == null ? new java.util.ArrayList<>() : cfg.panels;
            // store accent as a special panel entry hack (keeps file small) — key: theme.CUSTOM.accent in properties fallback retained
            net.lax1dude.eaglercraft.v1_8.aether.config.AetherConfigManager.set("theme.CUSTOM.accent", Integer.toString(c));
            AetherConfigJson.save(cfg);
            // Notify user
            net.lax1dude.eaglercraft.v1_8.aether.gui.AetherNotificationManager nm = new net.lax1dude.eaglercraft.v1_8.aether.gui.AetherNotificationManager(fr);
            nm.addNotification("Saved theme as CUSTOM", 3000, 1);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        drawCenteredString(fr, "Theme Editor", this.width / 2, 50, AetherTheme.TEXT);
        colorPicker.render(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(parent);
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }
}
