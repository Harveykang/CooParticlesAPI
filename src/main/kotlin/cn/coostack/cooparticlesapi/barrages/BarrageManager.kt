package cn.coostack.cooparticlesapi.barrages

import cn.coostack.cooparticlesapi.network.particle.ServerParticleGroupManager
import com.google.common.collect.ConcurrentHashMultiset

object BarrageManager {
    private val barriers = ConcurrentHashMultiset.create<Barrage>()

    fun spawn(barrage: Barrage) {
        spawnOnWorld(barrage)
        barriers.add(barrage)
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

    private fun spawnOnWorld(barrage: Barrage) {
        ServerParticleGroupManager.addParticleGroup(
            barrage.bindControl,
            barrage.loc,
            barrage.world
        )
        barrage.lunch = true
    }
}
