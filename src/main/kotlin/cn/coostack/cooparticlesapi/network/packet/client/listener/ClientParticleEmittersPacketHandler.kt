package cn.coostack.cooparticlesapi.network.packet.client.listener

import cn.coostack.cooparticlesapi.network.packet.PacketParticleEmittersS2C
import cn.coostack.cooparticlesapi.network.particle.emitters.ParticleEmitters
import cn.coostack.cooparticlesapi.network.particle.emitters.ParticleEmittersManager
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object ClientParticleEmittersPacketHandler : ClientPlayNetworking.PlayPayloadHandler<PacketParticleEmittersS2C> {
    override fun receive(
        payload: PacketParticleEmittersS2C,
        context: ClientPlayNetworking.Context
    ) {
        when (payload.type) {
            PacketParticleEmittersS2C.PacketType.CHANGE_OR_CREATE -> handleChangeOrCreate(payload, context)
            PacketParticleEmittersS2C.PacketType.REMOVE -> handleRemove(payload.emitter)
        }

    }

    fun handleChangeOrCreate(payload: PacketParticleEmittersS2C, context: ClientPlayNetworking.Context) {
        ParticleEmittersManager.createOrChangeClient(payload.emitter, context.player().world)
    }

    fun handleRemove(emitter: ParticleEmitters) {
        ParticleEmittersManager.clientEmitters[emitter.uuid]?.cancelled = true
    }
}