package cn.coostack.network.particle

import cn.coostack.network.buffer.ParticleControlerDataBuffer
import cn.coostack.network.buffer.ParticleControlerDataBuffers
import net.minecraft.server.network.ServerPlayerEntity
import java.util.*

class ScaleCircleGroupServer(private val bindPlayer: ServerPlayerEntity, visibleRange: Double = 32.0) :
    ServerParticleGroup(visibleRange) {
    private var anTick = 0
    private var anMaxTick = 30
    override fun tick() {
        if (anTick++ >= anMaxTick) {
            anTick = anMaxTick
        }
        teleportGroupTo(bindPlayer.pos)
    }

    override fun otherPacketArgs(): Map<String, ParticleControlerDataBuffer<out Any>> {
        return mapOf(
            "bind_player" to ParticleControlerDataBuffers.uuid(bindPlayer.uuid),
            "an_tick" to ParticleControlerDataBuffers.int(anTick)
        )
    }
}