package cn.coostack.cooparticlesapi.items

import cn.coostack.cooparticlesapi.CooParticleAPI
import cn.coostack.cooparticlesapi.barrages.HitBox
import cn.coostack.cooparticlesapi.network.particle.emitters.ControlableParticleData
import cn.coostack.cooparticlesapi.network.particle.emitters.ParticleEmittersManager
import cn.coostack.cooparticlesapi.network.particle.emitters.impl.PhysicsParticleEmitters
import cn.coostack.cooparticlesapi.network.particle.emitters.impl.PhysicsParticleEmitters.Companion.EARTH_GRAVITY
import cn.coostack.cooparticlesapi.network.particle.emitters.impl.PhysicsParticleEmitters.Companion.SEA_AIR_DENSITY
import cn.coostack.cooparticlesapi.network.particle.emitters.type.EmittersShootType
import cn.coostack.cooparticlesapi.network.particle.emitters.type.EmittersShootTypes
import cn.coostack.cooparticlesapi.particles.impl.TestEndRodEffect
import cn.coostack.cooparticlesapi.utils.Math3DUtil
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class TestParticleItem(settings: Settings) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (world.isClient) {
            return TypedActionResult.success(user.getStackInHand(hand))
        }
//        val serverGroup = ScaleCircleGroupServer(user.uuid)
//        ServerParticleGroupManager.addParticleGroup(
//            serverGroup, user.pos, world as ServerWorld
//        )
        val simple = PhysicsParticleEmitters(
            user.eyePos, user.world,
            ControlableParticleData().apply {
//                this.velocity = user.rotationVector.normalize().multiply(0.8)
                this.maxAge = 70
                this.color = Math3DUtil.colorOf(244, 100, 244)
                this.effect = TestEndRodEffect(this.uuid)
            }
        )
        simple.maxTick = 240
        simple.apply {
            this.gravity = 0.0
            this.airDensity = 0.0
//            this.wind = Vec3d(0.0, 1.0, 4.0)
//            this.shootType = EmittersShootTypes.line(user.rotationVector, 0.4)
            this.shootType = EmittersShootTypes.point()
            val v = user.rotationVector.normalize()
            evalEmittersXWithT = "(t / 10.0)*${v.x}"
            evalEmittersYWithT = "(t / 10.0)*${v.y}"
            evalEmittersZWithT = "(t / 10.0)*${v.z}"
            setup()
//            this.shootType = EmittersShootTypes.math(
//                "5 * COS(RAD(i * c * 10))",
//                "0",
//                "5 * SIN(RAD(i * c * 10))",
//                "4*(ox-x)/SQRT((ox-x)^2+(oy-z)^2)",
//                "0.1",
//                "4*(oz-z)/SQRT((ox-x)^2+(oy-z)^2)",
//            )
//            this.shootType = EmittersShootTypes.math(
//                "5 * COS(RAD(i * c * 20))",
//                "0",
//                "5 * SIN(RAD(i * c * 20))",
//                "0",
//                "0",
//                "0",
//            )

//            this.shootType = EmittersShootTypes.box(
//                HitBox.of(128.0,10.0,128.0)
//            )
            this.delay = 2
            this.count = 128
            this.countRandom = 0
        }
        ParticleEmittersManager.spawnEmitters(simple)
        CooParticleAPI.scheduler.runTaskTimerMaxTick(5, 240) {
//            simple.templateData.size += 0.05f
            ParticleEmittersManager.updateEmitters(simple)
        }
        return super.use(world, user, hand)
    }

}