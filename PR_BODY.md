PR: Aether ClickGUI — Eaglercraft 1.12 final polish and integration

What this PR contains
- Finalized Eaglercraft 1.12 port of the Aether ClickGUI with:
  - Full panel system, components (toggle, slider, dropdown, color picker, keybind selector), notifications, theme editor, persistent JSON config, and keyhook integration helper.
  - Keyboard navigation (dropdown, slider) and ESC/blur handling.
  - Lightweight animation helper (AnimUtils) and render polish functions.
  - README_ADAPTER.md explaining integration and testing steps.

How to test
- See README_ADAPTER.md for the smoke-test checklist. In short: copy the package into your Eaglercraft 1.12 sources, call KeyHook.poll() from your client tick, open the GUI, and exercise components and persistence.

Notes
- I used a small custom JSON serializer/parser to avoid adding external dependencies; if you prefer a library like Gson, I can migrate the persistence layer.
- GL calls are invoked via reflection to remain compatible with multiple runtimes; ensure LWJGL is included in your runtime for scissor clipping.
