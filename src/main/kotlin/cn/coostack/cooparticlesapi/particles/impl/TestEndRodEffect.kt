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

class TestEndRodEffect(controlUUID: UUID, faceToPlayer: Boolean = true) :
    ControlableParticleEffect(controlUUID, faceToPlayer) {
    companion object {
        @JvmStatic
        val codec: MapCodec<TestEndRodEffect> = RecordCodecBuilder.mapCodec {
            return@mapCodec it.group(
                Codec.BYTE_BUFFER.fieldOf("uuid").forGetter { effect ->
                    val toString = effect.controlUUID.toString()
                    val buffer = Unpooled.buffer()
                    buffer.writeBytes(toString.toByteArray())
                    buffer.nioBuffer()
                },
                Codec.BOOL.fieldOf("face_to_player").forGetter { effect ->
                    effect.faceToPlayer
                }
            ).apply(it) { buf, faceToPlayer ->
                TestEndRodEffect(
                    UUID.fromString(
                        String(buf.array())
                    ), faceToPlayer
                )
            }
        }

        @JvmStatic
        val packetCode: PacketCodec<RegistryByteBuf, TestEndRodEffect> = PacketCodec.of(
            { effect, buf ->
                buf.writeUuid(effect.controlUUID)
                buf.writeBoolean(effect.faceToPlayer)
            }, {
                TestEndRodEffect(it.readUuid(), it.readBoolean())
            }
        )
    }

    override fun getType(): ParticleType<*> {
        return CooModParticles.testEndRod
    }
}