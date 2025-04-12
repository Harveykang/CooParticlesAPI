package cn.coostack.test.particle.client

import cn.coostack.network.buffer.ParticleControlerDataBuffer
import cn.coostack.particles.ParticleDisplayer
import cn.coostack.particles.control.group.ControlableParticleGroup
import cn.coostack.particles.control.group.ControlableParticleGroupProvider
import cn.coostack.particles.impl.TestEndRodEffect
import cn.coostack.test.util.Math3DUtil
import cn.coostack.test.util.RelativeLocation
import net.minecraft.util.math.Vec3d
import org.joml.Vector3f
import java.util.*

class BarrierSwordGroupClient(uuid: UUID, var targetEntityID: Int?) : ControlableParticleGroup(uuid) {
    private var defaultDirection: Vec3d = Vec3d(0.0, 1.0, 0.0)

    class Provider : ControlableParticleGroupProvider {
        override fun createGroup(
            uuid: UUID,
            args: Map<String, ParticleControlerDataBuffer<*>>
        ): ControlableParticleGroup {
            return BarrierSwordGroupClient(uuid, null).also {
                it.defaultDirection = args["direction"]!!.loadedValue as Vec3d
            }
        }

        override fun changeGroup(group: ControlableParticleGroup, args: Map<String, ParticleControlerDataBuffer<*>>) {
            group as BarrierSwordGroupClient
            if (args.containsKey("target_entity_id")) {
                group.targetEntityID = args["target_entity_id"]!!.loadedValue as Int
            }
        }

    }

    override fun loadParticleLocations(): Map<ParticleRelativeData, RelativeLocation> {
        val res = HashMap<ParticleRelativeData, RelativeLocation>()
        val l1 = Math3DUtil.getLineLocations(Vec3d.ZERO, Vec3d(0.0, -1.0, 0.0), 10)
        val l2 = Math3DUtil.getLineLocations(Vec3d.ZERO, Vec3d(0.0, 3.0, 0.0), 60)
        val l3 = Math3DUtil.getLineLocations(Vec3d.ZERO, Vec3d(-1.0, 0.0, 0.0), 20)
        val l4 = Math3DUtil.getLineLocations(Vec3d.ZERO, Vec3d(1.0, 0.0, 0.0), 20)
        val total = l1 + l2 + l3 + l4
        total.forEach { loc ->
            res[withEffect({ ParticleDisplayer.withSingle(TestEndRodEffect(it)) }) {
                color = Vector3f(1f, 1f, 1f)
            }] = loc
        }

        return res
    }

    override fun onGroupDisplay() {
        axis = RelativeLocation.yAxis()
        addPreTickAction {
            if (targetEntityID != null) {
                val entity = world!!.getEntityById(targetEntityID!!) ?: return@addPreTickAction
                val direction = origin.relativize(entity.pos)
                rotateParticlesToPoint(RelativeLocation.of(direction))
            } else {
                rotateParticlesToPoint(RelativeLocation.of(defaultDirection))
            }
        }
    }
}