package net.lax1dude.eaglercraft.v1_12.aether.util;

/**
 * Small animation helper utilities for GUI easing and timing.
 */
public final class AnimUtils {
    private AnimUtils() {}

    public static float approach(float current, float target, float speed) {
        if (current == target) return current;
        float delta = target - current;
        float step = Math.signum(delta) * speed;
        if (Math.abs(step) > Math.abs(delta)) return target;
        return current + step;
    }

    public static float easeOutCubic(float t) {
        t = Math.max(0f, Math.min(1f, t));
        t = t - 1f;
        return t * t * t + 1f;
    }
}
