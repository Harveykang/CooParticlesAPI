package cn.coostack.cooparticlesapi.test.particle.client

import cn.coostack.cooparticlesapi.network.buffer.ParticleControlerDataBuffer
import cn.coostack.cooparticlesapi.particles.CooModParticles
import cn.coostack.cooparticlesapi.particles.ParticleDisplayer
import cn.coostack.cooparticlesapi.particles.control.group.ControlableParticleGroup
import cn.coostack.cooparticlesapi.particles.control.group.ControlableParticleGroupProvider
import cn.coostack.cooparticlesapi.particles.impl.ControlableCloudEffect
import cn.coostack.cooparticlesapi.particles.impl.ControlableCloudParticle
import cn.coostack.cooparticlesapi.particles.impl.ControlableEnchantmentEffect
import cn.coostack.cooparticlesapi.particles.impl.TestEndRodEffect
import cn.coostack.cooparticlesapi.utils.Math3DUtil
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import cn.coostack.cooparticlesapi.utils.builder.PointsBuilder
import net.minecraft.client.particle.ParticleTextureSheet
import org.joml.Vector3f
import java.util.*
import kotlin.math.PI

class ScaleCircleGroupClient(uuid: UUID, val bindPlayer: UUID) : ControlableParticleGroup(uuid) {
    internal var anTick = 0
    internal var anMaxTick = 30

    class Provider : ControlableParticleGroupProvider {
        override fun createGroup(
            uuid: UUID,
            args: Map<String, ParticleControlerDataBuffer<*>>
        ): ControlableParticleGroup {
            val bindPlayer = args["bind_player"]!!.loadedValue as UUID
            val group = ScaleCircleGroupClient(uuid, bindPlayer)
            group.anTick = args["an_tick"]!!.loadedValue as Int
            group.maxTick = args["max_tick"]!!.loadedValue as Int
            group.tick = args["tick"]!!.loadedValue as Int
            return group
        }

        override fun changeGroup(group: ControlableParticleGroup, args: Map<String, ParticleControlerDataBuffer<*>>) {
        }
    }

    override fun loadParticleLocations(): Map<ParticleRelativeData, RelativeLocation> {
        val res = mutableMapOf<ParticleRelativeData, RelativeLocation>()
//        val points = Math3DUtil.getCycloidGraphic(3.0, 5.0, -2, 3, 120, .5)
//        val points = Math3DUtil.getDiscreteCircleXZ(6.0,1080,3.0)
        val points = ArrayList<RelativeLocation>()
        points.addAll(
            Math3DUtil.getCircleXZ(4.0, 360)
        )
        val p3 = ArrayList<RelativeLocation>()
        p3.addAll(Math3DUtil.getPolygonInCircleLocations(3, 120, 4.0))
        p3.addAll(
            PointsBuilder.of(Math3DUtil.getPolygonInCircleLocations(3, 120, 4.0))
                .rotateAsAxis(PI / 3)
                .create()
        )
        p3.forEach {
            val withData = withEffect({ ParticleDisplayer.withSingle(TestEndRodEffect(it)) }) {
                color = Math3DUtil.colorOf(0, 255, 255)
                maxAge = 120
                size = 0.1f
                particleAlpha = 0.8f
                textureSheet = ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT
            }
            res[withData] = it
        }
//        points.addAll(
//            Math3DUtil.getBallLocations(3.0,32)
//        )
//        points.addAll(
//            Math3DUtil.getCircleXZ(7.0, 720)
//        )
//        points.addAll(
//            Math3DUtil.getCircleXZ(6.0, 360)
//        )
//
//        Math3DUtil.connectLines(
//            Math3DUtil.getCircleXZ(7.0, 16),
//            Math3DUtil.rotateAsAxis(Math3DUtil.getCircleXZ(6.0, 8), RelativeLocation.yAxis(), -PI / 16),
//            10
//        ).forEach {
//            points.addAll(it)
//        }
//
//        val p2 = ArrayList<RelativeLocation>()
//        p2.apply {
//            addAll(Math3DUtil.getCircleXZ(7.0, 16))
//            addAll(Math3DUtil.rotateAsAxis(Math3DUtil.getCircleXZ(6.0, 8), RelativeLocation.yAxis(), -PI / 16))
//        }
//        p2.forEach {
//            val withData = withEffect({ ParticleDisplayer.withSingle(ControlableCloudEffect(it)) }) {
//                color = Math3DUtil.colorOf(255, 0, 0)
//                maxAge = 120
//                size = 0.3f
//                particleAlpha = 0.8f
//                textureSheet = ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT
//            }
//            res[withData] = it
//        }
        val random = Random(System.currentTimeMillis())
        points.forEach {
            val withData = withEffect({ ParticleDisplayer.withSingle(ControlableEnchantmentEffect(it)) }) {
                color = Vector3f(100 / 255f, 100 / 255f, 255 / 255f)
                maxAge = 120
                size = 0.2f
                particleAlpha = 0.8f
                textureSheet = ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT
            }.withControler { c ->
//                c.addPreTickAction {
//                    currentAge++
//                    if (currentAge >= maxAge) {
//                        this@ScaleCircleGroupClient.canceled = true
//                    }
//                }
                c.controlAction {
                    currentAge = random.nextInt(maxAge)
                }
            }
            res[withData] = it
        }
        return res
    }


    override fun beforeDisplay(locations: Map<ParticleRelativeData, RelativeLocation>) {
        scale = 1.0 / anMaxTick
    }

    override fun onGroupDisplay() {
        addPreTickAction {
            val player = world!!.getPlayerByUuid(bindPlayer) ?: return@addPreTickAction
            // 同步位置
            teleportTo(player.pos)
            // 在变大的过程中也能旋转
            rotateParticlesAsAxis(Math.toRadians(10.0))
            if (tick++ >= maxTick - anMaxTick) {
                scale(scale - 1.0 / anMaxTick)
                return@addPreTickAction
            }
            // 旋转时间设定
            if (anTick++ >= anMaxTick) {
                anTick = anMaxTick
                return@addPreTickAction
            }
            scale(scale + 1.0 / anMaxTick)
        }
    }
}