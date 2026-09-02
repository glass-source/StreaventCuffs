package com.chachamaru.me.streaventhandcuffs.item;

import com.chachamaru.me.streaventhandcuffs.client.render.HandCuffsItemRenderer;
import com.chachamaru.me.streaventhandcuffs.cuffs.CuffLink;
import com.chachamaru.me.streaventhandcuffs.cuffs.CuffType;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class HandCuffsItem extends Item {

    public HandCuffsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {

        if (player.level().isClientSide) {
            return player instanceof Player ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        if (!(player instanceof ServerPlayer serverPlayer) || !(livingEntity instanceof ServerPlayer serverPlayer1)) {
            return InteractionResult.PASS;
        }

        if (serverPlayer == serverPlayer1) return InteractionResult.FAIL;

        if (CuffLink.isBound(itemStack)) return InteractionResult.FAIL;

        // Cuffing someone who is already cuffed used to run the whole bind again, leaving the
        // previous bound stacks behind in their inventories. enforceOffHand could then promote a
        // stale one back into an off-hand, leaving the two ends of the link on different types.
        if (CuffLink.isPlayerCuffed(serverPlayer) || CuffLink.isPlayerCuffed(serverPlayer1)) {
            return InteractionResult.FAIL;
        }

        // isCrouching() is pose based, and the pose is only recomputed once per tick in
        // Player.tick(), so here it still holds last tick's value. updatePlayerPose() also never
        // sets Pose.CROUCHING while flying, so in creative it stays false however hard you hold
        // shift. handleInteract sets the shift flag straight from the interaction packet
        // immediately before this runs, so that is the state the player actually clicked in.
        CuffType type = serverPlayer.isShiftKeyDown() ? CuffType.BACK : CuffType.FRONT;
        CuffLink.bindPlayers(serverPlayer, serverPlayer1, type);
        itemStack.setCount(0);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        return !CuffLink.isBound(item);
    }

    /**
     * Only ever called on the client, so the client-only types below are never loaded server side.
     * The renderer is built lazily because it touches Minecraft.getInstance().
     */
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            private HandCuffsItemRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new HandCuffsItemRenderer();
                }
                return renderer;
            }
        });
    }



}
