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

class ControlableCloudEffect(controlUUID: UUID, faceToPlayer: Boolean = true) : ControlableParticleEffect(
    controlUUID,
    faceToPlayer
) {
    companion object {
        @JvmStatic
        val codec: MapCodec<ControlableCloudEffect> = RecordCodecBuilder.mapCodec {
            return@mapCodec it.group(
                Codec.BYTE_BUFFER.fieldOf("uuid").forGetter { effect ->
                    val toString = effect.controlUUID.toString()
                    val buffer = Unpooled.buffer()
                    buffer.writeBytes(toString.toByteArray())
                    buffer.nioBuffer()
                }, Codec.BOOL.fieldOf("face_to_player").forGetter { effect ->
                    effect.faceToPlayer
                }
            ).apply(it) { buf, b ->
                ControlableCloudEffect(
                    UUID.fromString(
                        String(buf.array())
                    ), b
                )
            }
        }

        @JvmStatic
        val packetCode: PacketCodec<RegistryByteBuf, ControlableCloudEffect> = PacketCodec.of(
            { effect, buf ->
                buf.writeUuid(effect.controlUUID)
                buf.writeBoolean(effect.faceToPlayer)
            }, {
                ControlableCloudEffect(it.readUuid(), it.readBoolean())
            }
        )
    }


    override fun getType(): ParticleType<*>? {
        return CooModParticles.controlableCloud
    }

    override fun getPacketCodec(): PacketCodec<RegistryByteBuf, out ControlableParticleEffect> {
        return packetCode
    }

    override fun clone(): ControlableParticleEffect {
        return ControlableCloudEffect(controlUUID, faceToPlayer)
    }
}