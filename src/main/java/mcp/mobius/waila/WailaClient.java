package mcp.mobius.waila;

import mcp.mobius.waila.api.impl.config.WailaConfig;
import mcp.mobius.waila.gui.GuiConfigHome;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

public class WailaClient implements ClientModInitializer {

    public static FabricKeyBinding openConfig;
    public static FabricKeyBinding showOverlay;
    public static FabricKeyBinding toggleLiquid;

    @Override
    public void onInitializeClient() {
        KeyBindingRegistry.INSTANCE.addCategory(Waila.NAME);
        openConfig = FabricKeyBinding.Builder.create(new Identifier(Waila.MODID, "config"), InputUtil.Type.KEY_KEYBOARD, 320, Waila.NAME).build(); // Num 0
        showOverlay = FabricKeyBinding.Builder.create(new Identifier(Waila.MODID, "show_overlay"), InputUtil.Type.KEY_KEYBOARD, 321, Waila.NAME).build(); // Num 1
        toggleLiquid = FabricKeyBinding.Builder.create(new Identifier(Waila.MODID, "toggle_liquid"), InputUtil.Type.KEY_KEYBOARD, 322, Waila.NAME).build(); // Num 2
        KeyBindingRegistry.INSTANCE.register(openConfig);
        KeyBindingRegistry.INSTANCE.register(showOverlay);
        KeyBindingRegistry.INSTANCE.register(toggleLiquid);
    }

    public static void handleKeybinds() {
        if (openConfig == null || showOverlay == null || toggleLiquid == null)
            return;

        while (openConfig.wasPressed()) {
            MinecraftClient.getInstance().openGui(new GuiConfigHome(null));
        }

        while (showOverlay.wasPressed()) {
            if (Waila.CONFIG.get().getGeneral().getDisplayMode() == WailaConfig.DisplayMode.TOGGLE) {
                Waila.CONFIG.get().getGeneral().setDisplayTooltip(!Waila.CONFIG.get().getGeneral().shouldDisplayTooltip());
            }
        }

        while (toggleLiquid.wasPressed()) {
            Waila.CONFIG.get().getGeneral().setDisplayFluids(!Waila.CONFIG.get().getGeneral().shouldDisplayFluids());
        }
    }
}
