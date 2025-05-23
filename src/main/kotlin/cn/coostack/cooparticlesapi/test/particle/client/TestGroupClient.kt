package cn.coostack.cooparticlesapi.test.particle.client

import cn.coostack.cooparticlesapi.network.buffer.ParticleControlerDataBuffer
import cn.coostack.cooparticlesapi.particles.ParticleDisplayer
import cn.coostack.cooparticlesapi.particles.control.group.ControlableParticleGroup
import cn.coostack.cooparticlesapi.particles.control.group.ControlableParticleGroupProvider
import cn.coostack.cooparticlesapi.particles.impl.TestEndRodEffect
import cn.coostack.cooparticlesapi.utils.Math3DUtil
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text
import org.joml.Vector3f
import java.util.*

class TestGroupClient(uuid: UUID, val bindPlayer: UUID) : ControlableParticleGroup(uuid) {

    class Provider : ControlableParticleGroupProvider {
        override fun createGroup(
            uuid: UUID,
            args: Map<String, ParticleControlerDataBuffer<*>>
        ): ControlableParticleGroup {
            val bindUUID = args["bindUUID"]!!.loadedValue as UUID
            return TestGroupClient(uuid, bindUUID)
        }

        override fun changeGroup(group: ControlableParticleGroup, args: Map<String, ParticleControlerDataBuffer<*>>) {
        }
    }

    override fun loadParticleLocations(): Map<ParticleRelativeData, RelativeLocation> {
        val r1 = 3.0
        val r2 = 5.0
        val w1 = -2
        val w2 = 3
        val scale = 1.0
        val count = 360
        val list = Math3DUtil.getCycloidGraphic(r1, r2, w1, w2, count, scale).onEach { it.y += 6 }
        val map = list.associateBy {
            withEffect({ ParticleDisplayer.Companion.withSingle(TestEndRodEffect(it)) }) {
                color = Vector3f(230 / 255f, 130 / 255f, 60 / 255f)
                maxAge = maxTick
            }
        }
        val mutable = map.toMutableMap()
        for (rel in Math3DUtil.computeCycloidVertices(r1, r2, w1, w2, count, scale)) {
            mutable[withEffect({ u -> ParticleDisplayer.Companion.withGroup(TestSubGroupClient(u, bindPlayer)) }) {}] =
                rel.clone()
        }
        return mutable
    }


    override fun onGroupDisplay() {
        MinecraftClient.getInstance().player?.sendMessage(Text.of("发送粒子: ${this::class.java.name} 成功"))
        addPreTickAction {
            // 这种方法就是其他人看到的话粒子会显示在他们的头上而不是某个玩家的头上....
            val bindPlayerEntity = world!!.getPlayerByUuid(bindPlayer) ?: let {
                return@addPreTickAction
            }
            teleportTo(bindPlayerEntity.eyePos)
            rotateToWithAngle(
                RelativeLocation.of(bindPlayerEntity.rotationVector),
                Math.toRadians(10.0)
            )
        }
    }
}