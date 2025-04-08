package cn.coostack.particles.control

import cn.coostack.particles.ControlableParticle
import cn.coostack.network.buffer.ParticleControlerDataBuffer
import cn.coostack.particles.Controlable
import cn.coostack.test.util.RelativeLocation
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.math.Vec3d
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * 代理粒子
 * 此Controler 由 ControlerGroup代理创建 (Builder) 并使用
 */
@Environment(EnvType.CLIENT)
class ParticleControler(private val uuid: UUID) : Controlable<ControlableParticle> {
    lateinit var particle: ControlableParticle
        private set

    private var init = false
    private val invokeQueue = mutableListOf<ControlableParticle.() -> Unit>()

    /**
     * 参数缓存 (tick等)
     */
    val bufferedData = ConcurrentHashMap<String, Any>()
    lateinit var initInvoker: ControlableParticle.() -> Unit

    fun addPreTickAction(action: ControlableParticle.() -> Unit): ParticleControler {
        invokeQueue.add(action)
        return this
    }

    fun controlAction(action: (ControlableParticle.() -> Unit)): ParticleControler {
        action(particle)
        return this
    }

    internal fun loadParticle(particle: ControlableParticle) {
        if (::particle.isInitialized) {
            return
        }
        if (particle.controlUUID != uuid) {
            throw IllegalArgumentException("Particle uuid invalid")
        }
        this.particle = particle
    }

    internal fun particleInit() {
        if (init) {
            return
        }
        if (!::initInvoker.isInitialized) {
            initInvoker = {}
        }
        initInvoker(particle)
        init = true
    }

    /**
     * @see ControlableParticle.tick 执行该函数
     */
    internal fun doTick() {
        invokeQueue.forEach {
            it(particle)
        }
    }

    override fun rotateParticlesToPoint(to: RelativeLocation) {
    }

    override fun rotateToWithAngle(to: RelativeLocation, angle: Double) {
    }

    override fun rotateParticlesAsAxis(angle: Double) {
    }

    override fun teleportTo(pos: Vec3d) {
        particle.teleportTo(pos)
    }

    override fun teleportTo(x: Double, y: Double, z: Double) {
        particle.teleportTo(x, y, z)
    }

    override fun getControlObject(): ControlableParticle {
        return particle
    }


}