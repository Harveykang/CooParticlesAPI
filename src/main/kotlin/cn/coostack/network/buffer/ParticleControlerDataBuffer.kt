package cn.coostack.network.buffer

interface ParticleControlerDataBuffer<T> {
    var loadedValue: T?
    fun encode(): ByteArray?

    fun encode(value: T): ByteArray

    fun decode(buf: ByteArray): T
}