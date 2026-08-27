package net.lax1dude.eaglercraft.v1_8.aether.config;

import net.lax1dude.eaglercraft.v1_8.aether.gui.AetherPanel;
import net.lax1dude.eaglercraft.v1_8.aether.gui.AetherPanelManager;

import java.util.List;

/**
 * Higher-level config helpers that serialize panel layout and simple theme keys
 * using the existing AetherConfigManager (properties store).
 */
public class AetherConfig {

    private static final String KEY_PANEL_COUNT = "panels.count";
    private static final String KEY_PANEL_PREFIX = "panels."; // panels.0=TITLE|x,y,w,h
    private static final String KEY_THEME_CURRENT = "theme.current";

    public static void savePanelLayout(AetherPanelManager manager) {
        List<AetherPanel> panels = manager.getPanels();
        AetherConfigManager.set(KEY_PANEL_COUNT, Integer.toString(panels.size()));
        for (int i = 0; i < panels.size(); ++i) {
            AetherPanel p = panels.get(i);
            String v = escape(p.title()) + "|" + p.x + "," + p.y + "," + p.width + "," + p.height;
            AetherConfigManager.set(KEY_PANEL_PREFIX + i, v);
        }
    }

    public static void loadPanelLayout(AetherPanelManager manager) {
        String cntStr = AetherConfigManager.get(KEY_PANEL_COUNT, "0");
        int cnt = 0;
        try { cnt = Integer.parseInt(cntStr); } catch (NumberFormatException e) { cnt = 0; }
        // If no saved panels, do nothing
        if (cnt <= 0) return;
        // Clear existing panels and rebuild from saved data: simplistic approach
        manager.getPanels().clear();
        for (int i = 0; i < cnt; ++i) {
            String v = AetherConfigManager.get(KEY_PANEL_PREFIX + i, null);
            if (v == null) continue;
            String[] parts = v.split("\|", 2);
            String title = unescape(parts[0]);
            String[] nums = parts[1].split(",");
            if (nums.length != 4) continue;
            try {
                int x = Integer.parseInt(nums[0]);
                int y = Integer.parseInt(nums[1]);
                int w = Integer.parseInt(nums[2]);
                int h = Integer.parseInt(nums[3]);
                AetherPanel p = new AetherPanel(manager.getFontRenderer(), title, x, y, w, h);
                manager.getPanels().add(p);
            } catch (Exception ex) {
                // skip invalid
            }
        }
    }

    public static void setCurrentTheme(String themeId) {
        AetherConfigManager.set(KEY_THEME_CURRENT, themeId);
    }

    public static String getCurrentTheme() {
        return AetherConfigManager.get(KEY_THEME_CURRENT, "AETHER");
    }

    private static String escape(String s) {
        return s.replace("|", "%7C");
    }
    private static String unescape(String s) {
        return s.replace("%7C", "|");
    }
}
