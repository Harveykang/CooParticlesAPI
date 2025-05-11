package cn.coostack.cooparticlesapi.network.particle

import net.minecraft.util.math.Vec3d
import net.minecraft.world.World


/**
 * 发射单个粒子的 粒子发射器
 */
interface ParticleEmitters {
    var pos: Vec3d
    var world: World?




}