package net.lax1dude.eaglercraft.v1_12.aether.gui.components;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.lax1dude.eaglercraft.v1_12.aether.theme.AetherTheme;
import net.lax1dude.eaglercraft.v1_12.aether.util.AnimUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Improved color picker with SV square rendering and presets + alpha for 1.12.
 * SV square is drawn as horizontal lines (more accurate gradient) — keeps allocations low.
 */
public class AetherColorPicker extends AetherComponent {
    private final FontRenderer fr;
    private int color = AetherTheme.ACCENT_VIOLET;
    private boolean open = false;

    private float hue = 270f; // 0..360
    private float sat = 1f; // 0..1
    private float val = 1f; // 0..1
    private int alpha = 255; // 0..255

    private static final List<Integer> PRESETS = Arrays.asList(
        AetherTheme.ACCENT_VIOLET, AetherTheme.ACCENT_CYAN, 0xFF3B82F6, 0xFFEF4444, 0xFF10B981, 0xFFFF7A00, 0xFFFFFFFF
    );

    public AetherColorPicker(FontRenderer fr, int defaultColor) {
        this.fr = fr;
        setColor(defaultColor);
        this.width = 160; this.height = 18;
    }

    public int getColor() { return color; }

    public void setColor(int c) {
        this.color = c;
        float[] hsv = rgbToHsv(c);
        this.hue = hsv[0]; this.sat = hsv[1]; this.val = hsv[2];
        this.alpha = (c >> 24) & 0xFF;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        // collapsed
        Gui.drawRect(x, y, x + width, y + height, AetherTheme.SECONDARY_PANEL);
        fr.drawString(String.format("#%06X", (color & 0xFFFFFF)), x + 6, y + 4, AetherTheme.TEXT, false);
        // swatch
        Gui.drawRect(x + width - 22, y + 3, x + width - 4, y + height - 3, color);

        if (open) {
            int boxY = y + height + 4;
            int svSize = 96;
            int svX = x + 6;
            int svY = boxY;

            // Draw SV square by horizontal scanlines. This produces a smooth gradient with limited allocations.
            for (int iy = 0; iy < svSize; ++iy) {
                float v = 1f - (float)iy / (svSize - 1);
                int left = svX;
                int right = svX + svSize;
                // Build a small pixel row by drawing many 1px-wide vertical strips is expensive; instead draw single horizontal line per pixel with color varying across x.
                // We'll approximate by drawing thin vertical slices of width 1 (ok for 96px).
                for (int ix = 0; ix < svSize; ++ix) {
                    float s = (float)ix / (svSize - 1);
                    int col = hsvToRgb((int)hue, s, v);
                    Gui.drawRect(svX + ix, svY + iy, svX + ix + 1, svY + iy + 1, col);
                }
            }

            // Hue slider
            int hueX = svX + svSize + 10;
            int hueW = 12; int hueH = svSize;
            for (int i = 0; i < hueH; ++i) {
                int hh = (int)((float)i / (float)(hueH - 1) * 360f);
                int c = hsvToRgb(hh, 1f, 1f);
                Gui.drawRect(hueX, svY + i, hueX + hueW, svY + i + 1, c);
            }

            // alpha slider under them
            int aY = svY + svSize + 8;
            int aH = 8; int aW = svSize + hueW + 10;
            int aX = svX;
            Gui.drawRect(aX, aY, aX + aW, aY + aH, AetherTheme.SECONDARY_PANEL);
            int filled = (int)((alpha / 255f) * aW);
            Gui.drawRect(aX, aY, aX + filled, aY + aH, ((alpha & 0xFF) << 24) | (hsvToRgb((int)hue, sat, val) & 0xFFFFFF));

            // Presets row
            int presetY = aY + aH + 8;
            int sw = 20; int gap = 6; int cx = x + 6;
            for (int i = 0; i < PRESETS.size(); ++i) {
                int sx = cx + i * (sw + gap);
                Gui.drawRect(sx, presetY, sx + sw, presetY + sw, PRESETS.get(i));
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (!open && containsPoint(mouseX, mouseY)) { open = true; return; }
        if (open) {
            int boxY = y + height + 4;
            int svSize = 96;
            int svX = x + 6;
            int svY = boxY;
            int hueX = svX + svSize + 10;
            int hueW = 12; int hueH = svSize;
            int aY = svY + svSize + 8; int aH = 8; int aW = svSize + hueW + 10; int aX = svX;
            int presetY = aY + aH + 8; int sw = 20; int gap = 6; int cx = x + 6;

            // SV
            if (mouseX >= svX && mouseX <= svX + svSize && mouseY >= svY && mouseY <= svY + svSize) {
                int relX = mouseX - svX; int relY = mouseY - svY;
                this.sat = (float)relX / (float)(svSize - 1);
                this.val = 1f - (float)relY / (float)(svSize - 1);
                updateColorFromHSV();
                return;
            }
            // Hue
            if (mouseX >= hueX && mouseX <= hueX + hueW && mouseY >= svY && mouseY <= svY + hueH) {
                int rel = mouseY - svY;
                this.hue = (float)rel / (float)(hueH - 1) * 360f;
                updateColorFromHSV();
                return;
            }
            // Alpha
            if (mouseX >= aX && mouseX <= aX + aW && mouseY >= aY && mouseY <= aY + aH) {
                int rel = mouseX - aX;
                this.alpha = (int)((float)rel / (float)aW * 255f);
                updateColorFromHSV();
                return;
            }
            // Presets
            for (int i = 0; i < PRESETS.size(); ++i) {
                int sx = cx + i * (sw + gap);
                if (mouseX >= sx && mouseX <= sx + sw && mouseY >= presetY && mouseY <= presetY + sw) {
                    setColor(PRESETS.get(i));
                    open = false; return;
                }
            }
            // click outside: close
            open = false;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {}

    @Override
    public void mouseDragged(int mouseX, int mouseY, int button, long timeSinceClick) {}

    private void updateColorFromHSV() {
        int rgb = hsvToRgb((int)hue, sat, val);
        color = ((alpha & 0xFF) << 24) | (rgb & 0xFFFFFF);
    }

    private static int hsvToRgb(int h, float s, float v) {
        float hf = (h % 360) / 60f;
        int i = (int)Math.floor(hf);
        float f = hf - i;
        float p = v * (1f - s);
        float q = v * (1f - s * f);
        float t = v * (1f - s * (1f - f));
        float r=0,g=0,b=0;
        switch (i) {
            case 0: r=v; g=t; b=p; break;
            case 1: r=q; g=v; b=p; break;
            case 2: r=p; g=v; b=t; break;
            case 3: r=p; g=q; b=v; break;
            case 4: r=t; g=p; b=v; break;
            default: r=v; g=p; b=q; break;
        }
        int ri = Math.max(0, Math.min(255, (int)(r*255f)));
        int gi = Math.max(0, Math.min(255, (int)(g*255f)));
        int bi = Math.max(0, Math.min(255, (int)(b*255f)));
        return (ri<<16) | (gi<<8) | bi;
    }

    private static float[] rgbToHsv(int argb) {
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = (argb) & 0xFF;
        float rf = r / 255f, gf = g / 255f, bf = b / 255f;
        float max = Math.max(rf, Math.max(gf, bf));
        float min = Math.min(rf, Math.min(gf, bf));
        float h=0, s=0, v=max;
        float d = max - min;
        s = max == 0 ? 0 : d / max;
        if (max == min) h = 0f;
        else if (max == rf) h = (gf - bf) / d + (gf < bf ? 6f : 0f);
        else if (max == gf) h = (bf - rf) / d + 2f;
        else h = (rf - gf) / d + 4f;
        h *= 60f;
        return new float[]{h, s, v};
    }
}
