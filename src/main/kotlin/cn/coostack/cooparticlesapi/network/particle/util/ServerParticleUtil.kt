package cn.coostack.cooparticlesapi.network.particle.util

import cn.coostack.cooparticlesapi.network.packet.PacketParticleS2C
import cn.coostack.cooparticlesapi.utils.RelativeLocation
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.particle.ParticleEffect
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d

object ServerParticleUtil {
    /**
     * 使用minecraft的 spawnParticle方法
     * 可能无法设置粒子移动方向
     */
    fun spawnSingle(
        type: ParticleEffect,
        world: ServerWorld, pos: Vec3d, delta: Vec3d, force: Boolean, speed: Double, count: Int
    ) {
        world.players.forEach {
            world.spawnParticles(
                it, type, force, pos.x, pos.y, pos.z, count, delta.x, delta.y, delta.z, speed
            )
        }
    }
    /**
     * 使用minecraft的 spawnParticle方法
     * 可能无法设置粒子移动方向
     */
    fun spawnSingle(
        type: ParticleEffect,
        world: ServerWorld, pos: Vec3d, delta: Vec3d, force: Boolean, speed: Double, count: Int, range: Double
    ) {
        world.players.forEach {
            if (it.pos.distanceTo(pos) > range) {
                return@forEach
            }
            world.spawnParticles(
                it, type, force, pos.x, pos.y, pos.z, count, delta.x, delta.y, delta.z, speed
            )
        }
    }
    /**
     * 使用CooParticleAPI的 spawnParticle方法
     */
    fun spawnSingle(
        type: ParticleEffect,
        world: ServerWorld,
        pos: RelativeLocation,
        velocity: RelativeLocation,
        range: Double
    ) {
        spawnSingle(type, world, pos.toVector(), velocity.toVector(), range)
    }

    fun spawnSingle(type: ParticleEffect, world: ServerWorld, pos: RelativeLocation, velocity: RelativeLocation) {
        spawnSingle(type, world, pos.toVector(), velocity.toVector())
    }

    fun spawnSingle(type: ParticleEffect, world: ServerWorld, pos: Vec3d, velocity: Vec3d, range: Double) {
        world.players.forEach {
            if (it.pos.distanceTo(pos) > range) {
                return@forEach
            }
            ServerPlayNetworking.send(it, PacketParticleS2C(type, pos, velocity))
        }
    }

    fun spawnSingle(type: ParticleEffect, world: ServerWorld, pos: Vec3d, velocity: Vec3d) {
        world.players.forEach {
            ServerPlayNetworking.send(it, PacketParticleS2C(type, pos, velocity))
        }
    }

}