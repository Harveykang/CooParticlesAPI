package cn.coostack.particles.control.group

import cn.coostack.particles.Controlable
import cn.coostack.particles.ControlableParticle
import cn.coostack.particles.ParticleDisplayer
import cn.coostack.particles.control.ControlParticleManager
import cn.coostack.particles.control.ParticleControler
import cn.coostack.test.util.Math3DUtil
import cn.coostack.test.util.RelativeLocation
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.Vec3d
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
abstract class ControlableParticleGroup(val uuid: UUID) : Controlable<ControlableParticleGroup> {
    protected val particles = ConcurrentHashMap<UUID, Controlable<*>>()
    protected val particlesLocations = ConcurrentHashMap<Controlable<*>, RelativeLocation>()
    private val invokeQueue = mutableListOf<(ControlableParticleGroup) -> Unit>()

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
     * key代表的是 ParticleControler的初始化函数
     * @see ParticleControler.initInvoker key最终赋值到这里
     * 考虑到不同的粒子可能会存在于相同的位置，因此做此修改
     */
    abstract fun loadParticleLocations(): Map<ParticleRelativeData, RelativeLocation>

    /**
     * 每次客户端更新可见时都会执行一次这个方法
     */
    abstract fun onGroupDisplay()


    fun withEffect(effect: (UUID) -> ParticleDisplayer, invoker: ControlableParticle.() -> Unit): ParticleRelativeData =
        ParticleRelativeData(effect, invoker)

    fun clearParticles() {
        particles.onEach {
            val controlObject = it.value.getControlObject()
            if (controlObject is ControlableParticle) {
                controlObject.markDead()
            } else {
                controlObject as ControlableParticleGroup
                controlObject.clearParticles()
            }
        }.clear()
        particlesLocations.clear()
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
        particles.asSequence().filter { it.value is ControlableParticleGroup }.forEach {
            (it.value.getControlObject() as ControlableParticleGroup).tick()
        }
    }
    /**
     * 清除所有粒子并且重新按照相对位置渲染
     */
    fun flush() {
        // 即使flush也不会被处理
        // 如果没有display则不会出现origin world数据 因此也就无法进行操作
        if (canceled || !valid || !displayed) return
        clearParticles()
        valid = true
        displayParticles(origin, world!!)
    }



    fun display(pos: Vec3d, world: ClientWorld) {
        if (displayed) {
            return
        }
        this.origin = pos
        this.world = world
        displayed = true
        displayParticles(pos, world)
        onGroupDisplay()
    }


    override fun rotateParticlesToPoint(to: RelativeLocation) {
        if (!displayed) {
            return
        }
        Math3DUtil.rotatePointsToPoint(
            particlesLocations.values.toList(), to, axis
        )

        // 把粒子丢到对应的位置
        particlesLocations.forEach { (t, u) ->
            t.teleportTo(u.x + origin.x, u.y + origin.y, u.z + origin.z)
        }

        axis = to.normalize()
    }

    override fun rotateToWithAngle(to: RelativeLocation, angle: Double) {
        if (!displayed) {
            return
        }
        Math3DUtil.rotatePointsToPoint(
            particlesLocations.values.toList(), to, axis
        )
        Math3DUtil.rotateAsAxis(
            particlesLocations.values.toList(), to.normalize(), angle
        )
        particlesLocations.forEach { (t, u) ->
            t.teleportTo(u.x + origin.x, u.y + origin.y, u.z + origin.z)
        }
        axis = to.normalize()
    }

    /**
     * @param angle 输入弧度制
     */
    override fun rotateParticlesAsAxis(angle: Double) {
        if (!displayed) {
            return
        }
        Math3DUtil.rotateAsAxis(
            particlesLocations.values.toList(), axis, angle
        )
        // 把粒子丢到对应的位置
        particlesLocations.forEach { (t, u) ->
            t.teleportTo(u.x + origin.x, u.y + origin.y, u.z + origin.z)
        }
    }

    @Deprecated("使用 teleportTo")
    fun teleportGroupTo(pos: Vec3d) {
        this.origin = pos
        particlesLocations.forEach { (t, u) ->
            t.teleportTo(u.x + pos.x, u.y + pos.y, u.z + pos.z)
        }
    }

    override fun teleportTo(pos: Vec3d) {
        teleportGroupTo(pos)
    }

    override fun teleportTo(x: Double, y: Double, z: Double) {
        teleportGroupTo(Vec3d(x, y, z))
    }

    override fun getControlObject(): ControlableParticleGroup {
        return this
    }

    /**
     * 设置为protected的原因是不允许外界添加新的action以保持在其他玩家的视角里粒子运动方式是一致的
     * 因此只能在init里对其进行添加
     */
    protected fun addPreTickAction(action: (ControlableParticleGroup) -> Unit): ControlableParticleGroup {
        invokeQueue.add(action)
        return this
    }

    private fun displayParticles(pos: Vec3d, world: ClientWorld) {
        for ((v, rl) in loadParticleLocations()) {
            val uuid = UUID.randomUUID()
            val particleDisplayer = v.effect(uuid)
            if (particleDisplayer is ParticleDisplayer.SingleParticleDisplayer) {
                val controler = ControlParticleManager.createControl(uuid)
                controler.initInvoker = v.invoker
            }
            val toPos = Vec3d(pos.x + rl.x, pos.y + rl.y, pos.z + rl.z)
            val controler = particleDisplayer.display(toPos, world) ?: continue
            if (controler is ParticleControler) {
                v.controlerAction(controler)
            }
            particles[uuid] = controler
            particlesLocations[controler] = rl
        }
    }

    class ParticleRelativeData(
        val effect: (UUID) -> ParticleDisplayer,
        val invoker: ControlableParticle.() -> Unit
    ) {
        var controlerAction: (ParticleControler) -> Unit = {}
        fun withControler(controler: (ParticleControler) -> Unit): ParticleRelativeData {
            controlerAction = controler
            return this
        }
    }
}