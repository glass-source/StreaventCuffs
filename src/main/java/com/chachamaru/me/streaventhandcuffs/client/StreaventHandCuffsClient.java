package com.chachamaru.me.streaventhandcuffs.client;

import com.chachamaru.me.streaventhandcuffs.Streaventhandcuffs;
import com.chachamaru.me.streaventhandcuffs.client.render.CuffAnimations;
import com.chachamaru.me.streaventhandcuffs.client.render.CuffsLayer;
import com.chachamaru.me.streaventhandcuffs.client.render.HandCuffsModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Streaventhandcuffs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class StreaventHandCuffsClient {

    private StreaventHandCuffsClient() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(CuffAnimations::register);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HandCuffsModel.LAYER_LOCATION, HandCuffsModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        ModelPart root = event.getEntityModels().bakeLayer(HandCuffsModel.LAYER_LOCATION);

        for (String skin : event.getSkins()) {
            EntityRenderer<? extends Player> renderer = event.getPlayerSkin(skin);
            if (renderer instanceof PlayerRenderer playerRenderer) {
                playerRenderer.addLayer(new CuffsLayer(playerRenderer, root));
            }
        }
    }

}
