package cn.coostack.particles.control.group.impl

import cn.coostack.network.buffer.ParticleControlerDataBuffer
import cn.coostack.particles.ParticleDisplayer
import cn.coostack.particles.control.group.ControlableParticleGroup
import cn.coostack.particles.control.group.ControlableParticleGroupProvider
import cn.coostack.particles.impl.TestEndRodEffect
import cn.coostack.test.util.Math3DUtil
import cn.coostack.test.util.RelativeLocation
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
            return group
        }
    }

    override fun loadParticleLocations(): Map<ParticleRelativeData, RelativeLocation> {
        val res = mutableMapOf<ParticleRelativeData, RelativeLocation>()
        val points = Math3DUtil.getCycloidGraphic(3.0, 5.0, -2, 3, 360, .5)
        points.forEach {
            val withData = withEffect({ ParticleDisplayer.withSingle(TestEndRodEffect(it)) }) {
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