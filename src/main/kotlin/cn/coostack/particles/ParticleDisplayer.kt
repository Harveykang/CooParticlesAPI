package cn.coostack.particles

import cn.coostack.particles.control.ControlParticleManager
import cn.coostack.particles.control.group.ControlableParticleGroup
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.ParticleEffect
import net.minecraft.util.math.Vec3d

/**
 * 为了更好的让ControlableParticleGroup 进行操作
 */
interface ParticleDisplayer {
    companion object {
        @JvmStatic
        fun withSingle(effect: ControlableParticleEffect): ParticleDisplayer {
            return SingleParticleDisplayer(effect)
        }

        @JvmStatic
        fun withGroup(group: ControlableParticleGroup): ParticleDisplayer {
            return ParticleGroupDisplayer(group)
        }
    }

    fun display(loc: Vec3d, world: ClientWorld): Controlable<*>?
    class SingleParticleDisplayer(val effect: ControlableParticleEffect) : ParticleDisplayer {
        /**
         * 始终返回null 因为此时粒子一定还未设置成功
         */
        override fun display(loc: Vec3d, world: ClientWorld): Controlable<ControlableParticle>? {
            world.addParticle(effect, loc.x, loc.y, loc.z, 0.0, 0.0, 0.0)
            return ControlParticleManager.getControl(effect.controlUUID)
        }
    }

    class ParticleGroupDisplayer(val group: ControlableParticleGroup) : ParticleDisplayer {
        override fun display(loc: Vec3d, world: ClientWorld): Controlable<ControlableParticleGroup> {
            group.display(loc, world)
            return group
        }
    }
}