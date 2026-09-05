package io.ishowrnf.aether.mod;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import io.ishowrnf.aether.gui.AetherScreen;

@Mod(modid = "aetherclient", name = "Aether Client", version = "0.1", clientSideOnly = true)
public class ModMain {
    private static KeyBinding openGuiKey;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Client-only setup
        if (event.getSide() == Side.CLIENT) {
            openGuiKey = new KeyBinding("key.aether.open", Keyboard.KEY_RSHIFT, "key.categories.aether");
            ClientRegistry.registerKeyBinding(openGuiKey);
            MinecraftForge.EVENT_BUS.register(new ClientEvents());
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public void onKeyInput(InputEvent.KeyInputEvent ev) {
            if (openGuiKey != null && openGuiKey.isPressed()) {
                Minecraft.getMinecraft().displayGuiScreen(new AetherScreen());
            }
        }
    }
}
