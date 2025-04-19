package cn.coostack.cooparticlesapi.test.barrier

import cn.coostack.cooparticlesapi.barrages.AbstractBarrage
import cn.coostack.cooparticlesapi.barrages.BarrageHitResult
import cn.coostack.cooparticlesapi.barrages.BarrageOption
import cn.coostack.cooparticlesapi.barrages.HitBox
import cn.coostack.cooparticlesapi.network.particle.ServerParticleGroup
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.explosion.ExplosionBehavior
import java.util.function.Predicate

class SwordBarrage(
    loc: Vec3d,
    world: ServerWorld,
    hitBox: HitBox,
    bindControl: ServerParticleGroup,
    options: BarrageOption,
    val filter: Predicate<LivingEntity>,
    val searchBox: HitBox
) : AbstractBarrage(loc, world, hitBox, bindControl, options) {
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

    override fun onHit(result: BarrageHitResult) {
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