package cn.coostack.cooparticlesapi.network.packet.client.listener

import cn.coostack.cooparticlesapi.network.packet.PacketParticleS2C
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object ClientParticlePacketHandler : ClientPlayNetworking.PlayPayloadHandler<PacketParticleS2C> {
    override fun receive(packet: PacketParticleS2C, context: ClientPlayNetworking.Context) {
        val player = context.player() ?: return
        val world = player.world
        world.addParticle(
            packet.type, true, packet.pos.x, packet.pos.y, packet.pos.z,
            packet.velocity.x, packet.velocity.y, packet.velocity.z,
        )
    }
}