package net.lax1dude.eaglercraft.v1_12.aether.integration;

/**
 * Integration helper: shows where to call KeyHook.poll() and how to open the screen.
 * Paste the example into your client's per-tick loop (Minecraft.runTick or similar).
 */
public final class IntegrationHelper {
    private IntegrationHelper() {}

    /**
     * Example snippet to poll the Aether KeyHook once per tick.
     *
     * Place this call inside your main client tick or input polling method so
     * it executes once per frame/tick:
     *
     *   // inside your client tick
     *   net.lax1dude.eaglercraft.v1_12.aether.key.KeyHook.poll();
     *
     * Or open the screen manually for a quick test:
     *   Minecraft.getMinecraft().displayGuiScreen(new net.lax1dude.eaglercraft.v1_12.aether.gui.AetherScreen());
     */
    public static void examplePollCall() {
        // no-op - for documentation only
    }
}
