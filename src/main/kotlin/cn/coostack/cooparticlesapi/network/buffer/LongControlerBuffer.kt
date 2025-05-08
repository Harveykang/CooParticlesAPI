package cn.coostack.cooparticlesapi.network.buffer

import cn.coostack.cooparticlesapi.CooParticleAPI
import net.minecraft.util.Identifier

class LongControlerBuffer : ParticleControlerDataBuffer<Long> {
    companion object {
        @JvmStatic
        val id = ParticleControlerDataBuffer.Id(
            Identifier.of(
                CooParticleAPI.MOD_ID, "long"
            )
        )
    }
    override var loadedValue: Long? = 0L
    override fun encode(value: Long): ByteArray {
        return value.toString().toByteArray()
    }

    override fun encode(): ByteArray? {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): Long {
        return String(buf).toLong()
    }

    override fun getBufferID(): ParticleControlerDataBuffer.Id {
        return id
    }

}
