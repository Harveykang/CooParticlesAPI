package cn.coostack.network.particle

import cn.coostack.network.buffer.ParticleControlerDataBuffer
import cn.coostack.network.buffer.ParticleControlerDataBuffers
import cn.coostack.network.packet.PacketParticleGroupS2C
import cn.coostack.particles.control.ControlType
import cn.coostack.particles.control.group.ControlableParticleGroup
import cn.coostack.test.util.RelativeLocation
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.LivingEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.UUID

/**
 * @param clientGroup 客户端渲染的类型
 * @param uuid 粒子组合唯一标识符
 * @param visibleRange 玩家可见范围 （origin)
 */
abstract class ServerParticleGroup(
    var visibleRange: Double = 32.0
) {
    val uuid: UUID = UUID.randomUUID()
    var pos: Vec3d = Vec3d.ZERO
        internal set
    var world: World? = null
        internal set
    var valid = true
        internal set
    var clientGroup: Class<out ControlableParticleGroup>? = null
        internal set
    var canceled = false
        internal set

    /**
     * 客户端渲染视角下的生命周期
     */
    var clientTick = 0
        internal set
    var clientMaxTick = 120
        internal set

    /**
     * 服务器内的生命周期
     * 同步数据时不会传输给客户端
     */
    var tick = 0
    var maxTick = 120

    var axis: RelativeLocation = RelativeLocation(0.0, 1.0, 0.0)
        internal set

    /**
     * 每个tick能做的事情
     * 例如绑定实体位置 旋转等
     */
    abstract fun tick()

    /**
     * 获取自定义包的其他参数 (用于服务器传输给客户端)
     */
    abstract fun otherPacketArgs(): Map<String, ParticleControlerDataBuffer<out Any>>

    /**
     * 当粒子组合在服务器创建并且发送到客户端时执行
     * 可以给客户端发送一些包修改
     */
    open fun onGroupDisplay(pos: Vec3d, world: ServerWorld) {}

    /**
     * 通过doTickAlive函数导致粒子组合到达最终生命周期时执行的函数
     * 不强制重写
     */
    open fun onTickAliveDeath() {}

    /**
     * 同步后客户端层面粒子死亡 (除非没开启tickAlive)
     * 服务器处理上同样
     */
    open fun onClientViewDeath() {}

    /**
     * 可以在abstract tick中执行
     */
    fun doTickClient() {
        if (clientTick++ >= clientMaxTick) {
            onClientViewDeath()
        }
    }

    /**
     * 可以在abstract tick中执行
     */
    fun doTickAlive() {
        if (tick++ >= maxTick) {
            kill()
            onTickAliveDeath()
        }
    }

    /**
     * 销毁包 (在update时会删除所有的可见)
     */
    fun kill() {
        canceled = true
        world ?: return
        val visible = ServerParticleGroupManager.filterVisiblePlayer(this)
        visible.forEach {
            val player = world!!.getPlayerByUuid(it) ?: return@forEach
            // 发销毁包
            val packet = PacketParticleGroupS2C(uuid, ControlType.REMOVE, mapOf())
            ServerPlayNetworking.send(player as ServerPlayerEntity, packet)
        }
    }

    /**
     * 跟随玩家在线状态. 世界跟换 删除粒子
     */
    fun withPlayerStats(player: ServerPlayerEntity) {
        if (player.isDisconnected) {
            kill()
        }

        if (player.world != world) {
            kill()
        }
    }

    fun withEntityStats(entity: LivingEntity) {
        if (entity.isDead) {
            kill()
        }

        if (entity.world != world) {
            kill()
        }

    }

    fun setAxis(axis: Vec3d) {
        this.axis = RelativeLocation.of(axis)
        // 发包同步客户端
        world ?: return
        val visible = ServerParticleGroupManager.filterVisiblePlayer(this)
        visible.forEach {
            val player = world!!.getPlayerByUuid(it) ?: return@forEach
            // 发销毁包
            val packet = PacketParticleGroupS2C(
                uuid, ControlType.CHANGE, mapOf(
                    PacketParticleGroupS2C.PacketArgsType.AXIS.toArgsName to ParticleControlerDataBuffers.vec3d(axis)
                )
            )
            ServerPlayNetworking.send(player as ServerPlayerEntity, packet)
        }
    }

    /**
     * 不发送到客户端
     */
    fun setPosOnServer(pos: Vec3d) {
        this.pos = pos
    }

    /**
     * 同步旋转后的中心点和对称轴
     */
    fun setRotateToOnServer(to: RelativeLocation) {
        axis = to.normalize()
    }


    /**
     * 出现了一些控制类之外的传送
     */
    fun teleportGroupTo(pos: Vec3d) {
        // 发包告知 所有可见客户端
        this.pos = pos
        world ?: return
        val visible = ServerParticleGroupManager.filterVisiblePlayer(this)
        visible.forEach {
            val player = world!!.getPlayerByUuid(it) ?: return@forEach
            // 发销毁包
            val packet = PacketParticleGroupS2C(
                uuid, ControlType.CHANGE, mapOf(
                    PacketParticleGroupS2C.PacketArgsType.POS.toArgsName to ParticleControlerDataBuffers.vec3d(pos)
                )
            )
            ServerPlayNetworking.send(player as ServerPlayerEntity, packet)
        }
    }

    /**
     * 出现了一些控制类之外的旋转
     */
    fun rotateParticlesAsAxis(angle: Double) {
        world ?: return
        val visible = ServerParticleGroupManager.filterVisiblePlayer(this)
        visible.forEach {
            val player = world!!.getPlayerByUuid(it) ?: return@forEach
            // 发销毁包
            val packet = PacketParticleGroupS2C(
                uuid, ControlType.CHANGE, mapOf(
                    PacketParticleGroupS2C.PacketArgsType.ROTATE_AXIS.toArgsName to ParticleControlerDataBuffers.double(
                        angle
                    )
                )
            )
            ServerPlayNetworking.send(player as ServerPlayerEntity, packet)
        }
    }

    /**
     * 出现了一些控制类之外的旋转
     */
    fun rotateParticlesToPoint(to: Vec3d) {
        world ?: return
        val visible = ServerParticleGroupManager.filterVisiblePlayer(this)
        visible.forEach {
            val player = world!!.getPlayerByUuid(it) ?: return@forEach
            // 发销毁包
            val packet = PacketParticleGroupS2C(
                uuid, ControlType.CHANGE, mapOf(
                    PacketParticleGroupS2C.PacketArgsType.ROTATE_TO.toArgsName to ParticleControlerDataBuffers.vec3d(to)
                )
            )
            ServerPlayNetworking.send(player as ServerPlayerEntity, packet)
        }
    }

    fun changeTick(tick: Int) {
        this.clientTick = tick
        world ?: return
        val visible = ServerParticleGroupManager.filterVisiblePlayer(this)
        visible.forEach {
            val player = world!!.getPlayerByUuid(it) ?: return@forEach
            // 发销毁包
            val packet = PacketParticleGroupS2C(
                uuid, ControlType.CHANGE, mapOf(
                    PacketParticleGroupS2C.PacketArgsType.CURRENT_TICK.toArgsName to ParticleControlerDataBuffers.int(
                        tick
                    )
                )
            )
            ServerPlayNetworking.send(player as ServerPlayerEntity, packet)
        }
    }

    fun changeMaxTick(tick: Int) {
        this.clientMaxTick = tick
        world ?: return
        val visible = ServerParticleGroupManager.filterVisiblePlayer(this)
        visible.forEach {
            val player = world!!.getPlayerByUuid(it) ?: return@forEach
            // 发销毁包
            val packet = PacketParticleGroupS2C(
                uuid, ControlType.CHANGE, mapOf(
                    PacketParticleGroupS2C.PacketArgsType.MAX_TICK.toArgsName to ParticleControlerDataBuffers.int(tick)
                )
            )
            ServerPlayNetworking.send(player as ServerPlayerEntity, packet)
        }
    }

    fun initServerGroup(pos: Vec3d, world: World, maxTick: Int = 120) {
        this.pos = pos
        this.world = world
        this.clientMaxTick = maxTick
    }


}