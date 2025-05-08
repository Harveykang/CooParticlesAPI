package cn.coostack.cooparticlesapi.network.buffer

import cn.coostack.cooparticlesapi.CooParticleAPI
import net.minecraft.util.Identifier

class EmptyControlerBuffer() : ParticleControlerDataBuffer<Unit> {

    companion object {
        @JvmStatic
        val id = ParticleControlerDataBuffer.Id(
            Identifier.of(
                CooParticleAPI.MOD_ID, "empty"
            )
        )
    }

    override var loadedValue: Unit? = null

    override fun encode(value: Unit): ByteArray {
        return byteArrayOf(0)
    }


    override fun encode(): ByteArray {
        return byteArrayOf()
    }

    override fun decode(buf: ByteArray) {
    }

    override fun getBufferID(): ParticleControlerDataBuffer.Id {
        return id
    }

}