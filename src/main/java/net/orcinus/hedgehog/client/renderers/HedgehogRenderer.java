package net.orcinus.hedgehog.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.client.models.HedgehogModel;
import net.orcinus.hedgehog.client.models.HedgehogScaredModel;
import net.orcinus.hedgehog.client.renderers.layers.HedgehogClothLayer;
import net.orcinus.hedgehog.entities.HedgehogEntity;
import net.orcinus.hedgehog.init.HModelLayers;

@OnlyIn(Dist.CLIENT)
public class HedgehogRenderer extends MobRenderer<HedgehogEntity, EntityModel<HedgehogEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Hedgehog.MODID, "textures/entity/hedgehog.png");
    public static final ResourceLocation SCARED_TEXTURE = new ResourceLocation(Hedgehog.MODID, "textures/entity/scared_hedgehog.png");
    public static final ResourceLocation SPEED_CONSUMER = new ResourceLocation(Hedgehog.MODID, "textures/entity/speed_consumer_hedgehog.png");
    public static final ResourceLocation SCARED_SPEED_CONSUMER = new ResourceLocation(Hedgehog.MODID, "textures/entity/scared_speed_consumer_hedgehog.png");
    private final EntityModel<HedgehogEntity> scared;
    private final EntityModel<HedgehogEntity> normal = this.getModel();

    public HedgehogRenderer(EntityRendererProvider.Context context) {
        super(context, new HedgehogModel<>(context.bakeLayer(HModelLayers.HEDGEHOG)), 0.4F);
        this.scared = new HedgehogScaredModel<>(context.bakeLayer(HModelLayers.HEDGEHOG_SCARED));
        this.addLayer(new HedgehogClothLayer(this, context.getModelSet()));
    }

    @Override
    public void render(HedgehogEntity entity, float p_115456_, float p_115457_, PoseStack stack, MultiBufferSource source, int packedLight) {
        if (entity.getScaredTicks() > 0) {
            this.model = this.scared;
        } else {
            this.model = this.normal;
        }
        super.render(entity, p_115456_, p_115457_, stack, source, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(HedgehogEntity entity) {
        String s = ChatFormatting.stripFormatting(entity.getName().getString());
        if ("SpeedBoy".equals(s)) {
            return entity.getScaredTicks() > 0 ? SCARED_SPEED_CONSUMER : SPEED_CONSUMER;
        } else {
            return entity.getScaredTicks() > 0 ? SCARED_TEXTURE : TEXTURE;
        }
    }
}
