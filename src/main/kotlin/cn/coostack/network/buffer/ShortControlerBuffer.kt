package cn.coostack.network.buffer

class ShortControlerBuffer() : ParticleControlerDataBuffer<Short> {
    override var loadedValue: Short? = 0
    override fun encode(value: Short): ByteArray {
        return value.toString().toByteArray()
    }


    override fun encode(): ByteArray? {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): Short {
        return String(buf).toShort()
    }

}
