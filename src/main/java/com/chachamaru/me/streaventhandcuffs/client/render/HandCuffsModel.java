package com.chachamaru.me.streaventhandcuffs.client.render;// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.chachamaru.me.streaventhandcuffs.Streaventhandcuffs;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class HandCuffsModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Streaventhandcuffs.id("handcuffs"), "main");
	public static final ResourceLocation TEXTURE = Streaventhandcuffs.id("textures/entity/handcuffs.png");

	private final ModelPart esposas;
	private final ModelPart esposa;
	private final ModelPart bone;
	private final ModelPart bone3;
	private final ModelPart cadena;
	private final ModelPart cadena2;
	private final ModelPart cadena3;
	private final ModelPart cadena4;
	private final ModelPart cadena5;
	private final ModelPart esposa2;
	private final ModelPart bone2;
	private final ModelPart bone4;
	private final ModelPart cadena6;
	private final ModelPart cadena7;
	private final ModelPart cadena8;
	private final ModelPart cadena9;
	private final ModelPart cadena10;

	public HandCuffsModel(ModelPart root) {
		this.esposas = root.getChild("esposas");
		this.esposa = this.esposas.getChild("esposa");
		this.bone = this.esposa.getChild("bone");
		this.bone3 = this.bone.getChild("bone3");
		this.cadena = this.esposa.getChild("cadena");
		this.cadena2 = this.cadena.getChild("cadena2");
		this.cadena3 = this.esposa.getChild("cadena3");
		this.cadena4 = this.cadena3.getChild("cadena4");
		this.cadena5 = this.esposa.getChild("cadena5");
		this.esposa2 = this.esposas.getChild("esposa2");
		this.bone2 = this.esposa2.getChild("bone2");
		this.bone4 = this.bone2.getChild("bone4");
		this.cadena6 = this.esposa2.getChild("cadena6");
		this.cadena7 = this.cadena6.getChild("cadena7");
		this.cadena8 = this.esposa2.getChild("cadena8");
		this.cadena9 = this.cadena8.getChild("cadena9");
		this.cadena10 = this.esposa2.getChild("cadena10");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition esposas = partdefinition.addOrReplaceChild("esposas", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, 11.1393F, 5.7139F, 0.9163F, 0.0F, 0.0F));

		PartDefinition esposa = esposas.addOrReplaceChild("esposa", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.6F, -2.6F, 0.0F, -0.48F, 0.0F, 0.0F));

		PartDefinition bone = esposa.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 5).addBox(-0.616F, -1.144F, -0.572F, 4.576F, 1.144F, 1.716F, new CubeDeformation(0.0F))
		.texOffs(0, 10).addBox(-0.572F, 0.0F, -0.572F, 2.288F, 1.144F, 1.716F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(-1.716F, -5.632F, -0.572F, 1.144F, 4.488F, 1.716F, new CubeDeformation(0.0F))
		.texOffs(11, 12).addBox(3.916F, -5.632F, -0.572F, 1.144F, 4.488F, 1.716F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5722F, 0.0763F, 0.1635F, -1.0656F, 0.276F, -0.1325F));

		PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(11, 12).addBox(5.6892F, -2.2858F, -0.5148F, 1.0296F, 4.2592F, 1.5444F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(1.1F, -3.344F, -0.5148F, 4.576F, 1.144F, 1.6588F, new CubeDeformation(0.0F)), PartPose.offset(-1.716F, -3.432F, 0.0F));

		PartDefinition cadena = esposa.addOrReplaceChild("cadena", CubeListBuilder.create().texOffs(32, 32).addBox(-0.702F, -0.442F, -0.286F, 1.326F, 2.652F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 24).addBox(-0.26F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 25).addBox(-0.26F, 1.768F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 25).addBox(0.182F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 22).addBox(0.182F, 0.0F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 22).addBox(-0.702F, 0.0F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 24).addBox(-0.702F, 0.884F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 23).addBox(0.182F, 0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 20).addBox(-0.26F, -0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.52F, 1.04F, 0.26F, 0.0F, -1.5708F, -0.1745F));

		PartDefinition cadena2 = cadena.addOrReplaceChild("cadena2", CubeListBuilder.create().texOffs(32, 32).addBox(-0.442F, -0.442F, -1.326F, 1.326F, 2.652F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 24).addBox(0.0F, 1.326F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 25).addBox(0.0F, 1.768F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 25).addBox(0.442F, 1.326F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 22).addBox(0.442F, 0.0F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 22).addBox(-0.442F, 0.0F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 24).addBox(-0.442F, 0.884F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 23).addBox(0.442F, 0.442F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 20).addBox(0.0F, -0.442F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.04F, 1.04F, -0.26F, -1.5708F, -1.4399F, 1.5708F));

		PartDefinition cadena3 = esposa.addOrReplaceChild("cadena3", CubeListBuilder.create().texOffs(32, 32).addBox(-0.702F, -0.442F, -0.286F, 1.326F, 2.652F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 24).addBox(-0.26F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 25).addBox(-0.26F, 1.768F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 25).addBox(0.182F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 22).addBox(0.182F, 0.0F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 22).addBox(-0.702F, 0.0F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 24).addBox(-0.702F, 0.884F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 23).addBox(0.182F, 0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 20).addBox(-0.26F, -0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.04F, 3.51F, 0.26F, 0.0F, -1.5708F, -0.8727F));

		PartDefinition cadena4 = cadena3.addOrReplaceChild("cadena4", CubeListBuilder.create().texOffs(32, 32).addBox(-0.442F, -0.442F, -1.326F, 1.326F, 2.652F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 24).addBox(0.0F, 1.326F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 25).addBox(0.0F, 1.768F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 25).addBox(0.442F, 1.326F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 22).addBox(0.442F, 0.0F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 22).addBox(-0.442F, 0.0F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 24).addBox(-0.442F, 0.884F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 23).addBox(0.442F, 0.442F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 20).addBox(0.0F, -0.442F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.04F, 1.17F, -0.26F, -1.5708F, -1.0908F, 1.5708F));

		PartDefinition cadena5 = esposa.addOrReplaceChild("cadena5", CubeListBuilder.create().texOffs(32, 32).addBox(-0.702F, -0.442F, -0.286F, 1.326F, 2.652F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 24).addBox(-0.26F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 25).addBox(-0.26F, 1.768F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 25).addBox(0.182F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 22).addBox(0.182F, 0.0F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 22).addBox(-0.702F, 0.0F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 24).addBox(-0.702F, 0.884F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(11, 23).addBox(0.182F, 0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 20).addBox(-0.26F, -0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.64F, 4.55F, 0.26F, 0.0F, -1.5708F, -1.4399F));

		PartDefinition esposa2 = esposas.addOrReplaceChild("esposa2", CubeListBuilder.create(), PartPose.offsetAndRotation(7.8F, -2.6F, 0.0F, -0.48F, 0.0F, 0.0F));

		PartDefinition bone2 = esposa2.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 5).mirror().addBox(-3.96F, -1.144F, -0.572F, 4.576F, 1.144F, 1.716F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 10).mirror().addBox(-1.716F, 0.0F, -0.572F, 2.288F, 1.144F, 1.716F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 15).mirror().addBox(0.572F, -5.632F, -0.572F, 1.144F, 4.488F, 1.716F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 12).mirror().addBox(-5.06F, -5.632F, -0.572F, 1.144F, 4.488F, 1.716F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.8787F, 0.0823F, 0.1765F, -1.0565F, -0.1974F, 0.0934F));

		PartDefinition bone4 = bone2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(11, 12).mirror().addBox(-6.7188F, -0.0858F, -0.5148F, 1.0296F, 2.0592F, 1.5444F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-5.676F, -3.344F, -0.5148F, 4.576F, 1.144F, 1.6588F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.716F, -3.432F, 0.0F));

		PartDefinition cadena6 = esposa2.addOrReplaceChild("cadena6", CubeListBuilder.create().texOffs(32, 32).addBox(-0.624F, -0.442F, -0.286F, 1.326F, 2.652F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 24).mirror().addBox(-0.182F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 25).mirror().addBox(-0.182F, 1.768F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 25).mirror().addBox(-0.624F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 22).mirror().addBox(-0.624F, 0.0F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 22).mirror().addBox(0.26F, 0.0F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 24).mirror().addBox(0.26F, 0.884F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 23).mirror().addBox(-0.624F, 0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 20).mirror().addBox(-0.182F, -0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.52F, 1.04F, 0.26F, 0.0F, 1.5708F, 0.1745F));

		PartDefinition cadena7 = cadena6.addOrReplaceChild("cadena7", CubeListBuilder.create().texOffs(32, 32).addBox(-0.884F, -0.442F, -1.326F, 1.326F, 2.652F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 24).mirror().addBox(-0.442F, 1.326F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 25).mirror().addBox(-0.442F, 1.768F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 25).mirror().addBox(-0.884F, 1.326F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 22).mirror().addBox(-0.884F, 0.0F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 22).mirror().addBox(0.0F, 0.0F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 24).mirror().addBox(0.0F, 0.884F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 23).mirror().addBox(-0.884F, 0.442F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 20).mirror().addBox(-0.442F, -0.442F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.04F, 1.04F, -0.26F, -1.5708F, 1.4399F, -1.5708F));

		PartDefinition cadena8 = esposa2.addOrReplaceChild("cadena8", CubeListBuilder.create().texOffs(32, 32).addBox(-0.624F, -0.442F, -0.286F, 1.326F, 2.652F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 24).mirror().addBox(-0.182F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 25).mirror().addBox(-0.182F, 1.768F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 25).mirror().addBox(-0.624F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 22).mirror().addBox(-0.624F, 0.0F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 22).mirror().addBox(0.26F, 0.0F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 24).mirror().addBox(0.26F, 0.884F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 23).mirror().addBox(-0.624F, 0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 20).mirror().addBox(-0.182F, -0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.04F, 3.51F, 0.26F, 0.0F, 1.5708F, 0.8727F));

		PartDefinition cadena9 = cadena8.addOrReplaceChild("cadena9", CubeListBuilder.create().texOffs(32, 32).addBox(-0.884F, -0.442F, -1.326F, 1.326F, 2.652F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 24).mirror().addBox(-0.442F, 1.326F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 25).mirror().addBox(-0.442F, 1.768F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 25).mirror().addBox(-0.884F, 1.326F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 22).mirror().addBox(-0.884F, 0.0F, -1.326F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 22).mirror().addBox(0.0F, 0.0F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 24).mirror().addBox(0.0F, 0.884F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 23).mirror().addBox(-0.884F, 0.442F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 20).mirror().addBox(-0.442F, -0.442F, -1.326F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.04F, 1.17F, -0.26F, -1.5708F, 1.0908F, -1.5708F));

		PartDefinition cadena10 = esposa2.addOrReplaceChild("cadena10", CubeListBuilder.create().texOffs(32, 32).addBox(-0.624F, -0.442F, -0.286F, 1.326F, 2.652F, 0.5525F, new CubeDeformation(0.0F))
		.texOffs(12, 24).mirror().addBox(-0.182F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 25).mirror().addBox(-0.182F, 1.768F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 25).mirror().addBox(-0.624F, 1.326F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 22).mirror().addBox(-0.624F, 0.0F, -0.286F, 0.442F, 0.442F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 22).mirror().addBox(0.26F, 0.0F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 24).mirror().addBox(0.26F, 0.884F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(11, 23).mirror().addBox(-0.624F, 0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 20).mirror().addBox(-0.182F, -0.442F, -0.286F, 0.442F, 0.884F, 0.5525F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.64F, 4.55F, 0.26F, 0.0F, 1.5708F, 1.4399F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		esposas.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}