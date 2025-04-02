package cn.coostack.network.buffer

class StringControlerBuffer : ParticleControlerDataBuffer<String> {
    override var loadedValue: String? = ""
    override fun encode(value: String): ByteArray {
        return value.toByteArray()
    }


    override fun encode(): ByteArray? {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): String {
        return String(buf)
    }

}
