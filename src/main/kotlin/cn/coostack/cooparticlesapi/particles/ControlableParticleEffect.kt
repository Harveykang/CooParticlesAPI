package cn.coostack.cooparticlesapi.particles

import net.minecraft.particle.ParticleEffect
import java.util.UUID

abstract class ControlableParticleEffect(val controlUUID: UUID) : ParticleEffect {
}