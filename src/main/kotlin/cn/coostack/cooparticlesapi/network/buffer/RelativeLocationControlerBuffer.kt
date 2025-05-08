package cn.coostack.cooparticlesapi.network.buffer

import cn.coostack.cooparticlesapi.CooParticleAPI
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import io.netty.buffer.Unpooled
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d

class RelativeLocationControlerBuffer : ParticleControlerDataBuffer<RelativeLocation> {
    companion object {
        @JvmStatic
        val id = ParticleControlerDataBuffer.Id(
            Identifier.of(
                CooParticleAPI.MOD_ID, "relative"
            )
        )
    }

    override var loadedValue: RelativeLocation? = RelativeLocation()

    override fun encode(): ByteArray {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): RelativeLocation {
        return RelativeLocation.fromBytes(buf)
    }

    override fun getBufferID(): ParticleControlerDataBuffer.Id {
        return id
    }

    override fun encode(value: RelativeLocation): ByteArray {
        return value.toBytes()
    }

}