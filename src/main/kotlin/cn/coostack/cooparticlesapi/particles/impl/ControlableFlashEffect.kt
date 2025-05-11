package cn.coostack.cooparticlesapi.particles.impl

import cn.coostack.cooparticlesapi.particles.ControlableParticleEffect
import cn.coostack.cooparticlesapi.particles.CooModParticles
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.Unpooled
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.particle.ParticleType
import java.util.UUID

class ControlableFlashEffect(controlUUID: UUID) : ControlableParticleEffect(controlUUID) {
    companion object {
        @JvmStatic
        val codec: MapCodec<ControlableFlashEffect> = RecordCodecBuilder.mapCodec {
            return@mapCodec it.group(
                Codec.BYTE_BUFFER.fieldOf("uuid").forGetter { effect ->
                    val toString = effect.controlUUID.toString()
                    val buffer = Unpooled.buffer()
                    buffer.writeBytes(toString.toByteArray())
                    buffer.nioBuffer()
                }
            ).apply(it) { buf ->
                ControlableFlashEffect(
                    UUID.fromString(
                        String(buf.array())
                    )
                )
            }
        }

        @JvmStatic
        val packetCode: PacketCodec<RegistryByteBuf, ControlableFlashEffect> = PacketCodec.of(
            { effect, buf ->
                buf.writeUuid(effect.controlUUID)
            }, {
                ControlableFlashEffect(it.readUuid())
            }
        )
    }


    override fun getType(): ParticleType<*>? {
        return CooModParticles.controlableFlash
    }
}