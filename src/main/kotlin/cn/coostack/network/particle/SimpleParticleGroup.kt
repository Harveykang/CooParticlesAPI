package cn.coostack.network.particle

import cn.coostack.network.buffer.ParticleControlerDataBuffer
import java.util.*

class SimpleParticleGroup(uuid: UUID, visibleRange: Double = 32.0) : ServerParticleGroup(uuid, visibleRange) {
    override fun tick() {}
    override fun otherPacketArgs(): Map<String, ParticleControlerDataBuffer<out Any>> {
        return mapOf()
    }
}