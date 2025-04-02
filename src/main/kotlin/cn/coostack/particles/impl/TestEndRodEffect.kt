package cn.coostack.particles.impl

import cn.coostack.particles.ModParticles
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.Unpooled
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType
import java.util.UUID

class TestEndRodEffect(val controlUUID: UUID) : ParticleEffect {
    companion object {
        @JvmStatic
        val codec: MapCodec<TestEndRodEffect> = RecordCodecBuilder.mapCodec {
            return@mapCodec it.group(
                Codec.BYTE_BUFFER.fieldOf("uuid").forGetter { effect ->
                    val toString = effect.controlUUID.toString()
                    val buffer = Unpooled.buffer()
                    buffer.writeBytes(toString.toByteArray())
                    buffer.nioBuffer()
                }
            ).apply(it) { buf ->
                TestEndRodEffect(
                    UUID.fromString(
                        String(buf.array())
                    )
                )
            }
        }

        @JvmStatic
        val packetCode: PacketCodec<RegistryByteBuf, TestEndRodEffect> = PacketCodec.of(
            { effect, buf ->
                buf.writeUuid(effect.controlUUID)
            }, {
                TestEndRodEffect(it.readUuid())
            }
        )

    }

    override fun getType(): ParticleType<*> {
        return ModParticles.testEndRod
    }
}