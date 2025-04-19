package cn.coostack.cooparticlesapi.network.buffer

class EmptyControlerBuffer() : ParticleControlerDataBuffer<Unit> {
    override var loadedValue: Unit? = null

    override fun encode(value: Unit): ByteArray {
        return byteArrayOf(0)
    }


    override fun encode(): ByteArray {
        return byteArrayOf()
    }

    override fun decode(buf: ByteArray) {
    }

}