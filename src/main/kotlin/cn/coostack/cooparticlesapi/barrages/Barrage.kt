package cn.coostack.cooparticlesapi.barrages

import cn.coostack.cooparticlesapi.network.particle.ServerParticleGroup
import net.minecraft.entity.LivingEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d

interface Barrage {
    var loc: Vec3d
    val world: ServerWorld
    var hitBox: HitBox
    var shooter: LivingEntity?
    var direction: Vec3d
    var lunch: Boolean
    val valid: Boolean
    val options: BarrageOption

    /**
     * 设置bindControl 会每tick都会设置 loc (teleport)
     */
    val bindControl: ServerParticleGroup

    /**
     * @param result 击中的目标
     */
    fun onHit(result: BarrageHitResult)


    fun tick()

}