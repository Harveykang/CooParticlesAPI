package cn.coostack.network.buffer

class CharControlerBuffer : ParticleControlerDataBuffer<Char> {
    override var loadedValue: Char? = ' '
    override fun encode(value: Char): ByteArray {
        return value.toString().toByteArray()
    }


    override fun encode(): ByteArray {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): Char {
        return String(buf)[0]
    }

}
