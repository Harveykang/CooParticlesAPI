package cn.coostack.cooparticlesapi.particles.control

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * Client单例
 */
@Environment(EnvType.CLIENT)
object ControlParticleManager {
    private val controls = ConcurrentHashMap<UUID, ParticleControler>()

    internal fun getControl(uuid: UUID): ParticleControler? {
        return controls[uuid]
    }

    internal fun removeControl(uuid: UUID) {
        controls.remove(uuid)
    }

    fun createControl(uuid: UUID): ParticleControler {
        val controler = ParticleControler(uuid)
        controls[uuid] = controler
        return controler
    }
}