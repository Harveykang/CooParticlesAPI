package cn.coostack.network.buffer

class EmptyControlerBuffer() : ParticleControlerDataBuffer<Unit> {
    override var loadedValue: Unit? = null

    override fun encode(value: Unit): ByteArray {
        return byteArrayOf(0)
    }


    override fun encode(): ByteArray {
        return encode()
    }

    override fun decode(buf: ByteArray) {
    }

}