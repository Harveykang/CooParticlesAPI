package cn.coostack.particles.control.group

import cn.coostack.particles.ControlableParticle
import cn.coostack.particles.control.ControlParticleManager
import cn.coostack.particles.control.ParticleControler
import cn.coostack.test.util.Math3DUtil
import cn.coostack.test.util.RelativeLocation
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.ParticleEffect
import net.minecraft.util.math.Vec3d
import java.util.HashMap
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * 这里的所有参数都要设置为protected来保护group在每个玩家的眼里都是一样的形态
 * 此类负责渲染服务器发送给客户端的行为
 * 由服务器发包构建
 *
 * 继承此类须知
 * 并且只允许构造函数有且仅有这一个参数
 * 所有针对参数的修改请使用 var val init{} 修改
 * 同时也不要在类的外部输入任何参数 (除了在ControlableParticleGroupProvider的toggleGroup方法内)
 * 在发包序列化时并没有将类序列化成Byte[]传输给其他客户端
 * 而是直接通过class构建实例并且输入UUID添加到玩家的客户端上
 * 因此所有针对粒子的外部修改只会生效到一个客户端上
 *
 * 在客户端cancel只会在客户端不可见并且会被服务器状态刷新
 * 所以删除粒子只能在服务器上
 */
@Environment(EnvType.CLIENT)
abstract class ControlableParticleGroup(val uuid: UUID) {
    protected val particles = ConcurrentHashMap<UUID, ParticleControler>()
    private val invokeQueue = mutableListOf<(ControlableParticleGroup) -> Unit>()

    /**
     * uuid 代表ParticleControler的UUID
     */
    private val particleRelativeLocations = loadParticleLocations()

    var tick = 0
    var maxTick = 120

    /**
     * 是否会因为tick >= maxTick而禁止
     */
    var withTickDeath = false
    var valid = true
    var canceled = false
    var origin: Vec3d = Vec3d.ZERO
    var world: ClientWorld? = null
    var axis = RelativeLocation(0.0, 1.0, 0.0)
    var displayed = false

    /**
     * value代表的是 ParticleControler的初始化函数
     * @see ParticleControler.initInvoker value最终赋值到这里
     */
    abstract fun loadParticleLocations(): Map<RelativeLocation, ParticleRelativeData>

    /**
     * 每次客户端更新可见时都会执行一次这个方法
     */
    abstract fun onGroupDisplay()


    fun withEffect(effect: (UUID) -> ParticleEffect, invoker: ControlableParticle.() -> Unit): ParticleRelativeData =
        ParticleRelativeData(effect, invoker)

    fun clearParticles() {
        particles.onEach {
            it.value.particle.markDead()
        }.clear()
        valid = false
    }

    fun tick() {
        if (!valid || canceled) {
            clearParticles()
            return
        }
        if (withTickDeath) {
            if (tick++ >= maxTick) {
                valid = false
                return
            }
        }
        invokeQueue.forEach { it(this) }
    }

    fun display(pos: Vec3d, world: ClientWorld) {
        if (displayed) {
            return
        }
        this.origin = pos
        this.world = world
        displayed = true
        for ((rl, v) in particleRelativeLocations) {
            val uuid = UUID.randomUUID()
            val controler = ControlParticleManager.createControl(uuid)
            controler.initInvoker = v.invoker
            world.addParticle(
                v.effect(uuid), pos.x + rl.x, pos.y + rl.y, pos.z + rl.z, 0.0, 0.0, 0.0
            )
            particles[uuid] = controler
        }
        onGroupDisplay()
    }

    fun rotateParticlesToPoint(to: RelativeLocation) {
        if (!displayed) {
            return
        }
        val relativeParticles = getParticleRelativeLocations()
        Math3DUtil.rotatePointsToPoint(
            relativeParticles.values.toList(), to, axis
        )

        // 把粒子丢到对应的位置
        relativeParticles.forEach { (t, u) ->
            t.controlAction { teleportTo(u.x + origin.x, u.y + origin.y, u.z + origin.z) }
        }

        axis = to.normalize()
    }

    fun rotateToWithAngle(to: RelativeLocation, angle: Double) {
        if (!displayed) {
            return
        }
        val relativeParticles = getParticleRelativeLocations()
        Math3DUtil.rotatePointsToPoint(
            relativeParticles.values.toList(), to, axis
        )
        Math3DUtil.rotateAsAxis(
            relativeParticles.values.toList(), to.normalize(), angle
        )
        relativeParticles.forEach { (t, u) ->
            t.controlAction { teleportTo(u.x + origin.x, u.y + origin.y, u.z + origin.z) }
        }
        axis = to.normalize()
    }

    /**
     * @param angle 输入弧度制
     */
    fun rotateParticlesAsAxis(angle: Double) {
        if (!displayed) {
            return
        }
        val relativeParticles = getParticleRelativeLocations()
        Math3DUtil.rotateAsAxis(
            relativeParticles.values.toList(), axis, angle
        )
        // 把粒子丢到对应的位置
        relativeParticles.forEach { (t, u) ->
            t.controlAction { teleportTo(u.x + origin.x, u.y + origin.y, u.z + origin.z) }
        }
    }

    fun teleportGroupTo(pos: Vec3d) {
        val relativeMapper = getParticleRelativeLocations()
        this.origin = pos
        relativeMapper.forEach { (t, u) ->
            t.controlAction {
                teleportTo(u.x + pos.x, u.y + pos.y, u.z + pos.z)
            }
        }
    }

    /**
     * 设置为protected的原因是不允许外界添加新的action以保持在其他玩家的视角里粒子运动方式是一致的
     * 因此只能在init里对其进行添加
     */
    protected fun addPreTickAction(action: (ControlableParticleGroup) -> Unit): ControlableParticleGroup {
        invokeQueue.add(action)
        return this
    }

    protected fun getParticleRelativeLocations(): Map<ParticleControler, RelativeLocation> {
        val relativeParticles = HashMap<ParticleControler, RelativeLocation>()
        particles.forEach { (t, u) ->
            val value = RelativeLocation.of(origin.relativize(u.particle.pos))
            relativeParticles[u] = value
        }
        return relativeParticles
    }

    data class ParticleRelativeData(
        val effect: (UUID) -> ParticleEffect,
        val invoker: ControlableParticle.() -> Unit
    )

}