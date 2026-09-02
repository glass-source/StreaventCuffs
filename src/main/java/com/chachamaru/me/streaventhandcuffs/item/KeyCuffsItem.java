package com.chachamaru.me.streaventhandcuffs.item;

import com.chachamaru.me.streaventhandcuffs.cuffs.CuffLink;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class KeyCuffsItem extends Item {

    public KeyCuffsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity livingEntity, InteractionHand hand) {

        if (player.level().isClientSide) {
            return player instanceof Player ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        if (!(player instanceof ServerPlayer) || !(livingEntity instanceof ServerPlayer serverPlayer1)) {
            return InteractionResult.PASS;
        }

        if (!CuffLink.isPlayerCuffed(serverPlayer1)) {
            return InteractionResult.FAIL;
        }

        CuffLink.releasePlayers(serverPlayer1);

        return InteractionResult.CONSUME;
    }
}
