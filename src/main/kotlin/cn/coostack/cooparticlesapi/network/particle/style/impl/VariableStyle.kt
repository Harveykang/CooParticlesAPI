package cn.coostack.cooparticlesapi.network.particle.style.impl

import cn.coostack.cooparticlesapi.network.buffer.ParticleControlerDataBuffer
import cn.coostack.cooparticlesapi.network.particle.style.ParticleGroupStyle
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import java.util.UUID

class VariableStyle(visibleRange: Double = 32.0, uuid: UUID = UUID.randomUUID()) :
    ParticleGroupStyle(visibleRange, uuid) {

    /**
     * 统计可变内容
     */
    class VariableHelper() {
        data class VariableOption(
            val origin: RelativeLocation,
        ) {
        }

        private val states = ArrayList<VariableOption>()

    }

    override fun getCurrentFrames(): Map<StyleData, RelativeLocation> {
        TODO("Not yet implemented")
    }

    override fun onDisplay() {
        TODO("Not yet implemented")
    }

    override fun writePacketArgs(): Map<String, ParticleControlerDataBuffer<*>> {
        TODO("Not yet implemented")
    }

    override fun readPacketArgs(args: Map<String, ParticleControlerDataBuffer<*>>) {
        TODO("Not yet implemented")
    }
}