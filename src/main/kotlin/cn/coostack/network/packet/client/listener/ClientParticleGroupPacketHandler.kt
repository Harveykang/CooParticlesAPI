package cn.coostack.network.packet.client.listener

import cn.coostack.network.buffer.ParticleControlerDataBuffer
import cn.coostack.network.packet.PacketParticleGroupS2C
import cn.coostack.particles.control.ControlType
import cn.coostack.particles.control.group.ClientParticleGroupManager
import cn.coostack.particles.control.group.ControlableParticleGroup
import cn.coostack.test.util.RelativeLocation
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.util.math.Vec3d
import java.util.UUID
import cn.coostack.network.packet.PacketParticleGroupS2C.PacketArgsType

@Environment(EnvType.CLIENT)
object ClientParticleGroupPacketHandler : ClientPlayNetworking.PlayPayloadHandler<PacketParticleGroupS2C> {
    override fun receive(packet: PacketParticleGroupS2C, context: ClientPlayNetworking.Context) {
        // Group UUID
        val uuid = packet.uuid
        // 修改类型
        val type = packet.type
        when (type) {
            ControlType.CREATE -> handleCreate(uuid, packet.args)
            ControlType.CHANGE -> handleChange(uuid, packet.args)
            ControlType.REMOVE -> handleRemove(uuid)
        }
    }


    private fun handleCreate(
        groupUUID: UUID,
        args: Map<String, ParticleControlerDataBuffer<*>>
    ) {
        val pos = args[PacketArgsType.POS.toArgsName]?.loadedValue!! as Vec3d
        val world = MinecraftClient.getInstance().world!!
        val type = args[PacketArgsType.GROUP_TYPE.toArgsName]!!.loadedValue as String
        val currentTick = args[PacketArgsType.CURRENT_TICK.toArgsName]!!.loadedValue as Int
        val maxTick = args[PacketArgsType.MAX_TICK.toArgsName]!!.loadedValue as Int
        val groupClass = Class.forName(type)
        val builder = ClientParticleGroupManager.getBuilder(groupClass as Class<out ControlableParticleGroup>)!!
        val group = builder.createGroup(groupUUID, args)
        group.tick = currentTick
        group.maxTick = maxTick
        group.display(pos, world)
        ClientParticleGroupManager.addVisibleGroup(group)
    }

    private fun handleChange(
        groupUUID: UUID,
        args: Map<String, ParticleControlerDataBuffer<*>>
    ) {
        val targetGroup = ClientParticleGroupManager.getControlGroup(groupUUID) ?: return
        val argKeys = args.keys
        if (PacketArgsType.POS.toArgsName in argKeys) {
            val pos = args[PacketArgsType.POS.toArgsName]!!.loadedValue!! as Vec3d
            targetGroup.teleportGroupTo(pos)
        }
        if (PacketArgsType.ROTATE_TO.toArgsName in argKeys) {
            val to = args[PacketArgsType.ROTATE_TO.toArgsName]!!.loadedValue!! as Vec3d
            targetGroup.rotateParticlesToPoint(RelativeLocation.of(to))
        }
        if (PacketArgsType.ROTATE_AXIS.toArgsName in argKeys) {
            val angle = args[PacketArgsType.ROTATE_AXIS.toArgsName]!!.loadedValue as Double
            targetGroup.rotateParticlesAsAxis(angle)
        }
        if (PacketArgsType.AXIS.toArgsName in argKeys) {
            val axis = args[PacketArgsType.AXIS.toArgsName]!!.loadedValue!! as Vec3d
            targetGroup.axis = RelativeLocation.of(axis)
        }
        if (PacketArgsType.CURRENT_TICK.toArgsName in argKeys) {
            val tick = args[PacketArgsType.CURRENT_TICK.toArgsName]!!.loadedValue!! as Int
            targetGroup.tick = tick
        }
        if (PacketArgsType.MAX_TICK.toArgsName in argKeys) {
            val tick = args[PacketArgsType.MAX_TICK.toArgsName]!!.loadedValue!! as Int
            targetGroup.maxTick = tick
        }
        if (PacketArgsType.INVOKE.toArgsName in argKeys) {
            val targetMethodName = args[PacketArgsType.INVOKE.toArgsName]!!.loadedValue!! as String
            targetGroup::class.java.getDeclaredMethod(targetMethodName).apply {
                isAccessible = true
            }.invoke(targetGroup)
        }

        ClientParticleGroupManager.getBuilder(targetGroup::class.java)?.changeGroup(
            targetGroup, args
        )
    }

    private fun handleRemove(groupUUID: UUID) {
        ClientParticleGroupManager.removeVisible(groupUUID)
    }

}