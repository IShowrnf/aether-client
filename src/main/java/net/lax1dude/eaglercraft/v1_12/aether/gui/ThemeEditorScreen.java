package net.lax1dude.eaglercraft.v1_12.aether.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.FontRenderer;
import net.lax1dude.eaglercraft.v1_12.aether.config.AetherConfigJson;
import net.lax1dude.eaglercraft.v1_12.aether.theme.AetherTheme;
import net.lax1dude.eaglercraft.v1_12.aether.gui.components.AetherColorPicker;

import java.util.ArrayList;

/**
 * Theme editor for 1.12 port (simple)
 */
public class ThemeEditorScreen extends GuiScreen {
    private final GuiScreen parent; private final FontRenderer fr; private AetherColorPicker colorPicker; private GuiButton btnSave;
    public ThemeEditorScreen(GuiScreen parent) { this.parent = parent; this.fr = net.minecraft.client.Minecraft.getMinecraft().fontRenderer; }
    @Override public void initGui() {
        this.buttonList.clear();
        // load existing theme value if present
        int initial = AetherTheme.ACCENT_VIOLET;
        try {
            AetherConfigJson.Config cfg = AetherConfigJson.load();
            if ("CUSTOM".equals(cfg.theme)) {
                String s = net.lax1dude.eaglercraft.v1_12.aether.config.AetherConfigManager.get("theme.CUSTOM.accent", null);
                if (s != null) initial = Integer.parseInt(s);
            }
        } catch (Throwable t) {}
        this.colorPicker = new AetherColorPicker(fr, initial);
        this.colorPicker.setPosition(this.width/2 - 90, this.height/2 - 60); this.colorPicker.setSize(180,18);
        btnSave = new GuiButton(0,this.width/2 - 50,this.height/2 + 40,100,20,"Save Theme"); this.buttonList.add(btnSave);
    }
    @Override protected void actionPerformed(GuiButton button) { if (button == btnSave) { int c = colorPicker.getColor(); AetherConfigJson.Config cfg = AetherConfigJson.load(); cfg.theme = "CUSTOM"; if (cfg.panels == null) cfg.panels = new ArrayList<>(); net.lax1dude.eaglercraft.v1_12.aether.config.AetherConfigManager.set("theme.CUSTOM.accent", Integer.toString(c)); AetherConfigJson.save(cfg); net.lax1dude.eaglercraft.v1_12.aether.gui.AetherNotificationManager nm = new net.lax1dude.eaglercraft.v1_12.aether.gui.AetherNotificationManager(fr); nm.addNotification("Saved theme as CUSTOM", 3000, 1); } }
    @Override public void drawScreen(int mouseX, int mouseY, float partialTicks) { this.drawDefaultBackground(); drawCenteredString(fr, "Theme Editor", this.width/2, 50, AetherTheme.TEXT); colorPicker.render(mouseX, mouseY, partialTicks); super.drawScreen(mouseX, mouseY, partialTicks); }
    @Override protected void keyTyped(char typedChar, int keyCode) { if (keyCode == 1) { this.mc.displayGuiScreen(parent); return; } super.keyTyped(typedChar, keyCode); }
}
