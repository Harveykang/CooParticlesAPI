package cn.coostack

import cn.coostack.network.packet.PacketParticleGroupS2C
import cn.coostack.network.packet.client.listener.ClientParticleGroupPacketHandler
import cn.coostack.particles.ModParticles
import cn.coostack.particles.control.group.ClientParticleGroupManager
import cn.coostack.particles.control.group.impl.ScaleCircleGroupClient
import cn.coostack.particles.control.group.impl.TestGroupClient
import cn.coostack.particles.impl.TestEndRodParticle
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import org.slf4j.LoggerFactory

object CooParticleAPIClient : ClientModInitializer {

    override fun onInitializeClient() {
        particleGroupPacketListener()
        ClientTickEvents.START_WORLD_TICK.register {
            ClientParticleGroupManager.doClientTick()
        }
        ClientWorldEvents.AfterClientWorldChange { _, _ ->
            ClientParticleGroupManager.clearAllVisible()
        }
        ParticleFactoryRegistry.getInstance()
            .register(ModParticles.testEndRod, ParticleFactoryRegistry.PendingParticleFactory {
                return@PendingParticleFactory TestEndRodParticle.Factory(it)
            })
        ClientParticleGroupManager.register(
            TestGroupClient::class.java, TestGroupClient.Provider()
        )
        ClientParticleGroupManager.register(
            ScaleCircleGroupClient::class.java, ScaleCircleGroupClient.Provider()
        )
        ModParticles.reg()
    }


    private fun particleGroupPacketListener() {
        ClientPlayNetworking.registerGlobalReceiver(PacketParticleGroupS2C.payloadID, ClientParticleGroupPacketHandler)
    }


}