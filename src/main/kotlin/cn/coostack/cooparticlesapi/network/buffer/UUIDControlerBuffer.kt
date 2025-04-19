package cn.coostack.cooparticlesapi.network.buffer

import java.util.UUID

class UUIDControlerBuffer : ParticleControlerDataBuffer<UUID> {
    override var loadedValue: UUID? = UUID.randomUUID()
    override fun encode(value: UUID): ByteArray {
        return value.toString().toByteArray()
    }

    override fun encode(): ByteArray {
        return encode(loadedValue!!)
    }

    override fun decode(buf: ByteArray): UUID {
        return UUID.fromString(String(buf))
    }

}
