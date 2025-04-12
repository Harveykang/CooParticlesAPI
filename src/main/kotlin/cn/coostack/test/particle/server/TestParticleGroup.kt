package cn.coostack.test.particle.server

import cn.coostack.network.buffer.ParticleControlerDataBuffer
import cn.coostack.network.buffer.ParticleControlerDataBuffers
import cn.coostack.network.particle.ServerParticleGroup
import cn.coostack.particles.control.group.ControlableParticleGroup
import cn.coostack.test.particle.client.TestGroupClient
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class TestParticleGroup(private val bindPlayerUUID: UUID) : ServerParticleGroup(16.0) {
    override fun tick() {
        val bindPlayer = world!!.getPlayerByUuid(bindPlayerUUID) ?: let {
            kill()
            return
        }
        withPlayerStats(bindPlayer as ServerPlayerEntity)
        setPosOnServer(bindPlayer.eyePos)
    }

    override fun otherPacketArgs(): Map<String, ParticleControlerDataBuffer<out Any>> {
        return mapOf(
            "bindUUID" to ParticleControlerDataBuffers.uuid(bindPlayerUUID)
        )
    }

    override fun getClientType(): Class<out ControlableParticleGroup> {
        return TestGroupClient::class.java
    }
}