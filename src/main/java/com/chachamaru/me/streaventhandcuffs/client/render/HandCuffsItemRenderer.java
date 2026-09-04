package com.chachamaru.me.streaventhandcuffs.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Draws the handcuffs as real geometry in hand, the same way vanilla draws the trident: the item
 * model is "builtin/entity", so the item renderer hands off to this and the model below supplies
 * the shape. Reuses {@link HandCuffsModel}, which is the same Blockbench export the player layer
 * renders, so the held item and the worn cuffs cannot drift apart.
 */
public class HandCuffsItemRenderer extends BlockEntityWithoutLevelRenderer {

    // The export is positioned where the cuffs sit on a player's back, so the assembly's centre
    // of mass is well away from its pivot. These shift it back over the origin, which is what the
    // display transforms in models/item/handcuffs.json then rotate around.
    private static final float CENTRE_X = -2.6F / 16.0F;
    private static final float CENTRE_Y = 4.9F / 16.0F;
    private static final float CENTRE_Z = 0.0F;

    private HandCuffsModel<Entity> model;

    public HandCuffsItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                             MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        if (this.model == null) {
            // Baked on first use: layer definitions are not loaded yet when this renderer is
            // constructed. bakeLayer hands back a fresh tree every call, so clearing the root's
            // authored pose here cannot disturb the one CuffsLayer bakes for the player.
            ModelPart root = Minecraft.getInstance().getEntityModels().bakeLayer(HandCuffsModel.LAYER_LOCATION);
            root.getChild("esposas").loadPose(PartPose.ZERO);
            this.model = new HandCuffsModel<>(root);
        }

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);

        // Entity models are authored Y-down; vanilla's BEWLR does exactly this for the trident.
        poseStack.scale(1.0F, -1.0F, -1.0F);
        poseStack.translate(CENTRE_X, CENTRE_Y, CENTRE_Z);

        VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(bufferSource,
                this.model.renderType(HandCuffsModel.TEXTURE), false, stack.hasFoil());
        this.model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }
}
