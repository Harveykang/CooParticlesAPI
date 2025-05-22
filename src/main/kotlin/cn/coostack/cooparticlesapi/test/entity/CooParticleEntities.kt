package cn.coostack.cooparticlesapi.test.entity

import cn.coostack.cooparticlesapi.CooParticleAPI
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object CooParticleEntities {
    @JvmStatic
    val TEST_ENTITY = Registry.register(
        Registries.ENTITY_TYPE, Identifier.of(
            CooParticleAPI.MOD_ID, "test_entity"
        ),
        EntityType.Builder.create(::TestEntity, SpawnGroup.CREATURE)
            .dimensions(
                1f, 1f
            ).build()
    )

    @JvmStatic
    val TEST_PLAYER_ENTITY = Registry.register(
        Registries.ENTITY_TYPE, Identifier.of(
            CooParticleAPI.MOD_ID, "test_player_entity"
        ),
        EntityType.Builder.create(::TestPlayerEntity, SpawnGroup.CREATURE)
            .dimensions(
                1f, 2f
            ).build()
    )


    fun init() {

    }

}