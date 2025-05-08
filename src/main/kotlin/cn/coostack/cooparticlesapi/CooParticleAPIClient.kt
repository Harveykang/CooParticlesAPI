package cn.coostack.cooparticlesapi

import cn.coostack.cooparticlesapi.network.packet.PacketParticleGroupS2C
import cn.coostack.cooparticlesapi.network.packet.PacketParticleS2C
import cn.coostack.cooparticlesapi.network.packet.PacketParticleStyleS2C
import cn.coostack.cooparticlesapi.network.packet.client.listener.ClientParticleGroupPacketHandler
import cn.coostack.cooparticlesapi.network.packet.client.listener.ClientParticlePacketHandler
import cn.coostack.cooparticlesapi.network.packet.client.listener.ClientParticleStylePacketHandler
import cn.coostack.cooparticlesapi.network.particle.style.ParticleStyleManager
import cn.coostack.cooparticlesapi.particles.CooModParticles
import cn.coostack.cooparticlesapi.particles.control.group.ClientParticleGroupManager
import cn.coostack.cooparticlesapi.particles.impl.ControlableCloudEffect
import cn.coostack.cooparticlesapi.particles.impl.ControlableCloudParticle
import cn.coostack.cooparticlesapi.particles.impl.ControlableEnchantmentEffect
import cn.coostack.cooparticlesapi.particles.impl.ControlableEnchantmentParticle
import cn.coostack.cooparticlesapi.particles.impl.TestEndRodParticle
import cn.coostack.cooparticlesapi.test.particle.client.BarrierSwordGroupClient
import cn.coostack.cooparticlesapi.test.particle.client.ScaleCircleGroupClient
import cn.coostack.cooparticlesapi.test.particle.client.SequencedMagicCircleClient
import cn.coostack.cooparticlesapi.test.particle.client.TestGroupClient
import cn.coostack.cooparticlesapi.test.particle.style.ExampleSequencedStyle
import cn.coostack.cooparticlesapi.test.particle.style.ExampleStyle
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
            ParticleStyleManager.doTickClient()
        }
        ClientWorldEvents.AfterClientWorldChange { _, _ ->
            ClientParticleGroupManager.clearAllVisible()
        }
        ParticleFactoryRegistry.getInstance()
            .register(CooModParticles.testEndRod, ParticleFactoryRegistry.PendingParticleFactory {
                return@PendingParticleFactory TestEndRodParticle.Factory(it)
            })
        ParticleFactoryRegistry.getInstance()
            .register(CooModParticles.enchantment, ParticleFactoryRegistry.PendingParticleFactory {
                return@PendingParticleFactory ControlableEnchantmentParticle.Factory(it)
            })
        ParticleFactoryRegistry.getInstance()
            .register(CooModParticles.controlableCloud, ParticleFactoryRegistry.PendingParticleFactory {
                return@PendingParticleFactory ControlableCloudParticle.Factory(it)
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
        ClientParticleGroupManager.register(
            SequencedMagicCircleClient::class.java, SequencedMagicCircleClient.Provider()
        )

        ParticleStyleManager.register(ExampleStyle::class.java, ExampleStyle.Provider())
        ParticleStyleManager.register(ExampleSequencedStyle::class.java, ExampleSequencedStyle.Provider())
        CooModParticles.reg()
    }


    private fun particleGroupPacketListener() {
        ClientPlayNetworking.registerGlobalReceiver(
            PacketParticleGroupS2C.payloadID,
            ClientParticleGroupPacketHandler
        )
        ClientPlayNetworking.registerGlobalReceiver(
            PacketParticleStyleS2C.payloadID,
            ClientParticleStylePacketHandler
        )
        ClientPlayNetworking.registerGlobalReceiver(
            PacketParticleS2C.payloadID,
            ClientParticlePacketHandler
        )
    }


}