package cn.coostack.cooparticlesapi.utils.helper.emitters

import net.minecraft.util.math.Vec3d

object LinearResistanceHelper {
    fun setPercentageVelocity(enter: Vec3d, precent: Double): Vec3d {
        return enter.multiply(precent)
    }
}