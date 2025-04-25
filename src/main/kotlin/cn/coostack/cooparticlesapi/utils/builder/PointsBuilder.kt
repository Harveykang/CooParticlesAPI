package cn.coostack.cooparticlesapi.utils.builder

import cn.coostack.cooparticlesapi.utils.Math3DUtil
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import net.minecraft.util.math.Vec3d

class PointsBuilder {
    companion object {
        @JvmStatic
        fun of(axis: RelativeLocation): PointsBuilder {
            return PointsBuilder().also { it.axis = axis }
        }

        /**
         * 默认对称轴为Y轴
         */
        @JvmStatic
        fun of(points: Collection<RelativeLocation>): PointsBuilder {
            return PointsBuilder().also { it.addPoints(points) }
        }

        @JvmStatic
        fun of(axis: RelativeLocation, points: Collection<RelativeLocation>): PointsBuilder {
            return PointsBuilder().also { it.axis = axis; it.addPoints(points) }
        }

    }

    var axis = RelativeLocation.yAxis()
    private val points = ArrayList<RelativeLocation>()

    fun addPoints(enter: Collection<RelativeLocation>): PointsBuilder {
        points.addAll(enter)
        return this
    }

    fun rotateAsAxis(radius: Double): PointsBuilder {
        Math3DUtil.rotateAsAxis(points, axis, radius)
        return this
    }

    fun rotateTo(to: RelativeLocation): PointsBuilder {
        Math3DUtil.rotatePointsToPoint(points, to, axis)
        return this
    }

    fun rotateTo(to: Vec3d): PointsBuilder {
        Math3DUtil.rotatePointsToPoint(points, RelativeLocation.Companion.of(to), axis)
        return this
    }

    fun rotateTo(origin: RelativeLocation, end: RelativeLocation): PointsBuilder {
        Math3DUtil.rotatePointsToPoint(points, origin.toVector(), end.toVector(), axis)
        return this
    }

    fun rotateTo(origin: Vec3d, end: Vec3d): PointsBuilder {
        Math3DUtil.rotatePointsToPoint(points, origin, end, axis)
        return this
    }


    fun clear(): PointsBuilder {
        points.clear()
        return this
    }


    fun create(): List<RelativeLocation> = points

}