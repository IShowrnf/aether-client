# Aether GUI — progress and testing (updated)

This branch contains an interactive, incremental implementation of the Aether ClickGUI for Eaglercraft 1.8-style clients.

Recent additions (phase 4–5):
- Rendering utilities: src/main/java/io/ishowrnf/aether/gui/AetherRenderUtils.java
- Dropdown component: src/main/java/io/ishowrnf/aether/gui/components/AetherDropdown.java
- Color picker component: src/main/java/io/ishowrnf/aether/gui/components/AetherColorPicker.java
- Keybind selector component: src/main/java/io/ishowrnf/aether/gui/components/AetherKeybind.java
- Notification manager: src/main/java/io/ishowrnf/aether/gui/AetherNotificationManager.java

How to test
1. Merge the `io.ishowrnf.aether` package files into an Eaglercraft sources tree under `sources/main/java/`.
2. Open the GUI in-game:

```java
Minecraft.getMinecraft().displayGuiScreen(new io.ishowrnf.aether.gui.AetherScreen());
```

3. Interact with panels and components:
- Click toggles to switch them.
- Drag sliders.
- Click a dropdown to expand and pick options.
- Click the color picker to open presets and choose colors.
- Click a keybind control to enter listen mode; then press a key (the parent AetherScreen must forward keyTyped events to focused components for full functionality).
- Notifications: You can create notifications by adding calls to AetherNotificationManager.addNotification from code.

Notes & next work
- Scissoring is implemented as a no-op fallback; replace enableScissor/disableScissor in AetherRenderUtils with the Eaglercraft engine's scissor calls for accurate clipping.
- I will next implement:
  - Proper scissor usage and rounded rect shader-style rendering where available.
  - Full color picker with SV square and hue slider.
  - Keybind persistence and central keybinding manager for the GUI key (RIGHT_SHIFT by default).
  - Theme editor UI + config persistence to JSON.

If you'd like me to stop or reprioritize, tell me now. Otherwise I will continue implementing scissor/clipping and theme/config persistence and push updates to feature/aether-gui.
