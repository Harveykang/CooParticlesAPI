package cn.coostack.barriers

import cn.coostack.network.particle.ServerParticleGroup
import com.google.common.base.Predicate
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import kotlin.math.max

abstract class AbstractBarrier(
    override var loc: Vec3d,
    override val world: ServerWorld,
    override var hitBox: HitBox,
    override val bindControl: ServerParticleGroup,
    override val options: BarrierOption,
) : Barrier {
    override var shooter: LivingEntity? = null
    override var direction: Vec3d = Vec3d.ZERO
    override var lunch: Boolean = false
    private var currentTick = 0
    private var spawnTick = 0
    internal var isValid = true
    override val valid: Boolean
        get() = isValid

    /**
     * 当获取到hitBox有实体时，可以对实体进行过滤
     */
    abstract fun filterHitEntity(livingEntity: LivingEntity): Boolean

    override fun tick() {
        if (!lunch || !valid) {
            return
        }

        // 判定速度
        if (options.enableSpeed) {
            loc = loc.add(direction.normalize().multiply(options.speed))
            options.speed += options.acceleration
            // 判定加速度最大值设定
            if (options.accelerationMaxSpeedEnabled) {
                options.speed = max(options.accelerationMaxSpeed, options.speed)
            }
        } else {
            loc = loc.add(direction)
        }

        bindControl.teleportGroupTo(loc)
        // 判断击中
        if (options.maxLivingTick != -1) {
            if (currentTick++ > options.maxLivingTick) {
                hit(BarrierHitResult())
                return
            }
        }
        val blockPos = BlockPos.ofFloored(loc)
        val block = world.getBlockState(blockPos)
        val result = BarrierHitResult()
        if (!block.isAir) {
            if (block.isLiquid && !options.acrossLiquid) {
                result.hitBlockState = block
                hit(result)
            } else if (!options.acrossBlock) {
                result.hitBlockState = block
                hit(result)
            }
        }

        if (spawnTick < options.noneHitBoxTick) {
            spawnTick++
            return
        }
        val collection = hitBoxEntities().filter {
            return@filter filterHitEntity(it)
        }
        if (collection.isNotEmpty()) {
            result.entities.addAll(collection)
            hit(result)
        }
    }

    private fun hit(result: BarrierHitResult) {
        onHit(result)
        remove()
    }

    fun remove() {
        bindControl.kill()
        isValid = false
    }

    fun hitBoxEntities(): Set<LivingEntity> {
        val res = HashSet<LivingEntity>()
        res.addAll(world.getEntitiesByClass(LivingEntity::class.java, hitBox.ofBox(loc), { true }))
        return res
    }

    fun hitBoxEntities(filter: Predicate<LivingEntity>): Set<LivingEntity> {
        val res = HashSet<LivingEntity>()
        res.addAll(world.getEntitiesByClass(LivingEntity::class.java, hitBox.ofBox(loc), filter))
        return res
    }

}