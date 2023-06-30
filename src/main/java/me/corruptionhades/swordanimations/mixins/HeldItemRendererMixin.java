package me.corruptionhades.swordanimations.mixins;

import me.corruptionhades.swordanimations.base.Renderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.item.HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    public void renderItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        if(Renderer.mode.is("Off")) {
            return;
        }

        Renderer.render(client.player.getInventory().getMainHandStack(), matrices);
    }

    @Shadow
    private float equipProgressMainHand;
    @Shadow
    private float prevEquipProgressMainHand;
    @Shadow private float prevEquipProgressOffHand;
    @Shadow private float equipProgressOffHand;
    @Shadow private ItemStack mainHand;
    @Shadow private ItemStack offHand;

    @Final
    @Shadow private MinecraftClient client;


    @Inject(method = "updateHeldItems", at = @At("HEAD"), cancellable = true)
    public void updateHeldItems(CallbackInfo ci) {

        if(client.player == null) return;

        if(Renderer.mode.is("Off")) {
            return;
        }

        if(client.options.useKey.isPressed()) {

            ItemStack stack = client.player.getInventory().getMainHandStack();

            if(Renderer.isValid(stack)) {

                this.equipProgressMainHand = 1;
                this.equipProgressOffHand = 1;

                this.prevEquipProgressMainHand = this.equipProgressMainHand;
                this.prevEquipProgressOffHand = this.equipProgressOffHand;
                ClientPlayerEntity clientPlayerEntity = this.client.player;
                ItemStack itemStack = clientPlayerEntity.getMainHandStack();
                ItemStack itemStack2 = clientPlayerEntity.getOffHandStack();

                this.mainHand = itemStack;
                this.offHand = itemStack2;

                ci.cancel();
            }
        }
    }

    @ModifyVariable(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "STORE", ordinal = 0), index = 6)
    private float modifySwing(float value) {

        if(Renderer.mode.is("Off")) {
            return value;
        }

        if(client.options.useKey.isPressed()) {
            ItemStack stack = client.player.getInventory().getMainHandStack();
            if(Renderer.isValid(stack)) {
                return 0;
            }
        }

        return value;
    }
}
