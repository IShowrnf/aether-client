#!/usr/bin/env bash
# apply_integration_patch.sh
# Attempts to find Minecraft.java in a local Eaglercraft source tree and insert the KeyHook.poll() call
# Usage: ./apply_integration_patch.sh /path/to/eaglercraft/repo

set -e

REPO_ROOT="${1:-.}"
echo "Searching for Minecraft.java under: $REPO_ROOT"

mapfile -t matches < <(find "$REPO_ROOT" -type f -name Minecraft.java 2>/dev/null)

if [ ${#matches[@]} -eq 0 ]; then
  echo "No Minecraft.java found under $REPO_ROOT"
  echo "Try running from the root of your Eaglercraft sources or pass the path as the first argument." >&2
  exit 2
fi

if [ ${#matches[@]} -gt 1 ]; then
  echo "Multiple Minecraft.java files found:" 
  for i in "${!matches[@]}"; do
    printf "%3d) %s\n" "$((i+1))" "${matches[$i]}"
  done
  echo "Please re-run with the path to the correct file as the first argument." >&2
  exit 3
fi

TARGET_FILE="${matches[0]}"
echo "Patching file: $TARGET_FILE"

# Create a backup
cp "$TARGET_FILE" "$TARGET_FILE.bak.aether"

# We'll try to find the runTick/tick method and insert the poll line after the opening brace
POLL_LINE='        net.lax1dude.eaglercraft.v1_12.aether.key.KeyHook.poll();'

awk -v poll="$POLL_LINE" '
  BEGIN { inserted=0 }
  {
    print $0
    if (!inserted) {
      # detect common method signatures
      if ($0 ~ /public[ 	]+void[ 	]+(runTick|tick)\([^{]*\)[ 	]*\{[ 	]*$/) {
        # method already has opening brace on the same line
        print poll
        inserted=1
      } else if ($0 ~ /public[ 	]+void[ 	]+(runTick|tick)\([^{]*\)[ 	]*$/) {
        # method signature line; next non-empty line may be the opening brace
        sig_line=NR
        getline; print $0
        if ($0 ~ /^[ 	]*\{[ 	]*$/) {
          print poll
          inserted=1
        }
      }
    }
  }
  END {
    if (!inserted) {
      # Fallback: try to insert after first occurrence of "public void runTick()" simple
      # This awk pass didn't insert; signal failure by exit code
      exit 5
    }
  }' "$TARGET_FILE" > "$TARGET_FILE.aether.tmp" || {
    echo "Automatic insertion failed. Restoring backup." >&2
    mv "$TARGET_FILE.bak.aether" "$TARGET_FILE"
    rm -f "$TARGET_FILE.aether.tmp"
    exit 5
  }

mv "$TARGET_FILE.aether.tmp" "$TARGET_FILE"
chmod --reference="$TARGET_FILE.bak.aether" "$TARGET_FILE" 2>/dev/null || true

echo "Patched successfully. Backup saved to $TARGET_FILE.bak.aether"

echo "If the script failed because it couldn't reliably find the method signature, open the backup and insert the following line inside your per-tick method (after input polling):"
 echo "\n    net.lax1dude.eaglercraft.v1_12.aether.key.KeyHook.poll();\n"

exit 0
