package cn.coostack.network.particle

import cn.coostack.network.buffer.ParticleControlerDataBuffer
import cn.coostack.network.buffer.ParticleControlerDataBuffers
import cn.coostack.particles.control.group.impl.TestGroupClient
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import java.util.*

class ScaleCircleGroupServer(private val bindPlayerUUID: UUID, visibleRange: Double = 32.0) :
    ServerParticleGroup(visibleRange) {
    private var anTick = 0
    private var anMaxTick = 30

    init {
        maxTick = 120
        tick = 0
    }

    override fun tick() {
        val bindPlayer = world!!.getPlayerByUuid(bindPlayerUUID) ?: let {
            kill()
            return
        }
        doTickAlive()
        if (anTick++ >= anMaxTick) {
            anTick = anMaxTick
        }
        setPosOnServer(bindPlayer.pos)
        withPlayerStats(bindPlayer as ServerPlayerEntity)
    }

    override fun onTickAliveDeath() {
        val bindPlayer = world!!.getPlayerByUuid(bindPlayerUUID) ?: let {
            kill()
            return
        }
        val group = TestParticleGroup(bindPlayerUUID)
        ServerParticleGroupManager.addParticleGroup(
            TestGroupClient::class.java,
            group,
            bindPlayer.eyePos,
            world as ServerWorld
        )
    }

    override fun otherPacketArgs(): Map<String, ParticleControlerDataBuffer<out Any>> {
        return mapOf(
            "bind_player" to ParticleControlerDataBuffers.uuid(bindPlayerUUID),
            "an_tick" to ParticleControlerDataBuffers.int(anTick),
            "tick" to ParticleControlerDataBuffers.int(tick),
            "max_tick" to ParticleControlerDataBuffers.int(maxTick)
        )
    }

}