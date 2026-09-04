package com.chachamaru.me.streaventhandcuffs.cuffs;

import com.chachamaru.me.streaventhandcuffs.Streaventhandcuffs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Streaventhandcuffs.MODID)
public final class CuffRope {

    private CuffRope() {}

    private static final double ALLOWED_DISTANCE = 6.0D;
    private static final double PULL_PER_BLOCK = 0.15D;
    private static final double MAX_PULL = 0.6D;
    private static final double MAX_SPEED = 0.8D;
    private static final double MAX_UPWARD_SPEED = 0.3D;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        MinecraftServer server = event.getServer();
        Set<UUID> handled = new HashSet<>();

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (!handled.add(player.getUUID())) continue;

            ServerPlayer partner = CuffLink.getPartner(player);
            if (partner == null) continue;

            handled.add(partner.getUUID());
            tick(player, partner);
        }
    }

    private static void tick(ServerPlayer playerA, ServerPlayer playerB) {
        if (playerA.level() != playerB.level()) return;
        if (playerA.isSpectator() || playerB.isSpectator()) return;

        double distance = playerA.position().distanceTo(playerB.position());
        if (distance <= ALLOWED_DISTANCE) return;

        Vec3 vectorToPartner = playerB.position().subtract(playerA.position()).normalize();
        double pull = Math.min((distance - ALLOWED_DISTANCE) * PULL_PER_BLOCK, MAX_PULL);

        Vec3 pulse = new Vec3(vectorToPartner.x * pull,
                vectorToPartner.y * pull, vectorToPartner.z * pull);

        push(playerA, pulse);
        push(playerB, pulse.reverse());
    }

    private static void push(ServerPlayer player, Vec3 delta) {
        Vec3 movement = player.getDeltaMovement().add(delta);

        double horizontal = Math.sqrt(movement.x * movement.x + movement.z * movement.z);
        if (horizontal > MAX_SPEED) {
            double scale = MAX_SPEED / horizontal;
            movement = new Vec3(movement.x * scale, movement.y, movement.z * scale);
        }

        if (movement.y > MAX_UPWARD_SPEED) {
            movement = new Vec3(movement.x, MAX_UPWARD_SPEED, movement.z);
        }

        player.setDeltaMovement(movement);
        player.hurtMarked = true;
    }

}
