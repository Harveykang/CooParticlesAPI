package cn.coostack.cooparticlesapi.test.entity

import cn.coostack.cooparticlesapi.CooParticleAPI
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

class TestPlayerEntityRenderer(ctx: EntityRendererFactory.Context) :
    MobEntityRenderer<TestPlayerEntity, TestPlayerModel>(
        ctx, TestPlayerModel(
            ctx.getPart(CooParticlesEntityLayers.TEST_PLAYER_ENTITY_LAYER)
        ), 0.5f
    ) {

    companion object {
        @JvmStatic
        val TEXTURE = Identifier.of(CooParticleAPI.MOD_ID, "textures/entity/test_player_entity.png")
    }

    override fun getTexture(entity: TestPlayerEntity): Identifier {
        return TEXTURE
    }

    override fun render(
        entity: TestPlayerEntity,
        yaw: Float,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int
    ) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
    }
}