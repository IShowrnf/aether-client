package net.lax1dude.eaglercraft.v1_12.aether.config;

import net.lax1dude.eaglercraft.v1_12.aether.gui.AetherPanel;
import net.lax1dude.eaglercraft.v1_12.aether.gui.AetherPanelManager;

import java.util.List;

/**
 * Glue for saving and loading configuration via AetherConfigJson.
 */
public final class AetherConfig {
    private AetherConfig() {}

    public static void savePanelLayout(AetherPanelManager manager) {
        AetherConfigJson.Config cfg = AetherConfigJson.load();
        cfg.panels.clear();
        List<AetherPanel> panels = manager.getPanels();
        for (AetherPanel p : panels) {
            AetherConfigJson.PanelEntry e = new AetherConfigJson.PanelEntry(p.getTitle(), p.x, p.y, p.width, p.height);
            cfg.panels.add(e);
        }
        AetherConfigJson.save(cfg);
    }

    public static void loadPanelLayout(AetherPanelManager manager) {
        AetherConfigJson.Config cfg = AetherConfigJson.load();
        if (cfg.panels == null || cfg.panels.isEmpty()) return;
        manager.getPanels().clear();
        for (AetherConfigJson.PanelEntry e : cfg.panels) {
            AetherPanel p = new AetherPanel(manager.getFontRenderer(), e.title, e.x, e.y, e.w, e.h);
            manager.getPanels().add(p);
        }
    }

    public static void setCurrentTheme(String themeId) {
        AetherConfigJson.Config cfg = AetherConfigJson.load();
        cfg.theme = themeId;
        AetherConfigJson.save(cfg);
    }

    public static String getCurrentTheme() {
        AetherConfigJson.Config cfg = AetherConfigJson.load();
        return cfg.theme;
    }
}
