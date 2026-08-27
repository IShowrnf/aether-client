# Aether Client — GUI Scaffold

This branch adds an initial scaffold for the Aether ClickGUI UI (Phase 2–3 initial commit).

Goals
- Provide a GuiScreen-based AetherScreen to integrate into Eaglercraft 1.8.x builds.
- Provide a small theme constants class and a simple config manager stub.
- Provide integration instructions for porting these classes into an Eaglercraft build.

Important
- This scaffold does NOT include any Minecraft proprietary assets.
- The module system and full components are stubs. This is an opinionated starting point.

Files added (overview)
- src/main/java/io/ishowrnf/aether/gui/AetherScreen.java — GuiScreen subclass with basic header, dim background, open animation, ESC handling, and placeholder panels.
- src/main/java/io/ishowrnf/aether/theme/AetherTheme.java — theme color constants matching the Aether palette.
- src/main/java/io/ishowrnf/aether/config/AetherConfigManager.java — simple properties-based save/load stub for GUI config.
- assets/logo/aether_logo.svg — placeholder vector logo.
- README_ADAPTER.md — instructions to port these files into the Eaglercraft source and how to open the GUI in-game.

How to test
- Copy the `io.ishowrnf.aether.*` Java files into your Eaglercraft 1.8.8 sources (matching package path). Then call:

```java
// Example: open the Aether GUI from anywhere with access to Minecraft instance
Minecraft.getMinecraft().displayGuiScreen(new io.ishowrnf.aether.gui.AetherScreen());
```

- Add a KeyBinding or input hook for RIGHT_SHIFT to open the GUI.

Next steps
- Implement component library (Toggles, Sliders, Dropdowns, ColorPicker, Keybind UI, Tooltips, Notifications).
- Implement data-driven module system and config persistence with JSON.
- Implement animations, dragging, resizing, scissoring/clipping utilities.

License: MIT (same as repo)
