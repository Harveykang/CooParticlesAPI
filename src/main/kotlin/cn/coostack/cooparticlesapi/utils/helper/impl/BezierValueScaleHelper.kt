package cn.coostack.cooparticlesapi.utils.helper.impl

import cn.coostack.cooparticlesapi.network.particle.style.ParticleGroupStyle
import cn.coostack.cooparticlesapi.particles.Controlable
import cn.coostack.cooparticlesapi.utils.Math3DUtil
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import cn.coostack.cooparticlesapi.utils.helper.ScaleHelper

abstract class BezierValueScaleHelper(
    scaleTick: Int,
    private val controlPoint1: RelativeLocation,
    private val controlPoint2: RelativeLocation
) :
    ScaleHelper(controlPoint1.x, controlPoint2.y, scaleTick) {
    private val deltaScale = maxScale - minScale
    override fun doScale() {
        if (getLoadedGroup() == null) return

        val t = current.toDouble() / scaleTick
        val curveValue = Math3DUtil.cubicBezier(
            t,
            0.0,
            controlPoint1.y,
            controlPoint2.y,
            1.0
        )

        scale(minScale + curveValue * deltaScale)
    }

}