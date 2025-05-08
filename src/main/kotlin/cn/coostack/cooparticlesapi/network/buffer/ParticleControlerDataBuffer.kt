package cn.coostack.cooparticlesapi.network.buffer

import net.minecraft.util.Identifier

interface ParticleControlerDataBuffer<T> {

    data class Id(val value: Identifier)

    var loadedValue: T?
    fun encode(): ByteArray?

    fun encode(value: T): ByteArray

    fun decode(buf: ByteArray): T

    fun getBufferID(): Id

}