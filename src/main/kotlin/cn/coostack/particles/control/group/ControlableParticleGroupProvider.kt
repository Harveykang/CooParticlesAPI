package cn.coostack.particles.control.group

import cn.coostack.network.buffer.ParticleControlerDataBuffer
import java.util.*

/**
 * ServerParticleGroupManager在发送Group包后
 * 由ClientParticleGroupManager处理此包并且通过该对象创建ControlableParticleGroupBuilder
 * 这些参数代表发包时(create particle group) 用户输入的其余参数
 * 自行解码构建
 */
interface ControlableParticleGroupProvider {
    /**
     * @param args 接收到的所有参数
     * @see ParticleControlerDataBuffer.loadedValue 所有参数都解码在这里
     */
    fun createGroup(uuid: UUID, args: Map<String, ParticleControlerDataBuffer<*>>): ControlableParticleGroup
}