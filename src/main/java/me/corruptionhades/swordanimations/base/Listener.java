package me.corruptionhades.swordanimations.base;

import me.corruptionhades.swordanimations.SwordAnimations;
import me.corruptionhades.swordanimations.screen.custom.TestingScreen;
import net.minecraft.client.MinecraftClient;

public class Listener {

    public static void onUpdate() {
        MinecraftClient mc = MinecraftClient.getInstance();

        if(SwordAnimations.configMenu.wasPressed()) {
            mc.setScreen(new TestingScreen());
        }


        if(mc.options.useKey.isPressed()) {
            if(mc.options.attackKey.wasPressed()) {
                if(!mc.player.isBlocking()) {
                    if (mc.targetedEntity != null) {
                        mc.interactionManager.attackEntity(mc.player, mc.targetedEntity);
                    }
                    Renderer.swing();
                }
            }
        }
    }
}
