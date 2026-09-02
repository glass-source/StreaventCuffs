package com.chachamaru.me.streaventhandcuffs.client.render;

import com.chachamaru.me.streaventhandcuffs.Streaventhandcuffs;
import com.chachamaru.me.streaventhandcuffs.cuffs.CuffLink;
import com.chachamaru.me.streaventhandcuffs.cuffs.CuffType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Draws a rope between two cuffed players, using the same geometry vanilla builds for a leash in
 * MobRenderer#renderLeash. That method and its addVertexPair helper are private, so the maths is
 * reproduced here. It differs from vanilla in two ways: the rope is grey rather than brown, and
 * both ends are anchored at the players' cuffs rather than at a mob's chest.
 */
@Mod.EventBusSubscriber(modid = Streaventhandcuffs.MODID, value = Dist.CLIENT)
public final class CuffRopeRenderer {

    private CuffRopeRenderer() {}

    /** Grey. Vanilla's leash uses 0.5 / 0.4 / 0.3 here, which is what makes it look like rope. */
    private static final float ROPE_SHADE = 0.5F;

    /** Rope thickness and segment count, straight from vanilla. */
    private static final float ROPE_WIDTH = 0.025F;
    private static final int SEGMENTS = 24;

    /** Pose of the "esposas" root in HandCuffsModel#createBodyLayer, in model units. */
    private static final float CUFFS_MODEL_X = -2.0F;
    private static final float CUFFS_MODEL_Y = 11.1393F;
    private static final float CUFFS_MODEL_Z = 5.7139F;

    /** PlayerRenderer#scale. */
    private static final float PLAYER_MODEL_SCALE = 0.9375F;

    /** LivingEntityRenderer#render drops the model by this much before drawing it. */
    private static final float MODEL_Y_ORIGIN = 1.501F;

    /**
     * Both ends of a link fire RenderPlayerEvent, so without this the rope would be built twice.
     * Claiming both players when either one draws also keeps the rope visible when only one end is
     * rendered - the normal case for your own cuffs in first person, where the local player's model
     * is never drawn at all.
     */
    private static final Set<UUID> ROPED_THIS_FRAME = new HashSet<>();

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        if (!CuffLink.isPlayerCuffed(player)) return;

        UUID partnerId = CuffLink.getPartnerUUID(player);
        if (partnerId == null) return;

        Player partner = player.level().getPlayerByUUID(partnerId);
        if (partner == null || partner == player) return;

        if (!ROPED_THIS_FRAME.add(player.getUUID())) return;
        ROPED_THIS_FRAME.add(partnerId);

