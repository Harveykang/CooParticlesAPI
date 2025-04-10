package cn.coostack.test.util

import net.minecraft.util.math.Vec3d
import org.joml.Vector3f
import java.util.ArrayList
import kotlin.math.*

object Math3DUtil {

    fun colorOf(x: Int, y: Int, z: Int): Vector3f {
        return Vector3f(x.toFloat() / 255, y.toFloat() / 255, z.toFloat() / 255)
    }

    /**
     * DeepSeek解放大脑
     * @see getCycloidGraphic 获取此函数生成的图像的顶点
     * 参数要求必须和 getCycloidGraphic 生成的参数完全一致
     * @param count 填写你使用  getCycloidGraphic方法时 输入的count
     */
    fun computeCycloidVertices(
        r1: Double,
        r2: Double,
        w1: Int,
        w2: Int,
        count: Int,
        scale: Double
    ): MutableList<RelativeLocation> {
        val doubled = max(abs(w1), abs(w2))
        val precision = 360 * doubled / count
        val w1Step = w1 * precision
        val w2Step = w2 * precision

        val d = gcd(abs(w1), abs(w2))
        // 感谢MZ的数学更正
        val verticesCount = abs(w1 - w2) / d
        val vertices = mutableListOf<RelativeLocation>()

        for (k in 0..<verticesCount) {
            val delta = w1Step - w2Step
            val t = (2 * Math.PI * k) / delta
            val x = r1 * cos(w1Step * t) + r2 * cos(w2Step * t) * scale
            val z = r1 * sin(w1Step * t) + r2 * sin(w2Step * t) * scale
            vertices.add(
                RelativeLocation(x, 0.0, z)
            )
        }

        return vertices
    }


    /**
     * 求最大公约数
     */
    fun gcd(i: Int, j: Int): Int {
        var x = i.absoluteValue
        var y = j.absoluteValue
        while (y != 0) {
            val temp = y
            y = x % y
            x = temp
        }
        return x
    }

    /**
     * 此函数在计算上误将 弧度制输入成了角度制 导致结果出现偏差(虽然点大差不差, 因为360 * 0.1的 3600点的精度)
     * 足矣覆盖误差 但是要更加正确完整的结果请使用 输入count计算精度的函数版本
     *
     * @param r1 中心圆的半径
     * @param r2 中心圆上的圆的半径
     * @param w1 中心圆的角速度
     * @param w2 中心圆上的圆的角速度
     * r1:r2 与 w1:w2 和 生成的图形有紧密的关系
     * 例如
     * r1:r2 = 3:2 w1:w2 = 2:-3 时 图像是一个五角星
     * @param scale 半径精度 如果r1认为太大 则设置小的值
     * @return 最后的图像 (在XZ平面上(以Z为纵坐标))
     */
    fun getCycloidGraphic(
        r1: Double,
        r2: Double,
        w1: Int,
        w2: Int,
        count: Int,
        scale: Double
    ): MutableList<RelativeLocation> {
        // 原点上的圆的当前角度
        val result = ArrayList<RelativeLocation>()
        var radOrigin = 0.0
        var radA = 0.0
        val doubled = max(abs(w1), abs(w2))
        var current = 0
        val precision = 360 * doubled / count
        while (current < count) {
            radOrigin += w1 * precision
            radA += w2 * precision
            result.add(
                RelativeLocation(
                    (r2 * cos(radA) + r1 * cos(radOrigin)) * scale,
                    0.0,
                    (r2 * sin(radA) + r1 * sin(radOrigin)) * scale
                )
            )
            current++
        }
        return result
    }

    /**
     * from new bing
     * 将一个相对位置按照axis旋转 n度
     * @param angle 角度 输入时使用弧度制的角度
     */
    fun rotateVector(point: RelativeLocation, axis: RelativeLocation, angle: Double): RelativeLocation {
        val sinAngle = sin(angle / 2)
        val cosAngle = cos(angle / 2)
        val rotationAxis = axis.normalize() * sinAngle
        val rotationQuaternion = Quaternion(cosAngle, rotationAxis.x, rotationAxis.y, rotationAxis.z)
        val conjugateQuaternion = rotationQuaternion.conjugate()
        val vectorQuaternion = Quaternion(0.0, point.x, point.y, point.z)
        val rotatedVectorQuaternion = rotationQuaternion * vectorQuaternion * conjugateQuaternion
        return RelativeLocation(rotatedVectorQuaternion.x, rotatedVectorQuaternion.y, rotatedVectorQuaternion.z)
    }


    /**
     * 向量图形绕轴旋转N度
     * @param angle 角度 输入一个弧度制角度
     */
    fun rotateAsAxis(locList: List<RelativeLocation>, axis: RelativeLocation, angle: Double): List<RelativeLocation> {
        for (loc in locList) {
            val rotatePoint = rotateVector(loc, axis, angle)
            loc.also {
                it.x = rotatePoint.x
                it.y = rotatePoint.y
                it.z = rotatePoint.z
            }
        }
        return locList
    }

    /**
     * 让图形的对称轴指向某个点(图形跟着转变)
     */
    fun rotatePointsToPoint(
        locList: List<RelativeLocation>,
        toPoint: RelativeLocation,
        axis: RelativeLocation
    ): List<RelativeLocation> {
        if (axis.cross(toPoint).length() in -1e-5..1e-5) {
            return locList
        }
        // 计算旋转角度
        // 首先，将目标点（toPoint）和当前轴（axis）都归一化
        val normalizedAxis = axis.normalize()
        val normalizedToPoint = toPoint.normalize()

        // 计算两个向量之间的夹角
        val angle = acos(normalizedAxis.dot(normalizedToPoint))

        // 计算旋转轴，它是当前轴和目标点的叉乘
        val rotationAxis = normalizedAxis.cross(normalizedToPoint).normalize()
        // 使用rotateAsAxis函数旋转locList
        return rotateAsAxis(locList, rotationAxis, angle)
    }

