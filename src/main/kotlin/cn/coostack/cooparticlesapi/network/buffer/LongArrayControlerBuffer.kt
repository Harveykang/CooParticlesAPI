package cn.coostack.cooparticlesapi.network.buffer

import io.netty.buffer.Unpooled

class LongArrayControlerBuffer : ParticleControlerDataBuffer<LongArray> {
    override var loadedValue: LongArray? = LongArray(0)

    override fun encode(): ByteArray? {
        return encode(loadedValue!!)
    }

    override fun encode(value: LongArray): ByteArray {
        val buffer = Unpooled.buffer()
        buffer.writeInt(value.size)
        value.forEach { buffer.writeLong(it) }
        return buffer.copy().array()
    }

    override fun decode(buf: ByteArray): LongArray {
        val wrap = Unpooled.wrappedBuffer(buf)
        val size = wrap.readInt()
        val arr = LongArray(size)
        for (i in 0 until size) {
            arr[i] = wrap.readLong()
        }
        return arr
    }
}