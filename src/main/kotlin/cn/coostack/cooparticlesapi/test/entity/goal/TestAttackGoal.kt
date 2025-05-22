package cn.coostack.cooparticlesapi.test.entity.goal

import cn.coostack.cooparticlesapi.test.entity.TestEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.goal.MeleeAttackGoal
import net.minecraft.entity.mob.PathAwareEntity

class TestAttackGoal(entity: TestEntity, speed: Double, pauseWhenMobIdle: Boolean) :
    MeleeAttackGoal(entity, speed, pauseWhenMobIdle) {
    var delay = 20
    var cd = 20
    var find = false

    override fun start() {
        super.start()
        delay = 20
        cd = 20
    }

    override fun stop() {
        super.stop()
        mob as TestEntity
        mob.setAttacking(false)
    }

    override fun tick() {
        super.tick()
        if (find){
        }
    }


    override fun attack(target: LivingEntity?) {
        super.attack(target)

    }
}