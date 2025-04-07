package cn.coostack.particles

import net.minecraft.particle.ParticleEffect
import java.util.UUID

abstract class ControlableParticleEffect(val controlUUID: UUID) : ParticleEffect {
}