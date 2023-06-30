package me.corruptionhades.swordanimations.base;

import me.corruptionhades.swordanimations.screen.custom.NumberSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

public class Renderer {

    // HELP: https://discord.com/channels/507304429255393322/807617488313516032/1086932089041080371

    public static float start, end, current;
    public static final Mode<?> mode = new Mode<>(Modes.Sword, Modes.values());

    public static void init() {
        start = -90;
        end = start;
        current = start;
    }

    public static NumberSetting
            tXs = new NumberSetting("Translate X Start", -10, 10, 0, 0.1),
            tYs = new NumberSetting("Translate Y Start", -10, 10, 0, 0.1),
            tZs = new NumberSetting("Translate Z Start", -10, 10, 0, 0.1),
            tX = new NumberSetting("Translate X End", -10, 10, 0, 0.1),
            tY = new NumberSetting("Translate Y End", -10, 10, 0, 0.1),
            tZ = new NumberSetting("Translate Z End", -10, 10, 0, 0.1),
            rotX = new NumberSetting("Rotation X", -360, 360, 0, 1),
            rotY = new NumberSetting("Rotation Y", -360, 360, 0, 1),
            rotZ = new NumberSetting("Rotation Z", -360, 360, 0, 1),
            scaleX = new NumberSetting("Scale X", 0, 3, 1, 0.1),
            scaleY = new NumberSetting("Scale Y", 0, 3, 1, 0.1),
            scaleZ = new NumberSetting("Scale Z", 0, 3, 1, 0.1);

    public static boolean override = false;
    private static long lastFrameTime = 0;

    public static void render(ItemStack stack, MatrixStack matrices) {

        MinecraftClient mc = MinecraftClient.getInstance();

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastFrameTime;
        lastFrameTime = currentTime;

        if(!isValid(stack)) {
            return;
        }

        if (mc.options.useKey.isPressed()) {

            // Calculate the swing increment based on elapsed time
            float swingIncrement = 200f; // Adjust the swing speed as desired
            float swingProgress = swingIncrement * (elapsedTime / 1000f); // Divide by 1000 to convert milliseconds to seconds


                if (current > end) {
                    current -= swingProgress;
                }
                else if (current < end) {
                    current += swingProgress;
                }
                if (end == -130 && current <= end) {
                    end = start;
                }
                if(end == start && current >= end) {
                    current = end;
                }


            // Default handheld rotation is
            // 0, -90, 25
            // translation is 1.13, 3.2, 1.13
            // scale is 0.68

        try {

            if(override) {
                matrices.translate(tXs.getFloatValue(), tYs.getFloatValue(), tZs.getFloatValue());
                // matrices.scale(scaleX.getFloatValue(), scaleY.getFloatValue(), scaleZ.getFloatValue());
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotY.getFloatValue()));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotZ.getFloatValue()));
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rotX.getFloatValue()));
                matrices.translate(tX.getFloatValue(), tY.getFloatValue(), tZ.getFloatValue());
            }
            else {
                // Set the rotation point at the handle of the sword
                matrices.translate(0.4, -0.2, -0.1);
                // Rotates to face the player
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(85));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-17));
                // Rotates the sword up/down
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(current));

                matrices.translate(0, 0.6, 0.7);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

       }
    }

    public static void rotate(MatrixStack matrices, float angle, float x, float y, float z) {
        Quaternionf quaternion = new Quaternionf(x, y, z, angle);
        matrices.multiply(quaternion);
    }

    public static void swing() {
        end = -130;
    }

    public static boolean isValid(ItemStack stack) {
        return stack.getItem() instanceof SwordItem ||
                (stack.getItem() instanceof AxeItem && (mode.getMode() == Modes.Weapons || mode.getMode() == Modes.ALL)) ||
                (stack.getItem() instanceof ShovelItem && (mode.getMode() == Modes.ALL)) ||
                (stack.getItem() instanceof PickaxeItem && (mode.getMode() == Modes.ALL)) ||
                (stack.getItem() instanceof HoeItem && (mode.getMode() == Modes.ALL));
    }

    private enum Modes {
        Sword,
        Weapons,
        ALL, Off;
    }
}
