package net.lax1dude.eaglercraft.v1_8.aether.gui;

import io.ishowrnf.aether.theme.AetherTheme; // placeholder import to keep signature
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Simple notification manager that shows toasts at bottom-right.
 */
public class AetherNotificationManager {
    public static class Notification {
        public final String text;
        public final long expiryMs;
        public final int type; // 0=info,1=success,2=warn,3=error
        public final long created;
        public Notification(String t, long ttlMs, int type) { this.text = t; this.expiryMs = ttlMs; this.type = type; this.created = System.currentTimeMillis(); }
    }

    private final Deque<Notification> queue = new ArrayDeque<>();
    private final FontRenderer fr;
    private final int maxVisible = 4;

    public AetherNotificationManager(FontRenderer fr) { this.fr = fr; }

    public void addNotification(String text, long ttlMs, int type) { queue.addFirst(new Notification(text, ttlMs, type)); }

    public void render(int screenW, int screenH) {
        int margin = 12; int spacing = 8; int y = screenH - margin;
        int count = 0;
        for (Notification n : queue) {
            if (count++ >= maxVisible) break;
            long elapsed = System.currentTimeMillis() - n.created;
            if (elapsed > n.expiryMs) continue;
            int w = Math.min(360, 12 + fr.getStringWidth(n.text) + 12);
            y -= 36;
            int x = screenW - margin - w;
            int bg = 0xDD111111;
            if (n.type == 1) bg = 0xDD114411;
            else if (n.type == 2) bg = 0xDD775511;
            else if (n.type == 3) bg = 0xDD661111;
            Gui.drawRect(x, y, x + w, y + 28, bg);
            fr.drawString(n.text, x + 12, y + 8, net.lax1dude.eaglercraft.v1_8.aether.theme.AetherTheme.TEXT, false);
            y -= spacing;
        }
        // purge expired
        queue.removeIf(n -> System.currentTimeMillis() - n.created > n.expiryMs);
    }
}
