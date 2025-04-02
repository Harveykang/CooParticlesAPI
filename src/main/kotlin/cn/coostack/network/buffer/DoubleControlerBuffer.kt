package cn.coostack.network.buffer

class DoubleControlerBuffer() : ParticleControlerDataBuffer<Double> {
    override var loadedValue: Double? = 0.0
    override fun encode(value: Double): ByteArray {
        return value.toString().toByteArray()
    }

    override fun encode(): ByteArray? {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): Double {
        return String(buf).toDouble()
    }

}