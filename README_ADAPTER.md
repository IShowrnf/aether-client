# Aether ClickGUI — Eaglercraft Integration Adapter

This README explains how to integrate the Aether ClickGUI (Eaglercraft 1.12 port) into an Eaglercraft 1.12 client build, how to test it, and a short developer checklist.

Package to copy
- src/main/java/net/lax1dude/eaglercraft/v1_12/aether

Quick integration steps
1. Copy the package into your Eaglercraft 1.12 sources so the package is compiled (usually sources/main/java/).
2. Add a per-tick poll call to enable the configured GUI key (example, inside your client tick method):
   net.lax1dude.eaglercraft.v1_12.aether.key.KeyHook.poll();
3. (Optional quick test) Open the GUI manually from any client code:
   Minecraft.getMinecraft().displayGuiScreen(new net.lax1dude.eaglercraft.v1_12.aether.gui.AetherScreen());

Files of interest
- Theme & colors: net.lax1dude.eaglercraft.v1_12.aether.theme.AetherTheme
- GUI entry: net.lax1dude.eaglercraft.v1_12.aether.gui.AetherScreen
- Panel system: net.lax1dude.eaglercraft.v1_12.aether.gui.AetherPanel and AetherPanelManager
- Components: net.lax1dude.eaglercraft.v1_12.aether.gui.components
- Persistence: aether-config.json (AetherConfigJson + AetherConfig)
- Input poll helper: net.lax1dude.eaglercraft.v1_12.aether.key.KeyHook

Testing checklist (smoke tests)
- Open GUI with KeyHook or manual call.
- Drag panels, resize them, and verify position persists after closing the GUI.
- Scroll long panel content; check clipping.
- Interact with components (toggle, slider, dropdown, color picker, keybind).
- Save a theme in ThemeEditor and restart the client to verify persistence.

Notes & caveats
- No assets are included. Add textures/logos to your assets if desired.
- GL scissor is invoked reflectively; make sure your runtime includes LWJGL for correct clipping.
- The code is intentionally dependency-free (no external JSON library). The JSON helper uses a small custom serializer/parser; this is simple and forgiving but not a full JSON library.

If you want a single-file patch to add KeyHook.poll() into a specific client file, tell me the exact file and I will produce a ready-to-apply patch.
