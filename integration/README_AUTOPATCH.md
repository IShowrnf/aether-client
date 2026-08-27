Auto-integration README

I attempted to automate patching a local Eaglercraft client to call KeyHook.poll() once per tick so the Aether GUI opens with the configured key.

Files added in this branch:
- integration/apply_integration_patch.sh  (POSIX shell script)
- integration/apply_integration_patch.bat (Windows helper — falls back to manual instructions)

How to use (POSIX / macOS / Linux):
1) Download/clone this branch to your machine and make the shell script executable:
   chmod +x integration/apply_integration_patch.sh
2) Run the script pointing at your Eaglercraft source root (or run from the project root):
   ./integration/apply_integration_patch.sh /path/to/eaglercraft/sources
3) The script will search for Minecraft.java, back it up to Minecraft.java.bak.aether and try to insert the line:
   net.lax1dude.eaglercraft.v1_12.aether.key.KeyHook.poll();
   inside the runTick/tick method.

If the script fails it will leave a backup and not alter the original. Review the patched file and build your client.

If you prefer, I can also create a PR against your Eaglercraft repo if you give me the repo name and confirm I should open a PR. Otherwise run the script locally once and report any errors and I will fix the script.
