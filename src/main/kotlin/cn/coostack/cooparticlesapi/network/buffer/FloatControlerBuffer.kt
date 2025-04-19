package cn.coostack.cooparticlesapi.network.buffer

class FloatControlerBuffer() : ParticleControlerDataBuffer<Float> {
    override var loadedValue: Float? = 0.0f
    override fun encode(value: Float): ByteArray {
        return value.toString().toByteArray()
    }


    override fun encode(): ByteArray? {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): Float {
        return String(buf).toFloat()

    }

}