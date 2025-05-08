package cn.coostack.cooparticlesapi.network.buffer

import cn.coostack.cooparticlesapi.CooParticleAPI
import io.netty.buffer.Unpooled
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d

class Vec3dControlerBuffer : ParticleControlerDataBuffer<Vec3d> {
    companion object {
        @JvmStatic
        val id = ParticleControlerDataBuffer.Id(
            Identifier.of(
                CooParticleAPI.MOD_ID, "vec3d"
            )
        )
    }

    override var loadedValue: Vec3d? = Vec3d.ZERO

    override fun encode(): ByteArray {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): Vec3d {
        val wrappedBuffer = Unpooled.wrappedBuffer(buf)
        return Vec3d(wrappedBuffer.readDouble(), wrappedBuffer.readDouble(), wrappedBuffer.readDouble())
    }

    override fun getBufferID(): ParticleControlerDataBuffer.Id {
        return id
    }

    override fun encode(value: Vec3d): ByteArray {
        val buf = Unpooled.buffer()
        buf.writeDouble(value.x)
        buf.writeDouble(value.y)
        buf.writeDouble(value.z)
        return buf.copy().array()
    }

}