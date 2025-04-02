package cn.coostack.network.particle

import cn.coostack.network.buffer.ParticleControlerDataBuffer
import cn.coostack.network.buffer.ParticleControlerDataBuffers
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class TestParticleGroup(private val bindPlayer: ServerPlayerEntity) : ServerParticleGroup(UUID.randomUUID(), 16.0) {
    override fun tick() {
        withPlayerStats(bindPlayer)
        setPosOnServer(bindPlayer.eyePos)
    }

    override fun otherPacketArgs(): Map<String, ParticleControlerDataBuffer<out Any>> {
        return mapOf(
            "bindUUID" to ParticleControlerDataBuffers.uuid(bindPlayer.uuid)
        )
    }
}