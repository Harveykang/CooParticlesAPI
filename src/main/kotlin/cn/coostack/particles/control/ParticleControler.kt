package cn.coostack.particles.control

import cn.coostack.particles.ControlableParticle
import cn.coostack.network.buffer.ParticleControlerDataBuffer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * 代理粒子
 * 此Controler 由 ControlerGroup代理创建 (Builder) 并使用
 */
@Environment(EnvType.CLIENT)
class ParticleControler(private val uuid: UUID) {
    lateinit var particle: ControlableParticle
        private set

    private var init = false
    private val invokeQueue = mutableListOf<ControlableParticle.() -> Unit>()

    /**
     * 参数缓存 (tick等)
     */
    val bufferedData = ConcurrentHashMap<String, ParticleControlerDataBuffer<*>>()
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


}