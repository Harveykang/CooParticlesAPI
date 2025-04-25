package cn.coostack.cooparticlesapi.particles.impl

import cn.coostack.cooparticlesapi.particles.ControlableParticle
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleFactory
import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.Vec3d
import java.util.UUID

class ControlableCloudParticle(
    world: ClientWorld,
    pos: Vec3d,
    velocity: Vec3d,
    controlUUID: UUID,
    val provider: SpriteProvider
) :
    ControlableParticle(world, pos, velocity, controlUUID) {

    init {
        setSprite(provider.getSprite(age, maxAge))
        controler.addPreTickAction {
            setSpriteForAge(provider)
        }
    }

    init {
        setSprite(provider.getSprite(age, maxAge))
        controler.addPreTickAction {
            setSpriteForAge(provider)
        }
    }

    class Factory(val provider: SpriteProvider) : ParticleFactory<ControlableCloudEffect> {
        override fun createParticle(
            parameters: ControlableCloudEffect,
            world: ClientWorld,
            x: Double,
            y: Double,
            z: Double,
            velocityX: Double,
            velocityY: Double,
            velocityZ: Double
        ): Particle {
            return ControlableCloudParticle(
                world,
                Vec3d(x, y, z),
                Vec3d(velocityX, velocityY, velocityZ),
                parameters.controlUUID,
                provider
            )
        }
    }

}