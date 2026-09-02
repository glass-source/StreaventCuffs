package com.chachamaru.me.streaventhandcuffs.client.render;

import com.chachamaru.me.streaventhandcuffs.Streaventhandcuffs;
import com.chachamaru.me.streaventhandcuffs.cuffs.CuffLink;
import com.chachamaru.me.streaventhandcuffs.cuffs.CuffType;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = Streaventhandcuffs.MODID, value = Dist.CLIENT)
public final class CuffAnimations {

    private CuffAnimations() {}

    private static final ResourceLocation LAYER = Streaventhandcuffs.id("handcuff_pose");

    // PlayerAnimator keys the registry on the animation's name *inside* the json, lowercased -
    // not on the file name. handcuffs.json declares both of these.
    private static final ResourceLocation BACK = Streaventhandcuffs.id("handcuffs");
    private static final ResourceLocation FRONT = Streaventhandcuffs.id("handcuffsfront");

    private static final int FADE_TICKS = 5;

    public static void register() {
        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register((player, animationStack) -> {
            ModifierLayer<IAnimation> layer = new ModifierLayer<>();
            animationStack.addAnimLayer(1000, layer);
            PlayerAnimationAccess.getPlayerAssociatedData(player).set(LAYER, layer);
        });
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }

        // Every loaded player, not just the local one - other players have to be posed too.
        for (AbstractClientPlayer player : level.players()) {
            IAnimation stored = PlayerAnimationAccess.getPlayerAssociatedData(player).get(LAYER);
            if (!(stored instanceof ModifierLayer)) continue;

            @SuppressWarnings("unchecked")
            ModifierLayer<IAnimation> layer = (ModifierLayer<IAnimation>) stored;

            startAnimation(layer, animationFromType(player));
        }
    }

    private static void startAnimation(ModifierLayer<IAnimation> layer, @Nullable ResourceLocation wanted) {
        KeyframeAnimation target = wanted == null ? null : PlayerAnimationRegistry.getAnimation(wanted);

        IAnimation current = layer.getAnimation();
        KeyframeAnimation playing = current instanceof KeyframeAnimationPlayer keyframes ? keyframes.getData() : null;

        // This tick runs 20 times a second, so it has to be idempotent. The registry hands back
        // the same KeyframeAnimation instance every call and KeyframeAnimationPlayer keeps that
        // reference, so identity is enough to tell whether the right pose is already running -
        // without this check the animation would restart from frame zero every single tick.
        if (playing == target) return;

        layer.replaceAnimationWithFade(
                AbstractFadeModifier.standardFadeIn(FADE_TICKS, Ease.INOUTSINE),
                target == null ? null : new KeyframeAnimationPlayer(target),
                true);
    }

    @Nullable
    private static ResourceLocation animationFromType(AbstractClientPlayer player) {
        if (!CuffLink.isPlayerCuffed(player)) return null;

        return CuffLink.getTypeFromPlayer(player) == CuffType.FRONT ? FRONT : BACK;
    }

    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Opening event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !CuffLink.isPlayerCuffed(player)) return;

        if (event.getNewScreen() instanceof AbstractContainerScreen<?>) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onRenderArm(RenderArmEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || !CuffLink.isPlayerCuffed(player)) return;
        event.setCanceled(true);
    }

}
