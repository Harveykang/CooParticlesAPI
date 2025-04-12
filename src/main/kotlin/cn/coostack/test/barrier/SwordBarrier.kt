package cn.coostack.test.barrier

import cn.coostack.barriers.AbstractBarrier
import cn.coostack.barriers.BarrierHitResult
import cn.coostack.barriers.BarrierOption
import cn.coostack.barriers.HitBox
import cn.coostack.network.particle.ServerParticleGroup
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSources
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.explosion.ExplosionBehavior
import java.util.function.Predicate

class SwordBarrier(
    loc: Vec3d,
    world: ServerWorld,
    hitBox: HitBox,
    bindControl: ServerParticleGroup,
    options: BarrierOption,
    val filter: Predicate<LivingEntity>,
    val searchBox: HitBox
) : AbstractBarrier(loc, world, hitBox, bindControl, options) {
    override fun filterHitEntity(livingEntity: LivingEntity): Boolean {
        return filter.test(livingEntity)
    }

    override fun tick() {
        super.tick()
        val entities = world.getEntitiesByClass(LivingEntity::class.java, searchBox.ofBox(loc), filter)
        var closestEntity: LivingEntity? = null

        for (entity in entities) {
            if (closestEntity == null) {
                closestEntity = entity
                continue
            }
            if (loc.distanceTo(closestEntity.pos) > loc.distanceTo(entity.pos)) {
                closestEntity = entity
            }
        }
        if (closestEntity == null) {
            return
        }
        direction = loc.relativize(closestEntity.pos).normalize().multiply(0.5)
    }

    override fun onHit(result: BarrierHitResult) {
        val hitBlock = result.hitBlockState
        if (hitBlock != null) {
            handleBlock(hitBlock)
        }

        if (result.entities.isNotEmpty()) {
            handleEntities(result.entities)
        }

    }

    private fun handleBlock(hit: BlockState) {
        world.createExplosion(
            shooter,
            shooter?.recentDamageSource,
            ExplosionBehavior(),
            loc.x,
            loc.y,
            loc.z,
            3f,
            false,
            World.ExplosionSourceType.TRIGGER
        )
    }

    private fun handleEntities(entities: List<LivingEntity>) {
        val attackAmount = 10f
        entities.forEach { entity ->
            entity.attacker = shooter
            if (shooter != null) {
                val sources = if (shooter is PlayerEntity) {
                    entity.damageSources.playerAttack(shooter as PlayerEntity)
                } else {
                    entity.damageSources.mobAttack(shooter)
                }
                entity.damage(sources, attackAmount)
            } else {
                entity.damage(
                    world.damageSources.generic(), attackAmount
                )
            }
        }
        world.createExplosion(
            shooter,
            shooter?.recentDamageSource,
            ExplosionBehavior(),
            loc.x,
            loc.y,
            loc.z,
            3f,
            false,
            World.ExplosionSourceType.TRIGGER
        )
    }
}