    /**
     * 让图形的对称轴指向某个点(图形跟着转变)
     */
    fun rotatePointsToPoint(
        locList: List<RelativeLocation>,
        origin: Vec3d,
        toPoint: Vec3d,
        axis: RelativeLocation
    ): List<RelativeLocation> {
        if (axis.length() in -0.00001..0.000001) {
            return locList
        }
        val relToPoint = RelativeLocation.of(origin, toPoint)
        return rotatePointsToPoint(locList, relToPoint, axis)
    }


    /**
     * @param angle 角度
     * @param rad 角度是否为弧度制
     * @return 返回符合游戏要求的角度制度数
     */
    fun toMinecraftAngle(angle: Double, rad: Boolean): Double {
        var enter = angle
        if (rad) {
            enter = Math.toDegrees(angle)
        }
        enter %= 360
        if (enter > 180) enter -= 360
        if (enter < -180) enter += 360
        return enter
    }

    /**
     * @param yaw 输入弧度制yaw
     */
    fun toMinecraftYaw(yaw: Double): Double = yaw - PI / 2

    fun getYawFromLocation(loc: Vec3d): Double {
        return atan2(loc.z, loc.x)
    }

    // 这不纯傻逼
    // 不就求线面角吗 ???
    fun getPitchFromLocation(loc: Vec3d): Double {
        // 可以用when代替 但是懒得换了:(
        if (loc.y == 0.0 && loc.x == 0.0 && loc.z == 0.0) return 0.0
        val sq = sqrt(loc.x.pow(2) + loc.z.pow(2))
        return atan2(loc.y, sq * getAxisSymbol(loc))
    }

    fun getPitchFromRelativeLocation(vec: RelativeLocation): Double {
        val yAxis = RelativeLocation(0.0, 1.0, 0.0)
        val l = vec.length()
        val dot = yAxis.dot(vec)
        // l * yl * cos(*) = dot
        // cos(*) = dot / (l * yl)
        // 90 - acos(dot/(l * yl))
        return Math.toRadians(90.0) - acos(dot / l)
    }

    /**
     * 获取在start-end线段内的count个点集合
     */
    fun getLineLocations(start: Vec3d, end: Vec3d, count: Int): List<RelativeLocation> {
        val origin = RelativeLocation.of(start)
        val res = mutableListOf(origin)
        val step = start.distanceTo(end) / count
        val direction = start.relativize(end).normalize().multiply(step)
        val relativeDirection = RelativeLocation.of(direction)
        for (i in 2..count) {
            val next = origin + relativeDirection
            res.add(next)
        }
        return res
    }

    /**
     * 获取 从origin 向 direction方向的射线上 每个间距为 step 且总数量为count的点集合
     */
    fun getLineLocations(origin: Vec3d, direction: Vec3d, step: Double, count: Int): List<RelativeLocation> {
        val originRel = RelativeLocation.of(origin)
        val res = mutableListOf(originRel)
        val relativeDirection =
            RelativeLocation.of(Vec3d(direction.x, direction.y, direction.z).normalize().multiply(step))
        for (i in 2..count) {
            val next = originRel + relativeDirection
            res.add(next)
        }
        return res
    }

    /**
     * 旋转是通过旋转x/z 轴来坐标值的
     * 由于sqrt pow 是恒大于0的值因此不能用于坐标求值
     */
    private fun getAxisSymbol(loc: Vec3d): Int {
        val quadrants = getQuadrants(getYawFromLocation(loc))
        return when (quadrants) { // 1
            1 -> if (loc.x >= 0 && loc.z >= 0) 1 else -1
            2 -> if (loc.x <= 0 && loc.z >= 0) 1 else -1
            3 -> if (loc.x <= 0 && loc.z <= 0) 1 else -1
            4 -> if (loc.x >= 0 && loc.z <= 0) 1 else -1
            else -> 1
        }
    }

    private fun getQuadrants(rad: Double): Int {
        val sin = sin(rad)
        val cos = cos(rad)
        return if (sin > 0 && cos > 0) 1 else if (sin < 0 && cos > 0) 4 else if (sin > 0 && cos < 0) 2 else if (sin < 0 && cos < 0) 3
        else if (sin == 0.0 && cos > 0) {
            // X轴上
            1
        } else if (sin == 0.0 && cos < 0) {
            // X负半轴
            3
        } else if (sin > 0) {
            2
        } else {
            4
        }
    }

    private class Quaternion(var w: Double, var x: Double, var y: Double, var z: Double) {
        fun conjugate(): Quaternion = Quaternion(w, -x, -y, -z)
        operator fun times(that: Float): Quaternion = Quaternion(
            this.w * that,
            this.x * that,
            this.y * that,
            this.z * that
        )

        operator fun times(that: Quaternion): Quaternion = Quaternion(
            this.w * that.w - this.x * that.x - this.y * that.y - this.z * that.z,
            this.x * that.w + this.w * that.x - this.z * that.y + this.y * that.z,
            this.y * that.w + this.z * that.x + this.w * that.y - this.x * that.z,
            this.z * that.w - this.y * that.x + this.x * that.y + this.w * that.z
        )
    }


}