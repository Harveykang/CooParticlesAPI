package cn.coostack.cooparticlesapi.test.entity

import cn.coostack.cooparticlesapi.CooParticleAPI
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.util.Identifier

object CooParticlesEntityLayers {

    @JvmStatic
    val TEST_ENTITY_LAYER = EntityModelLayer(
        Identifier.of(CooParticleAPI.MOD_ID,"test_entity"),"main"
    )

    @JvmStatic
    val TEST_PLAYER_ENTITY_LAYER = EntityModelLayer(
        Identifier.of(CooParticleAPI.MOD_ID,"test_player_entity"),"main"
    )

}