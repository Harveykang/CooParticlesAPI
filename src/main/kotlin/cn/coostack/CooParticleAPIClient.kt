package cn.coostack

import cn.coostack.network.packet.PacketParticleGroupS2C
import cn.coostack.network.packet.client.listener.ClientParticleGroupPacketHandler
import cn.coostack.particles.ModParticles
import cn.coostack.particles.control.group.ClientParticleGroupManager
import cn.coostack.particles.impl.TestEndRodParticle
import cn.coostack.test.particle.client.ScaleCircleGroupClient
import cn.coostack.test.particle.client.TestGroupClient
import cn.coostack.test.particle.client.BarrierSwordGroupClient
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
        ClientPlayNetworking.registerGlobalReceiver(PacketParticleGroupS2C.payloadID, ClientParticleGroupPacketHandler)
    }


}