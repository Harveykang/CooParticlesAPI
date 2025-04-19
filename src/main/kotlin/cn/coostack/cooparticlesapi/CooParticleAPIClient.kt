package cn.coostack.cooparticlesapi

import cn.coostack.cooparticlesapi.network.packet.PacketParticleGroupS2C
import cn.coostack.cooparticlesapi.network.packet.PacketParticleS2C
import cn.coostack.cooparticlesapi.network.packet.client.listener.ClientParticleGroupPacketHandler
import cn.coostack.cooparticlesapi.network.packet.client.listener.ClientParticlePacketHandler
import cn.coostack.cooparticlesapi.particles.ModParticles
import cn.coostack.cooparticlesapi.particles.control.group.ClientParticleGroupManager
import cn.coostack.cooparticlesapi.particles.impl.TestEndRodParticle
import cn.coostack.cooparticlesapi.test.particle.client.BarrierSwordGroupClient
import cn.coostack.cooparticlesapi.test.particle.client.ScaleCircleGroupClient
import cn.coostack.cooparticlesapi.test.particle.client.TestGroupClient
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry

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
        ClientParticleGroupManager.register(
            BarrierSwordGroupClient::class.java, BarrierSwordGroupClient.Provider()
        )
        ModParticles.reg()
    }


    private fun particleGroupPacketListener() {
        ClientPlayNetworking.registerGlobalReceiver(
            PacketParticleGroupS2C.Companion.payloadID,
            ClientParticleGroupPacketHandler
        )
        ClientPlayNetworking.registerGlobalReceiver(PacketParticleS2C.Companion.payloadID, ClientParticlePacketHandler)
    }


}