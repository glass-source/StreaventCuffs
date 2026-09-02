package com.chachamaru.me.streaventhandcuffs.client.render;

import com.chachamaru.me.streaventhandcuffs.cuffs.CuffLink;
import com.chachamaru.me.streaventhandcuffs.cuffs.CuffType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class CuffsLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private final ModelPart cuffs;

    public CuffsLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent,
                      ModelPart cuffs) {
        super(parent);
        this.cuffs = cuffs;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       AbstractClientPlayer player, float p_117353_, float p_117354_,
                       float p_117355_, float p_117356_, float p_117357_, float p_117358_) {

        if (!CuffLink.isPlayerCuffed(player)) return;

        poseStack.pushPose();

        if (CuffLink.getTypeFromPlayer(player) == CuffType.FRONT) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        }

        this.cuffs.render(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(HandCuffsModel.TEXTURE)),
                packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();

    }
}
