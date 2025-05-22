package cn.coostack.cooparticlesapi.test.entity

import net.minecraft.entity.AnimationState
import net.minecraft.entity.EntityPose
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.AnimalMateGoal
import net.minecraft.entity.ai.goal.LookAroundGoal
import net.minecraft.entity.ai.goal.LookAtEntityGoal
import net.minecraft.entity.ai.goal.SwimGoal
import net.minecraft.entity.ai.goal.TemptGoal
import net.minecraft.entity.ai.goal.WanderAroundGoal
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import kotlin.math.min

class TestEntity(entityType: EntityType<out PathAwareEntity>, world: World) : PathAwareEntity(entityType, world) {

    companion object {
        @JvmStatic
        val state = AnimationState()

        @JvmStatic
        fun createDefaultMobAttributes(): DefaultAttributeContainer.Builder {
            return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 500.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 20.0)

        }
    }

    var timeout = 0

    private fun setupAnimation() {
        timeout = this.random.nextInt(40) + 40
        if (timeout <= 0) {
            state.start(this.age)
        } else {
            timeout--
        }
    }


    override fun tick() {
        super.tick()
        setupAnimation()
    }


    override fun updateLimbs(posDelta: Float) {
        super.updateLimbs(posDelta)
        val pose = if (this.pose == EntityPose.STANDING) min(posDelta * 6f, 1f) else 0f
        this.limbAnimator.updateLimbs(pose,0.2f )
    }

    override fun initGoals() {
        super.initGoals()
        goalSelector.apply {
            add(0, SwimGoal(this@TestEntity))
            add(5, WanderAroundGoal(this@TestEntity, 3.0))
            add(6, LookAtEntityGoal(this@TestEntity, PlayerEntity::class.java, 3f))
            add(7, LookAroundGoal(this@TestEntity))
        }
//        goalSelector.add(1, TemptGoal(this,1.25,))

    }

}