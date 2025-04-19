package cn.coostack.cooparticlesapi.network.packet

import cn.coostack.cooparticlesapi.CooParticleAPI
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d

/**
 *
 */
class PacketParticleS2C(
    val type: ParticleEffect,
    val pos: Vec3d,
    val velocity: Vec3d,
) : CustomPayload {
    companion object {
        private val identifierID = Identifier.of(CooParticleAPI.MOD_ID, "particle")
        val payloadID = CustomPayload.Id<PacketParticleS2C>(identifierID)
        private val CODEC: PacketCodec<RegistryByteBuf, PacketParticleS2C> =
            CustomPayload.codecOf({ packet, buf ->
                buf.writeVec3d(packet.pos)
                buf.writeVec3d(packet.velocity)
                ParticleTypes.PACKET_CODEC.encode(buf, packet.type)
            }, { buf ->
                val pos = buf.readVec3d()
                val velocity = buf.readVec3d()
                val type = ParticleTypes.PACKET_CODEC.decode(buf)
                return@codecOf PacketParticleS2C(type, pos, velocity)
            })

        fun init() {
            PayloadTypeRegistry.playS2C().register(payloadID, CODEC)
        }
    }

    override fun getId(): CustomPayload.Id<out CustomPayload> {
        return payloadID
    }
}