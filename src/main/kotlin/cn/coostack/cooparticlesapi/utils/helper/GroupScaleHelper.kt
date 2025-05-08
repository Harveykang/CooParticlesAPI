package cn.coostack.cooparticlesapi.utils.helper

import cn.coostack.cooparticlesapi.particles.Controlable
import cn.coostack.cooparticlesapi.particles.control.group.ControlableParticleGroup

/**
 * 从最小scale 到 最大scale
 * @param scaleTick 从minScale 到 maxScale需要的tick
 */
class GroupScaleHelper(minScale: Double, maxScale: Double, scaleTick: Int) :
    ScaleHelper(minScale, maxScale, scaleTick) {
    lateinit var group: ControlableParticleGroup
    override fun loadControler(controler: Controlable<*>) {
        if (controler !is ControlableParticleGroup) {
            return
        }
        group = controler
        group.scale(minScale)
    }

    override fun getLoadedGroup(): Controlable<*>? {
        if (!::group.isInitialized) {
            return null
        }
        return group
    }

    override fun getGroupScale(): Double {
        return group.scale
    }

    override fun scale(scale: Double) {
        group.scale(scale.coerceIn(minScale, maxScale))
    }
}