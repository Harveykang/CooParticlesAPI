package cn.coostack.test.particle.server

import cn.coostack.barriers.HitBox
import cn.coostack.network.buffer.ParticleControlerDataBuffer
import cn.coostack.network.buffer.ParticleControlerDataBuffers
import cn.coostack.network.particle.ServerParticleGroup
import cn.coostack.particles.control.group.ControlableParticleGroup
import cn.coostack.test.particle.client.BarrierSwordGroupClient
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.Vec3d
import java.util.function.Predicate

class BarrierSwordGroupServer(
    private val box: HitBox,
    private val filter: Predicate<LivingEntity>,
    var direction: Vec3d
) :
    ServerParticleGroup(64.0) {

    override fun tick() {
        val entities = world!!.getEntitiesByClass(LivingEntity::class.java, box.ofBox(pos), filter)
        var closestEntity: LivingEntity? = null

        for (entity in entities) {
            if (closestEntity == null) {
                closestEntity = entity
                continue
            }
            if (pos.distanceTo(closestEntity.pos) > pos.distanceTo(entity.pos)) {
                closestEntity = entity
            }
        }
        if (closestEntity == null) {
            return
        }
        direction = pos.relativize(closestEntity.pos).normalize().multiply(0.5)
        change({}, mapOf("target_entity_id" to ParticleControlerDataBuffers.int(closestEntity.id)))
    }

    override fun otherPacketArgs(): Map<String, ParticleControlerDataBuffer<out Any>> {
        return mapOf("direction" to ParticleControlerDataBuffers.vec3d(direction))
    }

    override fun getClientType(): Class<out ControlableParticleGroup> {
        return BarrierSwordGroupClient::class.java
    }
}