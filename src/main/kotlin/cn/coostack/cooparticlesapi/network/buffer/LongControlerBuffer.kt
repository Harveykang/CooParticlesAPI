package cn.coostack.cooparticlesapi.network.buffer

class LongControlerBuffer : ParticleControlerDataBuffer<Long> {
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

}
