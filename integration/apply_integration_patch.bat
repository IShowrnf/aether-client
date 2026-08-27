@echo off
REM apply_integration_patch.bat - Windows helper to patch Minecraft.java with KeyHook.poll()
REM Usage: apply_integration_patch.bat C:\path\to\eaglercraft\sources
nSET REPO_ROOT=%1nIF "%REPO_ROOT%"=="" SET REPO_ROOT=.necho Searching for Minecraft.java under %REPO_ROOT%
for /f "delims=" %%F in ('dir /s /b "%REPO_ROOT%\Minecraft.java" 2^>nul') do (
  set "FOUND=%%F"
  goto :found
)
echo Minecraft.java not found under %REPO_ROOT% >&2
exit /b 2
:found
echo Patching %FOUND%
copy "%FOUND%" "%FOUND%.bak.aether" >nul
nREM This batch file cannot safely parse Java — show instructions insteadnecho.
echo Manual steps (Windows):
echo 1) Open %FOUND% in a text editor.
echo 2) Find the client tick method, commonly named "public void runTick()" or "public void tick()".
echo 3) Inside that method, after input polling, add this line:
echo     net.lax1dude.eaglercraft.v1_12.aether.key.KeyHook.poll();
echo.
echo Backup saved as %FOUND%.bak.aether
exit /b 0
