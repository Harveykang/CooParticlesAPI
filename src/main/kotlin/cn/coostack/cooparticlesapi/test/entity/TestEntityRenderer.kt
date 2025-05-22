package cn.coostack.cooparticlesapi.test.entity

import cn.coostack.cooparticlesapi.CooParticleAPI
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

class TestEntityRenderer(context: EntityRendererFactory.Context) :
    MobEntityRenderer<TestEntity, TestEntityModel<TestEntity>>(
        context, TestEntityModel(
            context.getPart(CooParticlesEntityLayers.TEST_ENTITY_LAYER)
        ), 0.5f
    ) {
    companion object {
        @JvmStatic
        val TEXTURE = Identifier.of(CooParticleAPI.MOD_ID, "textures/entity/test_entity.png")
    }

    override fun getTexture(entity: TestEntity): Identifier {
        return TEXTURE
    }

    override fun render(
        livingEntity: TestEntity,
        f: Float,
        g: Float,
        matrixStack: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        i: Int
    ) {
        if (livingEntity.isBaby) {
            matrixStack.scale(0.5f, 0.5f, 0.5f)
        }
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i)
    }
}