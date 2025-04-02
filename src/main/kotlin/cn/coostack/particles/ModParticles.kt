package cn.coostack.particles

import cn.coostack.CooParticleAPI
import cn.coostack.particles.impl.TestEndRodEffect
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.Unpooled
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import java.util.*

object ModParticles {
    fun reg() {}
    val testEndRod: ParticleType<TestEndRodEffect> = register(
        "test_end_rod", false, { TestEndRodEffect.codec }, { TestEndRodEffect.packetCode }
    )

    fun <T : ParticleEffect?> register(
        id: String, alwaysShow: Boolean,
        codecGetter: (type: ParticleType<T>) -> MapCodec<T>,
        packetCodec: (type: ParticleType<T>) -> PacketCodec<RegistryByteBuf, T>,
    ): ParticleType<T> {
        val particleType = object : ParticleType<T>(alwaysShow) {
            override fun getCodec(): MapCodec<T> {
                return codecGetter(this)
            }

            override fun getPacketCodec(): PacketCodec<in RegistryByteBuf, T> {
                return packetCodec(this)
            }
        }
        return Registry.register(
            Registries.PARTICLE_TYPE, Identifier.of(CooParticleAPI.MOD_ID, id), particleType
        )
    }


}