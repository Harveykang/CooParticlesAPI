package cn.coostack.particles.impl

import cn.coostack.particles.ControlableParticle
import net.minecraft.client.particle.Particle
import net.minecraft.client.particle.ParticleFactory
import net.minecraft.client.particle.ParticleTextureSheet
import net.minecraft.client.particle.SpriteProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.Vec3d
import java.util.*

class TestEndRodParticle(
    world: ClientWorld,
    pos: Vec3d,
    velocity: Vec3d,
    controlUUID: UUID,
    val provider: SpriteProvider
) :
    ControlableParticle(world, pos, velocity, controlUUID) {
    override fun getType(): ParticleTextureSheet {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE
    }

    init {
        setSprite(provider.getSprite(0,120))
        controler.addPreTickAction {
            setSpriteForAge(provider)
        }
    }

    class Factory(val provider: SpriteProvider) : ParticleFactory<TestEndRodEffect> {
        override fun createParticle(
            parameters: TestEndRodEffect,
            world: ClientWorld,
            x: Double,
            y: Double,
            z: Double,
            velocityX: Double,
            velocityY: Double,
            velocityZ: Double
        ): Particle {
            return TestEndRodParticle(
                world,
                Vec3d(x, y, z),
                Vec3d(velocityX, velocityY, velocityZ),
                parameters.controlUUID,
                provider
            )
        }

    }
}