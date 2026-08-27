# Aether GUI — progress and testing

This file lists the current state of the feature/aether-gui branch and how to test it.

Files added so far (phase 2–4):
- src/main/java/io/ishowrnf/aether/gui/AetherScreen.java
- src/main/java/io/ishowrnf/aether/gui/AetherPanel.java
- src/main/java/io/ishowrnf/aether/gui/AetherPanelManager.java
- src/main/java/io/ishowrnf/aether/gui/components/AetherComponent.java
- src/main/java/io/ishowrnf/aether/gui/components/AetherToggle.java
- src/main/java/io/ishowrnf/aether/gui/components/AetherSlider.java
- src/main/java/io/ishowrnf/aether/theme/AetherTheme.java
- src/main/java/io/ishowrnf/aether/theme/AetherThemeManager.java
- src/main/java/io/ishowrnf/aether/config/AetherConfigManager.java
- assets/logo/aether_logo.svg

What to test locally in an Eaglercraft dev environment
1. Merge or copy the `io.ishowrnf.aether` package files into the Eaglercraft sources under `sources/main/java/`.
2. From any place with access to the Minecraft instance (e.g., a custom key handler or main menu), call:

```java
Minecraft.getMinecraft().displayGuiScreen(new io.ishowrnf.aether.gui.AetherScreen());
```

3. When the GUI opens you should see a dim overlay, centered AETHER title, and four panels with placeholder modules. You can:
   - Click a module toggle to toggle its state visually.
   - Drag a panel by its title bar.
   - Resize a panel by dragging the bottom-right corner.
   - Drag sliders by clicking/dragging their handles.
   - Scroll inside a panel using the mouse wheel when over it (handled per-panel).

Notes & limitations
- This is a working UI scaffold but not a finished product. Missing:
  - Rounded rectangle helpers
  - Proper scissoring/clipping via GL scissor; the current implementation uses simple Y-range checks to avoid rendering off-panel elements.
  - Dropdowns, color picker, keybind selector, theme editor, notifications, tooltips
  - Persistence beyond the simple properties file (AetherConfigManager)
  - Accessibility keyboard navigation
  - Animation refinements and polished visuals

Next steps (I will implement automatically unless you ask otherwise):
1. Rounded rect utility and scissoring for proper clipping.
2. Implement AetherDropdown, AetherColorPicker, AetherKeybind components.
3. Notification system and toasts.
4. Theme editor UI and persistence for themes.
5. Save/Load config using JSON for panel positions/sizes and module states.
6. Open animation polish and micro transitions.

If you want me to stop or change priorities, say so. Otherwise I will continue implementing the remaining components and persistence and push incremental commits to feature/aether-gui.
