package cn.coostack.cooparticlesapi.test.entity

import cn.coostack.cooparticlesapi.test.entity.goal.TestPlayerAttackGoal
import net.minecraft.entity.AnimationState
import net.minecraft.entity.EntityPose
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.goal.ActiveTargetGoal
import net.minecraft.entity.ai.goal.LookAroundGoal
import net.minecraft.entity.ai.goal.LookAtEntityGoal
import net.minecraft.entity.ai.goal.RevengeGoal
import net.minecraft.entity.ai.goal.SwimGoal
import net.minecraft.entity.ai.goal.WanderAroundGoal
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedDataHandler
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World
import kotlin.math.min

class TestPlayerEntity(entityType: EntityType<out PathAwareEntity>, world: World) : PathAwareEntity(entityType, world) {
    companion object {
        @JvmStatic
        val IS_ATTACKING = DataTracker.registerData(
            TestPlayerEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN
        )


        @JvmStatic
        fun createDefaultMobAttributes(): DefaultAttributeContainer.Builder {
            return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 10.0)
        }
    }

    val idleState = AnimationState()

    val attack = AnimationState()
    override fun setAttacking(attacking: Boolean) {
        super.setAttacking(attacking)
        this.dataTracker.set(IS_ATTACKING, attacking)
    }

    override fun isAttacking(): Boolean {
        return this.dataTracker.get(IS_ATTACKING)
    }

    override fun initDataTracker(builder: DataTracker.Builder?) {
        super.initDataTracker(builder)
        builder?.add(IS_ATTACKING, false)
    }

    var timeout = 0
    var attackTick = 0
    private fun setupAnimation() {
        if (timeout <= 0) {
            timeout = this.random.nextInt(40) + 80
            idleState.start(this.age)
        } else {
            timeout--
        }
        if (isAttacking && attackTick <= 0) {
            attackTick = 10
            attack.start(this.age)
        } else {
            attackTick--
        }
        if (!isAttacking) {
            attack.stop()
            attackTick = 0
        }
    }

    override fun tick() {
        super.tick()
        setupAnimation()
    }

    override fun updateLimbs(posDelta: Float) {
        super.updateLimbs(posDelta)
        val pose = if (this.pose == EntityPose.STANDING) min(posDelta * 6f, 5f) else 0f
        this.limbAnimator.updateLimbs(pose, 0.02f)
    }

    override fun initGoals() {
        super.initGoals()
        goalSelector.apply {
            add(0, SwimGoal(this@TestPlayerEntity))
            add(5, WanderAroundGoal(this@TestPlayerEntity, 1.0))
            add(6, LookAtEntityGoal(this@TestPlayerEntity, PlayerEntity::class.java, 3f))
            add(7, LookAroundGoal(this@TestPlayerEntity))
            add(1, TestPlayerAttackGoal(this@TestPlayerEntity, 1.0, false))
        }
        targetSelector.add(1, RevengeGoal(this))
        targetSelector.add(1, ActiveTargetGoal(this, LivingEntity::class.java, true))
    }
}