        renderRope(player, partner, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource());
    }

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            ROPED_THIS_FRAME.clear();
        }
    }

    private static void renderRope(Player from, Player to, float partialTick,
                                   PoseStack poseStack, MultiBufferSource bufferSource) {
        poseStack.pushPose();

        // Each end is anchored at its own player's cuffs, using that player's own body rotation
        // and cuff type - the two ends of a link are not necessarily facing the same way.
        Vec3 fromLocal = cuffAnchorOffset(from, Mth.rotLerp(partialTick, from.yBodyRotO, from.yBodyRot));
        Vec3 toLocal = cuffAnchorOffset(to, Mth.rotLerp(partialTick, to.yBodyRotO, to.yBodyRot));

        Vec3 fromAnchor = anchorPosition(from, fromLocal, partialTick);
        Vec3 toAnchor = anchorPosition(to, toLocal, partialTick);

        // The pose stack sits at the rendered player's position, so walk out to their cuffs first.
        poseStack.translate(fromLocal.x, fromLocal.y, fromLocal.z);

        float spanX = (float) (toAnchor.x - fromAnchor.x);
        float spanY = (float) (toAnchor.y - fromAnchor.y);
        float spanZ = (float) (toAnchor.z - fromAnchor.z);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.leash());
        Matrix4f pose = poseStack.last().pose();

        float normal = Mth.invSqrt(spanX * spanX + spanZ * spanZ) * ROPE_WIDTH / 2.0F;
        float normalZ = spanZ * normal;
        float normalX = spanX * normal;

        // Light is sampled at each end and interpolated along the rope. Vanilla samples at the
        // entities' eyes; sampling at the cuffs matches where the rope actually hangs.
        // EntityRenderer#getBlockLightLevel is protected, so its body is inlined here.
        BlockPos fromPos = BlockPos.containing(fromAnchor);
        BlockPos toPos = BlockPos.containing(toAnchor);

        int fromBlockLight = from.isOnFire() ? 15 : from.level().getBrightness(LightLayer.BLOCK, fromPos);
        int toBlockLight = to.isOnFire() ? 15 : to.level().getBrightness(LightLayer.BLOCK, toPos);
        int fromSkyLight = from.level().getBrightness(LightLayer.SKY, fromPos);
        int toSkyLight = to.level().getBrightness(LightLayer.SKY, toPos);

        for (int i = 0; i <= SEGMENTS; ++i) {
            addVertexPair(consumer, pose, spanX, spanY, spanZ, fromBlockLight, toBlockLight,
                    fromSkyLight, toSkyLight, ROPE_WIDTH, ROPE_WIDTH, normalZ, normalX, i, false);
        }

        for (int i = SEGMENTS; i >= 0; --i) {
            addVertexPair(consumer, pose, spanX, spanY, spanZ, fromBlockLight, toBlockLight,
                    fromSkyLight, toSkyLight, ROPE_WIDTH, 0.0F, normalZ, normalX, i, true);
        }

        poseStack.popPose();
    }

    /**
     * Where this player's cuffs sit, as an offset from their feet, already turned to face the way
     * their body is facing. Same frame vanilla's Entity#getLeashOffset works in: blocks, Y up,
     * +X to the player's left, +Z the way they face.
     * <p>
     * The cuffs are authored in model space - 1/16 blocks, Y down, +Z behind the player. Unwinding
     * what LivingEntityRenderer#render does to get there (setupRotations, then scale(-1, -1, 1),
     * then PlayerRenderer#scale, then translate(0, -1.501, 0)) leaves
     * x = s * mx/16, y = s * (1.501 - my/16), z = -s * mz/16.
     * <p>
     * CuffsLayer renders front cuffs by spinning the assembly a half turn about the player's own
     * vertical axis, which negates the model's x and z, so this anchor mirrors along with it.
     */
    private static Vec3 cuffAnchorOffset(Player player, float bodyYaw) {
        boolean front = CuffLink.getTypeFromPlayer(player) == CuffType.FRONT;

        float modelX = front ? -CUFFS_MODEL_X : CUFFS_MODEL_X;
        float modelZ = front ? -CUFFS_MODEL_Z : CUFFS_MODEL_Z;

        double x = PLAYER_MODEL_SCALE * modelX / 16.0D;
        double y = PLAYER_MODEL_SCALE * (MODEL_Y_ORIGIN - CUFFS_MODEL_Y / 16.0D);
        double z = -PLAYER_MODEL_SCALE * modelZ / 16.0D;

        // Same rotation vanilla applies to the leash offset.
        double angle = (double) (bodyYaw * ((float) Math.PI / 180F)) + (Math.PI / 2D);

        return new Vec3(
                Math.cos(angle) * z + Math.sin(angle) * x,
                y,
                Math.sin(angle) * z - Math.cos(angle) * x);
    }

    private static Vec3 anchorPosition(Player player, Vec3 offset, float partialTick) {
        return new Vec3(
                Mth.lerp((double) partialTick, player.xo, player.getX()) + offset.x,
                Mth.lerp((double) partialTick, player.yo, player.getY()) + offset.y,
                Mth.lerp((double) partialTick, player.zo, player.getZ()) + offset.z);
    }

    private static void addVertexPair(VertexConsumer consumer, Matrix4f pose,
                                      float spanX, float spanY, float spanZ,
                                      int fromBlockLight, int toBlockLight,
                                      int fromSkyLight, int toSkyLight,
                                      float width, float yOffset,
                                      float normalZ, float normalX,
                                      int segment, boolean back) {

        float t = (float) segment / (float) SEGMENTS;

        int blockLight = (int) Mth.lerp(t, (float) fromBlockLight, (float) toBlockLight);
        int skyLight = (int) Mth.lerp(t, (float) fromSkyLight, (float) toSkyLight);
        int packedLight = LightTexture.pack(blockLight, skyLight);

        // Alternating segments are darkened, which is what gives the rope its braided look.
        float shade = segment % 2 == (back ? 1 : 0) ? 0.7F : 1.0F;
        float grey = ROPE_SHADE * shade;

        float x = spanX * t;
        // Quadratic sag, so the rope hangs rather than running straight between the two anchors.
        float y = spanY > 0.0F ? spanY * t * t : spanY - spanY * (1.0F - t) * (1.0F - t);
        float z = spanZ * t;

        consumer.vertex(pose, x - normalZ, y + yOffset, z + normalX)
                .color(grey, grey, grey, 1.0F).uv2(packedLight).endVertex();
        consumer.vertex(pose, x + normalZ, y + width - yOffset, z - normalX)
                .color(grey, grey, grey, 1.0F).uv2(packedLight).endVertex();
    }
}
