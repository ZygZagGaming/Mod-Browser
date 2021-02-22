package com.zygzag.modbrowser.mixin;

import com.terraformersmc.modmenu.event.ModMenuEventHandler;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModMenuEventHandler.class)
public class ModBrowserMixin {
    @Inject(method = "afterTitleScreenInit", at = @At(value = "RETURN"), remap = false)
    private static void injectAfterTitleScreenInit(Screen screen, CallbackInfo ci) {
        if (screen instanceof TitleScreen && Screens.getButtons(screen).size() == 9) {
            Screens.getButtons(screen).get(3).setWidth(98);
            Screens.getButtons(screen).get(3).y = (screen.height / 4) + 24 * 5;
        }
    }
}
