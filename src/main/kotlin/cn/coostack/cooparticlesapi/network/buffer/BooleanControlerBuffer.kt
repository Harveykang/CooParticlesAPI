package cn.coostack.cooparticlesapi.network.buffer

class BooleanControlerBuffer() : ParticleControlerDataBuffer<Boolean> {
    override var loadedValue: Boolean? = false

    override fun encode(value: Boolean): ByteArray {
        return byteArrayOf(if (value) 1 else 0)
    }


    override fun encode(): ByteArray {
        return encode(loadedValue ?: false)
    }

    override fun decode(buf: ByteArray): Boolean {
        if (buf.isNotEmpty()) {
            return buf[0] == 1.toByte()
        }
        return false
    }

}