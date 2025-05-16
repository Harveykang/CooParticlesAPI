package cn.coostack.cooparticlesapi.particles

import cn.coostack.cooparticlesapi.particles.impl.ControlableCloudEffect
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.Unpooled
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.particle.ParticleEffect
import java.util.UUID

abstract class ControlableParticleEffect(var controlUUID: UUID, val faceToPlayer: Boolean = true) : ParticleEffect {
    abstract fun getPacketCodec(): PacketCodec<RegistryByteBuf, out ControlableParticleEffect>
    abstract fun clone(): ControlableParticleEffect
}