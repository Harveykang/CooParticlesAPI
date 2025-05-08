package cn.coostack.cooparticlesapi.network.buffer

import cn.coostack.cooparticlesapi.CooParticleAPI
import net.minecraft.util.Identifier

class FloatControlerBuffer() : ParticleControlerDataBuffer<Float> {

    companion object {
        @JvmStatic
        val id = ParticleControlerDataBuffer.Id(
            Identifier.of(
                CooParticleAPI.MOD_ID, "float"
            )
        )
    }

    override var loadedValue: Float? = 0.0f
    override fun encode(value: Float): ByteArray {
        return value.toString().toByteArray()
    }


    override fun encode(): ByteArray? {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): Float {
        return String(buf).toFloat()

    }

    override fun getBufferID(): ParticleControlerDataBuffer.Id {
        return id
    }

}