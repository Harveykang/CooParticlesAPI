package cn.coostack.cooparticlesapi.particles

import cn.coostack.cooparticlesapi.utils.RelativeLocation
import net.minecraft.util.math.Vec3d

/**
 * T 控制器对象
 */
interface Controlable<T> {
    fun rotateParticlesToPoint(to: RelativeLocation)
    fun rotateToWithAngle(to: RelativeLocation, angle: Double)
    fun rotateParticlesAsAxis(angle: Double)

    fun teleportTo(pos: Vec3d)
    fun teleportTo(x: Double, y: Double, z: Double)

    fun remove()

    fun getControlObject(): T
}