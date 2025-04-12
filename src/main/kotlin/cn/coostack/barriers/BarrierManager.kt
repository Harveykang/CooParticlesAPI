package cn.coostack.barriers

import cn.coostack.network.particle.ServerParticleGroupManager
import com.google.common.collect.ConcurrentHashMultiset
import net.minecraft.world.World

object BarrierManager {
    private val barriers = ConcurrentHashMultiset.create<Barrier>()

    fun spawn(barrier: Barrier) {
        spawnOnWorld(barrier)
        barriers.add(barrier)
    }


    fun doTick() {
        val iterator = barriers.iterator()
        while (iterator.hasNext()) {
            val barrier = iterator.next()
            barrier.tick()
            if (!barrier.valid) {
                iterator.remove()
            }
        }
    }

    private fun spawnOnWorld(barrier: Barrier) {
        ServerParticleGroupManager.addParticleGroup(
            barrier.bindControl,
            barrier.loc,
            barrier.world
        )
        barrier.lunch = true
    }
}
