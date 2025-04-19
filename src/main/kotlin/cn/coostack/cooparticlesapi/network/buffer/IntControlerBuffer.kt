package cn.coostack.cooparticlesapi.network.buffer

class IntControlerBuffer : ParticleControlerDataBuffer<Int> {
    override var loadedValue: Int? = 0
    override fun encode(value: Int): ByteArray {
        return value.toString().toByteArray()
    }


    override fun encode(): ByteArray? {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): Int {
        return String(buf).toInt()
    }

}
