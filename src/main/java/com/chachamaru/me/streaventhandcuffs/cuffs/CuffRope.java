package com.chachamaru.me.streaventhandcuffs.cuffs;

import com.chachamaru.me.streaventhandcuffs.Streaventhandcuffs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Streaventhandcuffs.MODID)
public final class CuffRope {

    private CuffRope() {}

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        MinecraftServer server = event.getServer();

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            ServerPlayer partner = CuffLink.getPartner(player);
            if (partner == null) continue;
            tick(player, partner);
        }
    }

    private static void tick(ServerPlayer playerA, ServerPlayer playerB) {
        if (playerA.level() != playerB.level()) return;
        if (playerA.isSpectator() || playerB.isSpectator()) return;

        double allowedDistance = 6.0D;
        double distance = playerA.position().distanceTo(playerB.position());
        if (distance <= allowedDistance) return;

        Vec3 vectorToPartner = playerB.position().subtract(playerA.position()).normalize();
        double pull = Math.min((distance - allowedDistance) * 0.15D, 0.6D);

        Vec3 pulse = new Vec3(vectorToPartner.x * pull,
                vectorToPartner.y * pull, vectorToPartner.z * pull);

        push(playerA, pulse);
        push(playerB, pulse.reverse());

    }

    private static void push(ServerPlayer player, Vec3 delta) {
        player.setDeltaMovement(player.getDeltaMovement().add(delta));
        player.hurtMarked = true;
    }

}
