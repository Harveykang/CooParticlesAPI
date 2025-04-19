package cn.coostack.cooparticlesapi.network.buffer

import io.netty.buffer.Unpooled
import net.minecraft.util.math.Vec3d

class Vec3dControlerBuffer : ParticleControlerDataBuffer<Vec3d> {
    override var loadedValue: Vec3d? = Vec3d.ZERO

    override fun encode(): ByteArray {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): Vec3d {
        val wrappedBuffer = Unpooled.wrappedBuffer(buf)
        return Vec3d(wrappedBuffer.readDouble(), wrappedBuffer.readDouble(), wrappedBuffer.readDouble())
    }

    override fun encode(value: Vec3d): ByteArray {
        val buf = Unpooled.buffer()
        buf.writeDouble(value.x)
        buf.writeDouble(value.y)
        buf.writeDouble(value.z)
        return buf.copy().array()
    }

}