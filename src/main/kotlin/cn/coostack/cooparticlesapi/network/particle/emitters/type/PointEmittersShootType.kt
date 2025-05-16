package cn.coostack.cooparticlesapi.network.particle.emitters.type

import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.util.math.Vec3d

class PointEmittersShootType : EmittersShootType {
    companion object {
        @JvmStatic
        val CODEC: PacketCodec<RegistryByteBuf, EmittersShootType> =
            PacketCodec.ofStatic<RegistryByteBuf, EmittersShootType>(
                { b, p -> }, {
                    PointEmittersShootType()
                }
            )
        const val ID = "point"
    }

    override fun getID(): String {
        return ID
    }

    override fun getCodec(): PacketCodec<RegistryByteBuf, EmittersShootType> {
        return CODEC
    }

    override fun getPositions(origin: Vec3d, tick: Int, count: Int): List<Vec3d> {
        return List(1) {
            origin
        }
    }

    override fun getDefaultDirection(enter: Vec3d, tick: Int, pos: Vec3d, origin: Vec3d): Vec3d {
        return enter
    }
}