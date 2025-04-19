package cn.coostack.cooparticlesapi.test.particle.client

import cn.coostack.cooparticlesapi.network.buffer.ParticleControlerDataBuffer
import cn.coostack.cooparticlesapi.particles.ParticleDisplayer
import cn.coostack.cooparticlesapi.particles.control.group.ControlableParticleGroup
import cn.coostack.cooparticlesapi.particles.control.group.ControlableParticleGroupProvider
import cn.coostack.cooparticlesapi.particles.impl.TestEndRodEffect
import cn.coostack.cooparticlesapi.utils.Math3DUtil
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import org.joml.Vector3f
import java.util.*

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
        val points = Math3DUtil.getCycloidGraphic(3.0, 5.0, -2, 3, 720, .5)
        points.forEach {
            val withData = withEffect({ ParticleDisplayer.Companion.withSingle(TestEndRodEffect(it)) }) {
                color = Vector3f(100 / 255f, 100 / 255f, 255 / 255f)
                maxAge = 120
            }.withControler { c ->
                c.addPreTickAction {
                    currentAge++
                    if (currentAge >= maxAge) {
                        this@ScaleCircleGroupClient.canceled = true
                    }
                }
            }
            res[withData] = it.multiply(1.0 / anMaxTick)
        }
        return res
    }


    override fun onGroupDisplay() {
        // 保存最初始的长度
        // 用于做稳定的长度添加
        val firstClone = particlesLocations.map {
            it.key to it.value.length()
        }.toMap()
        addPreTickAction {
            val player = world!!.getPlayerByUuid(bindPlayer) ?: return@addPreTickAction
            // 同步位置
            teleportTo(player.pos)
            // 在变大的过程中也能旋转
            rotateParticlesAsAxis(Math.toRadians(10.0))
            if (tick++ >= maxTick - anMaxTick) {
                particlesLocations.forEach {
                    val cloneLength = firstClone[it.key]!!
                    // 确保添加的方向和长度正确
                    it.value.remove(it.value.normalize().multiply(cloneLength))
                }
                return@addPreTickAction
            }
            // 旋转时间设定
            if (anTick++ >= anMaxTick) {
                anTick = anMaxTick
                return@addPreTickAction
            }
            particlesLocations.forEach {
                val cloneLength = firstClone[it.key]!!
                // 确保添加的方向和长度正确
                it.value.add(it.value.normalize().multiply(cloneLength))
            }
        }
    }
}