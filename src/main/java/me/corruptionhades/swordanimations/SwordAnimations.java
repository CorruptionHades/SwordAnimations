package me.corruptionhades.swordanimations;

import me.corruptionhades.swordanimations.base.Renderer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SwordAnimations implements ModInitializer {

    public static KeyBinding configMenu;

    @Override
    public void onInitialize() {
        Renderer.init();

        configMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding("Sword Animations Config Menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_CONTROL, "Sword Animations"));
    }
}
